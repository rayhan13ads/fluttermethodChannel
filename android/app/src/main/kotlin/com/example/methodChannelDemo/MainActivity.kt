package com.example.methodChannelDemo

import com.example.methodChannelDemo.models.Post
import com.google.gson.Gson
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private  val CHANNEL:String = "username";

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


    }

}
