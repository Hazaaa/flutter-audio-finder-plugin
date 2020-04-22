part of 'audio_finder.dart';

class Artist {
  int id;
  String name;
  int numberOfTracks;
  int numberOfAlbums;
  List<AudioFile> tracks;

  Artist(Map map) {
    id = map['id'];
    name = map['name'];
    numberOfAlbums = map['numberOfAlbums'];
    numberOfTracks = map['numberOfTracks'];

    if(map.containsKey("tracks")) {
      tracks = new List<AudioFile>();

      for (var trackMap in map['tracks']) {
        tracks.add(new AudioFile(trackMap));
      }
    }
  }

  // TODO: REMOVE THIS
  logData() {
    print("Artist name: " + name + ", id: " + id.toString() + ", number of tracks: " + numberOfTracks.toString() + ", number of albums: " + numberOfAlbums.toString());
  }
}
