# Audio Finder Plugin

Flutter plugin for retrieving all audio files from device. 

*Note:* This plugin is for now only for Android. iOS implementation will be in future and if anyone want to contribute to iOS implementation is more than welcome to do that.

## Features

- [x] - Get all audio files from device.
- [x] - Get audio files from specific folder.
- [x] - Get audio files by purpose (e.g. Music, Notification, Alarm, Ringtone, Podcast).
- [x] - Find audio files by name.
- [x] - Get audio files with specific length or size.
- [x] - Get all artists and their information.
- [x] - Get all audio files from certain artist.
- [x] - Get all albums and their information.
- [x] - Get all audio files from certain album.
- [x] - Get all playlists and their information.
- [x] - Get all audio files for certain playlist.
- [x] - Get all genres.
- [x] - Get all audio files for certain genre.

## Todo

- [ ] - Get list of folders that contains one or more audio files.
- [ ] - Add permission control.
- [ ] - Add tests for everything.

## Installation

Add `audio_finder` as a dependency in your pubspec.yaml file.

>*Note:* If you see this message: 
  ```.../AudioFinderPlugin.java uses or overrides a deprecated API. Note: Recompile with -Xlint:deprecation for details.```
It means that plugin is using java methods that are deprecated in AndroidX but they are needed for older APIs. This is just warning not error. Your code will compile and app will run just fine.

### Example

Here is small flutter app example how to use audio_finder plugin.

```TODO: ADD EXAMPLE```
