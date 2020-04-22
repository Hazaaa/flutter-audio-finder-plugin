part of 'audio_finder.dart';

class Album {
  int id;
  String name;
  String artist;
  int numberOfTracks;
  int year;
  List<AudioFile> tracks;

  Album(Map map) {
    id = map['id'];
    name = map['name'];
    artist = map['artist'];
    numberOfTracks = map['numberOfTracks'];
    year = map['year'];

    if(map.containsKey("tracks")) {
      tracks = new List<AudioFile>();

      for (var trackMap in map['tracks']) {
        tracks.add(new AudioFile(trackMap));
      }
    }
  }

  // TODO: REMOVE THIS
  logData() {
    print("Album name: " + name + ", id: " + id.toString() + ", artist: " + artist + ", number of tracks: " + numberOfTracks.toString() + ", year: " + year.toString());
  }
}
