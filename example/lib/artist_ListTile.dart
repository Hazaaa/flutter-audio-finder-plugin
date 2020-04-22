import 'package:flutter/material.dart';

class ArtistListTile extends StatelessWidget {
  const ArtistListTile(
      {Key key, this.id, this.name, this.numberOfTracks, this.numberOfAlbums})
      : super(key: key);

  final String id;
  final String name;
  final String numberOfTracks;
  final String numberOfAlbums;

  @override
  Widget build(BuildContext context) {
    return ExpansionTile(
      leading: Text(id),
      title: Text(name),
      children: <Widget>[
        Text("Number of tracks: " + numberOfTracks),
        Text("Number of albums: " + numberOfAlbums),
      ],
    );
  }
}
