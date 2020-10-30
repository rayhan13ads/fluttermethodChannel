import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:methodChannelDemo/models/post.dart';

class HomePage extends StatefulWidget {
  HomePage({Key key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String methodChannelValue;
  static const methodChannel = MethodChannel('username');
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    methodChannelValue = 'not initiated';
  }

  initMethodChannel() async {
    // pass in data into android kotlin code;
    Posts posts = await apiCall();

    print("--------get data--------${posts.body}");

    //make json to string
    String data = json.encode(posts);
    print("---------------string data ${data}");

    var sendmap = <String, dynamic>{'from': data, 'phone': '01639130474'};
    String returedValue =
        await methodChannel.invokeMethod<String>('getUserName', sendmap);
    if (returedValue != null) {
      setState(() {
        methodChannelValue = returedValue;
      });
    }
  }


sslCommerzMethodChannel() async {
   
    String returedValue =
        await methodChannel.invokeMethod<String>('callSSL');
    if (returedValue != null) {
      setState(() {
        methodChannelValue = returedValue;
      });
    }
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Title'),
      ),
      body: Container(
        child: Center(
          child: Column(
            children: [
              Text(
                "${methodChannelValue}",
                style: Theme.of(context).textTheme.headline4,
              ),
              RaisedButton(
                onPressed: () {
                  initMethodChannel();
                },
                color: Colors.greenAccent,
                child: Text("Initate Method"),
              ),
              SizedBox(height: 30,),
              RaisedButton(
                onPressed: () {
                  sslCommerzMethodChannel();
                },
                color: Colors.greenAccent,
                child: Text("payment by ssl commerz"),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<Posts> apiCall() async {
    Dio dio = Dio();
    var res = await dio.get("https://jsonplaceholder.typicode.com/posts/1");
    return Posts.fromJson(res.data);
  }
}
