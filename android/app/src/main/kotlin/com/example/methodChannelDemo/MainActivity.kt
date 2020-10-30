package com.example.methodChannelDemo

import com.example.methodChannelDemo.models.Post
import com.google.gson.Gson
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
//import io.flutter.app.FlutterActivity


class MainActivity: FlutterActivity(), SSLCTransactionResponseListener {

    private  val CHANNEL:String = "username";
    private lateinit var SSLDATA:String;
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor,CHANNEL).setMethodCallHandler { call, result ->
            val arg:Map<String,Object> = call.arguments();
            val gson = Gson()
            if (call.method.equals("getUserName")){

                var from = arg.get("from")

                var post:Post = gson.fromJson(from.toString(),Post::class.java);
                var message = "Hi came from  ${post.body}"
                result.success(message);
            }
        }

        MethodChannel(flutterEngine.dartExecutor,CHANNEL).setMethodCallHandler { call, result ->
            //val arg:Map<String,Object> = call.arguments();
            //val gson = Gson()
            if (call.method.equals("callSSL")){

              //  var from = arg.get("from")

                //var post:Post = gson.fromJson(from.toString(),Post::class.java);
                integrateSSlCommerze()
                var message = "SSL Response:  ${SSLDATA}"
                result.success(message);
            }
        }


    }


   fun integrateSSlCommerze(){
       var STORE_ID = "jadro5f619e4e02e99"
       var PASSWORD = "jadro5f619e4e02e99@ssl"
       val sslCommerzInitialization = SSLCommerzInitialization(STORE_ID,PASSWORD,2000.00,SSLCCurrencyType.BDT,"201","",SSLCSdkType.TESTBOX);
       val customerInfoInitializer =  SSLCCustomerInfoInitializer("customer name", "customer email",
       "address", "dhaka", "1214", "Bangladesh", "01639130474");
       IntegrateSSLCommerz
               .getInstance(application.applicationContext)
               .addSSLCommerzInitialization(sslCommerzInitialization)
               .addCustomerInfoInitializer(customerInfoInitializer)
               .buildApiCall(this);

    }

    override fun transactionSuccess(p0: SSLCTransactionInfoModel?) {
        var gson = Gson();
        var dataString = gson.toJson(p0);
        print("--------transection Succcess---------$dataString-");
        this.SSLDATA = dataString;
    }

    override fun transactionFail(p0: String?) {
       print("----------transactrionFaild --------$p0");
        this.SSLDATA = p0.toString()
    }

    override fun merchantValidationError(p0: String?) {
      print("---------merchant Validation------$p0")
        this.SSLDATA = p0.toString()
    }

}
