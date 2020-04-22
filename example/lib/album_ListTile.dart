import 'package:flutter/material.dart';

class AlbumListTile extends StatelessWidget {
  const AlbumListTile(
      {Key key,
      this.id,
      this.name,
      this.artist,
      this.numberOfTracks,
      this.year})
      : super(key: key);

  final String id;
  final String name;
  final String artist;
  final String numberOfTracks;
  final String year;

  @override
  Widget build(BuildContext context) {
    return ExpansionTile(
      leading: Text(id),
      title: Text(name),
      children: <Widget>[
        Text("Artist: " + artist),
        Text("Number of tracks: " + numberOfTracks),
        Text("Year: " + year),
      ],
    );
  }
}
