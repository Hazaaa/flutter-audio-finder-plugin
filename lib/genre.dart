part of 'audio_finder.dart';

class Genre {
  int id;
  String name;
  List<AudioFile> tracks;

  Genre(Map map) {
    id = map['id'];
    name = map['name'];

    if(map.containsKey("tracks")) {
      tracks = new List<AudioFile>();

      for (var trackMap in map['tracks']) {
        tracks.add(new AudioFile(trackMap));
      }
    }
  }

  // TODO: REMOVE THIS
  logData() {
    print("Genre name: " + name + ", id: " + id.toString());
  }
}
