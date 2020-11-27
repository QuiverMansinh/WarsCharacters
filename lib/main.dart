import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_app_test_1/CharacterScrollList.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: Builder(
            builder: (BuildContext context) {
              SystemChrome.setEnabledSystemUIOverlays([]);
              double width = MediaQuery. of(context). size. width;
              return ButtonBar(
                alignment: MainAxisAlignment.start,
                buttonHeight: 100,
                children: [
                  Container(
                    width: width,
                    child: FloatingActionButton(
                      heroTag: null,
                      backgroundColor: Color.fromARGB(255, 150, 150, 150),
                      shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.only(topRight: Radius.circular(100),bottomRight: Radius.circular(100))),
                      child: Container(
                        alignment: Alignment.centerLeft,
                        padding: EdgeInsets.only(left: 30),
                        child: Text('New',textAlign: TextAlign.start),
                      ),
                      onPressed: () {
                        Navigator.push(context, MaterialPageRoute(builder: (context) => CharacterScrollList()));
                      },
                    ),
                  ),
                  Container(
                    height: 20,
                  ),
                  Container(
                    width: width/(4/3),
                    child: FloatingActionButton(
                      heroTag: null,
                      backgroundColor: Color.fromARGB(255, 150, 150, 150),
                      shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.only(topRight: Radius.circular(100),bottomRight: Radius.circular(100))),
                      child: Container(
                        alignment: Alignment.centerLeft,
                        padding: EdgeInsets.only(left: 30),
                        child: Text('Continue',textAlign: TextAlign.start),
                      ),
                      onPressed: () {/** */},
                    ),
                  ),
                  Container(
                    height: 20,
                  ),
                  Container(
                    width: width/2,
                    child: FloatingActionButton(
                      heroTag: null,
                      backgroundColor: Color.fromARGB(255, 150, 150, 150),
                      shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.only(topRight: Radius.circular(100),bottomRight: Radius.circular(100))),
                      child: Container(
                        alignment: Alignment.centerLeft,
                        padding: EdgeInsets.only(left: 30),
                        child: Text('Multi-player',textAlign: TextAlign.start),
                      ),
                      onPressed: null,
                      disabledElevation: 1,
                    ),
                  ),
                ],
              );
            },
          ),
        ),
      ),
    );
  }
}