import 'dart:async';

import 'package:flutter/services.dart';

part 'audio_file.dart';
part 'artist.dart';
part 'album.dart';
part 'playlist.dart';
part 'genre.dart';

class AudioFinder {
  static const MethodChannel _channel = const MethodChannel('audio_finder');

  static Future<List<AudioFile>> getAllAudioFiles() async {
    final List<dynamic> returnedFiles =
        await _channel.invokeMethod('getAllAudioFiles');
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  static Future<List<AudioFile>> getAudioFilesFromFolder(
      String folderName) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('getAudioFilesFromFolder', {"folderName": folderName});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  /// Returns all audio files by purpose.
  /// [purpose] can be Music, Ringtone, Alarm, Notification or Podcast.
  static Future<List<AudioFile>> getAudioFilesByPurpose(
      AudioPurpose purpose) async {
    final List<dynamic> returnedFiles = await _channel.invokeMethod(
        'getAudioFilesByPurpose',
        {"purpose": purpose.toString().toLowerCase().split('.').last});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  /// Returns all audio files less or above provided [size].
  /// [size] is in megabytes.
  /// If [moreThan] is true it will return all audio files larger than [size],
  /// and if is false it will return all audio files smaller than [size].
  static Future<List<AudioFile>> getAudioFilesBySize(
      double size, bool moreThan) async {
    final List<dynamic> returnedFiles = await _channel.invokeMethod(
        'getAudioFilesBySize',
        {"size": (size * 1000000).toString(), "moreThan": moreThan});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  /// Returns all audio files shorter or longer than provided [length].
  /// [length] is in seconds.
  /// If [longerThan] is true it will return all audio files longer than [length],
  /// and if is false it will return all audio files shorter than [length].
  static Future<List<AudioFile>> getAudioFilesByLength(
      int length, bool longerThan) async {
    final List<dynamic> returnedFiles = await _channel.invokeMethod(
        'getAudioFilesByLength',
        {"length": (length * 1000).toString(), "longerThan": longerThan});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  static Future<List<AudioFile>> findAudioFileByName(String fileName) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('findAudioFileByName', {"fileName": fileName});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  // Artists

  static Future<List<Artist>> getAllArtists() async {
    final List<dynamic> returnedFiles =
        await _channel.invokeMethod('getArtistsData');
    return returnedFiles.map((map) => new Artist(map)).toList();
  }

  static Future<Artist> getArtistById(int artistId, bool includeTracks) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('getArtistsData', {"artistId": artistId.toString(), "includeTracks": includeTracks});
    return returnedFiles.map((map) => new Artist(map)).toList().first;
  }

  static Future<List<Artist>> findArtistByName(String artistName, bool includeTracks) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('getArtistsData', {"artistName": artistName, "includeTracks": includeTracks});
    return returnedFiles.map((map) => new Artist(map)).toList();
  }

  // Albums

  static Future<List<Album>> getAllAlbums() async {
    final List<dynamic> returnedFiles =
        await _channel.invokeMethod('getAlbumsData');
    return returnedFiles.map((map) => new Album(map)).toList();
  }

  static Future<Album> getAlbumById(int albumId, bool includeTracks) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('getAlbumsData', {"albumId": albumId.toString(), "includeTracks": includeTracks});
    return returnedFiles.map((map) => new Album(map)).toList().first;
  }

  static Future<List<Album>> findAlbumByName(String albumName, bool includeTracks) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('getAlbumsData', {"albumName": albumName, "includeTracks": includeTracks});
    return returnedFiles.map((map) => new Album(map)).toList();
  }

  // Playlists

  static Future<List<Playlist>> getAllPlaylists() async {
    final List<dynamic> returnedFiles =
        await _channel.invokeMethod('getPlaylistsData');
    return returnedFiles.map((map) => new Playlist(map)).toList();
  }

  // Genres

  static Future<List<Genre>> getAllGenres() async {
    final List<dynamic> returnedFiles =
        await _channel.invokeMethod('getGenresData');
    return returnedFiles.map((map) => new Genre(map)).toList();
  }
}
