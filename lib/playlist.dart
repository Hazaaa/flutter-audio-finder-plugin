part of 'audio_finder.dart';

class Playlist {
  int id;
  String name;
  String data;
  List<AudioFile> tracks;

  Playlist(Map map) {
    id = map['id'];
    name = map['name'];
    data = map['data'];

    if(map.containsKey("tracks")) {
      tracks = new List<AudioFile>();

      for (var trackMap in map['tracks']) {
        tracks.add(new AudioFile(trackMap));
      }
    }
  }

  // TODO: REMOVE THIS
  logData() {
    print("Playlist name: " + name + ", id: " + id.toString() + ", data: " + data);
  }
}
