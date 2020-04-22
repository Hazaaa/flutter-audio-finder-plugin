import 'package:flutter/material.dart';

class PlaylistListTile extends StatelessWidget {
  const PlaylistListTile(
      {Key key, this.id, this.name, this.data,})
      : super(key: key);

  final String id;
  final String name;
  final String data;

  @override
  Widget build(BuildContext context) {
    return ExpansionTile(
      leading: Text(id),
      title: Text(name),
      children: <Widget>[
        Text("Data: " + data)
      ],
    );
  }
}
