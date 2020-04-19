import 'dart:async';

import 'package:flutter/services.dart';

part 'audio_file.dart';

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

  static Future<List<AudioFile>> getAudioFilesByPurpose(
      AudioPurpose purpose) async {
    final List<dynamic> returnedFiles = await _channel.invokeMethod(
        'getAudioFilesByPurpose',
        {"purpose": purpose.toString().toLowerCase().split('.').last});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }

  static Future<List<AudioFile>> findAudioFileByName(String fileName) async {
    final List<dynamic> returnedFiles = await _channel
        .invokeMethod('findAudioFileByName', {"fileName": fileName});
    return returnedFiles.map((map) => new AudioFile(map)).toList();
  }
}
