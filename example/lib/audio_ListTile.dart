import 'package:flutter/material.dart';

class AudioListTile extends StatelessWidget {
  const AudioListTile(
      {Key key,
      this.id,
      this.name,
      this.path,
      this.size,
      this.duration,
      this.artist,
      this.artistId,
      this.album,
      this.albumId,
      this.purpose,
      this.type})
      : super(key: key);

  final String id;
  final String name;
  final String path;
  final String size;
  final String duration;
  final String artist;
  final String artistId;
  final String album;
  final String albumId;
  final String purpose;
  final String type;

  @override
  Widget build(BuildContext context) {
    return ExpansionTile(
      leading: Text(id),
      title: Text(name),
      children: <Widget>[
        Text("Path: " + path),
        Text("Size: " + size),
        Text("Duration: " + duration),
        Text("Type: " + type),
        Text("Purpose: " + purpose),
        Text("Artist: " + artist),
        Text("ArtistID: " + artistId),
        Text("Album: " + album),
        Text("AlbumID: " + albumId),
      ],
    );
  }
}
