part of 'audio_finder.dart';

enum AudioPurpose { Music, Notification, Alaram, Ringtone, Podcast }

class AudioFile {
  int id;

  /// Full path to file
  String path;

  /// MIME type
  String type;

  /// File display name
  String name;

  /// File size in bytes
  int size;

  /// File duration in miliseconds
  int duration;

  /// File purpose (eg. Music, Notification, Ringtone, Podcast, Alarm)
  AudioPurpose purpose;

  String artist;
  int artistId;
  String album;
  int albumId;

  AudioFile(Map map) {
    id = map['id'];
    path = map['path'];
    type = map['type'];
    name = map['name'];
    size = map['size'];
    duration = map['duration'];
    artist = map['artist'];
    artistId = map['artistId'];
    album = map['album'];
    albumId = map['albumId'];

    switch (map['purpose']) {
      case "music":
        purpose = AudioPurpose.Music;
        break;
      case "notification":
        purpose = AudioPurpose.Notification;
        break;
      case "alarm":
        purpose = AudioPurpose.Alaram;
        break;
      case "podcast":
        purpose = AudioPurpose.Podcast;
        break;
      case "ringtone":
        purpose = AudioPurpose.Ringtone;
        break;
      default:
        purpose = AudioPurpose.Music;
    }
  }
}
