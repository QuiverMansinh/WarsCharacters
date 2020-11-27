import 'package:flutter/material.dart';
class CharacterScrollList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            body: Center(
                child: Builder(
                    builder: (BuildContext context) {
                      double width = MediaQuery. of(context). size. width;
                      return ListView(
                        padding: const EdgeInsets.all(10),
                        children: <Widget>[
                          ButtonBar(
                            alignment: MainAxisAlignment.center,
                            children: [
                              Container(
                                width: width,
                                child: FloatingActionButton(
                                  heroTag: null,
                                  backgroundColor: Color.fromARGB(255, 150, 150, 150),
                                  shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(100)),
                                  child: Container(
                                    alignment: Alignment.centerLeft,
                                    padding: EdgeInsets.only(left: 30),
                                  ),
                                  onPressed: () {
                                    Navigator.push(context, MaterialPageRoute(builder: (context) => CharacterScrollList()));
                                  },
                                ),
                              ),
                              Container(
                                height: 20,
                              ),
                            ],
                          )
                        ],
                      );
                    }
                )
            )
        )
    );
  }
}