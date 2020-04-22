import 'package:audio_finder_example/album_ListTile.dart';
import 'package:audio_finder_example/artist_ListTile.dart';
import 'package:audio_finder_example/audio_ListTile.dart';
import 'package:audio_finder_example/playlist_ListTile.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:audio_finder/audio_finder.dart';
import 'package:permission_handler/permission_handler.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Future<List<AudioFile>> _futureAudioFiles;
  Future<List<Artist>> _futureArtists;
  Future<List<Album>> _futureAlbums;
  Future<List<Playlist>> _futurePlaylists;
  Future<List<Genre>> _futureGenres;

  int _currentIndex = 0;
  TextEditingController searchController = new TextEditingController();

  @override
  void initState() {
    super.initState();
    _futureAudioFiles = getAllAudioFiles();
  }

  @override
  void dispose() {
    searchController.dispose();
    super.dispose();
  }

  Future<List<AudioFile>> getAllAudioFiles() async {
    if (!await checkStoragePermission()) {
      return List<AudioFile>();
    }
    List<AudioFile> temp;
    try {
      temp = await AudioFinder.getAllAudioFiles();
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<AudioFile>();
    }
  }

  Future<List<AudioFile>> getAudioFilesFromFolder() async {
    if (!await checkStoragePermission()) {
      return List<AudioFile>();
    }
    List<AudioFile> temp;
    try {
      temp = await AudioFinder.getAudioFilesFromFolder("Download");
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<AudioFile>();
    }
  }

  Future<List<Artist>> getAllArtists() async {
    if (!await checkStoragePermission()) {
      return List<Artist>();
    }

    List<Artist> temp;
    try {
      temp = await AudioFinder.getAllArtists();
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<Artist>();
    }
  }

  Future<List<Album>> getAllAlbums() async {
    if (!await checkStoragePermission()) {
      return List<Album>();
    }

    List<Album> temp;
    try {
      temp = await AudioFinder.getAllAlbums();
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<Album>();
    }
  }

  Future<List<Playlist>> getAllPlaylists() async {
    if (!await checkStoragePermission()) {
      return List<Playlist>();
    }

    List<Playlist> temp;
    try {
      temp = await AudioFinder.getAllPlaylists();
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<Playlist>();
    }
  }

  Future<List<Genre>> getAllGenres() async {
    if (!await checkStoragePermission()) {
      return List<Genre>();
    }

    List<Genre> temp;
    try {
      temp = await AudioFinder.getAllGenres();
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<Genre>();
    }
  }

  Future<List<AudioFile>> findAudioFileByName() async {
    if (!await checkStoragePermission()) {
      return List<AudioFile>();
    }

    List<AudioFile> temp;
    try {
      temp = await AudioFinder.findAudioFileByName(searchController.text);
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return List<AudioFile>();
    }
  }

  static Future<bool> checkStoragePermission() async {
    var status = await Permission.storage.status;
    if (status.isUndetermined || status.isDenied) {
      if (await Permission.storage.request().isGranted) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  Future<dynamic> getRightFuture() {
    if (_currentIndex == 0) {
      return getAllAudioFiles();
    } else if (_currentIndex == 1) {
      return getAudioFilesFromFolder();
    } else if (_currentIndex == 2) {
      return getAllAlbums();
    } else if (_currentIndex == 3) {
      return getAllArtists();
    } else if (_currentIndex == 4) {
      return getAllPlaylists();
    } else {
      return getAllGenres();
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text("Audio finder plugin"),
        ),
        body: Column(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.all(15.0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  Expanded(
                    child: TextField(
                      controller: searchController,
                    ),
                  ),
                  GestureDetector(
                    child: Icon(
                      Icons.search,
                      size: 40,
                    ),
                    onTap: () {
                      setState(() {
                        _futureAudioFiles = findAudioFileByName();
                      });
                    },
                  )
                ],
              ),
            ),
            FutureBuilder(
              future: getRightFuture(),
              builder: (context, snapshot) {
                if (!snapshot.hasData) {
                  return Center(
                      child: CircularProgressIndicator(
                    strokeWidth: 10,
                  ));
                } else {
                  return Expanded(
                    child: ListView.builder(
                      scrollDirection: Axis.vertical,
                      itemCount: snapshot.data.length,
                      itemBuilder: (BuildContext context, int index) {
                        if (_currentIndex == 0 || _currentIndex == 1) {
                          AudioFile temp = snapshot.data[index];
                          return AudioListTile(
                            id: temp.id.toString(),
                            album: temp.album,
                            albumId: temp.albumId.toString(),
                            artist: temp.artist,
                            artistId: temp.artistId.toString(),
                            duration: temp.duration.toString(),
                            name: temp.name,
                            path: temp.path,
                            purpose: temp.purpose
                                .toString()
                                .toLowerCase()
                                .split('.')
                                .last,
                            type: temp.type,
                            size: temp.size.toString(),
                          );
                        } else if (_currentIndex == 2) {
                          Album temp = snapshot.data[index];
                          return AlbumListTile(
                            artist: temp.artist,
                            id: temp.id.toString(),
                            name: temp.name,
                            numberOfTracks: temp.numberOfTracks.toString(),
                            year: temp.year.toString(),
                          );
                        } else if (_currentIndex == 3) {
                          Artist temp = snapshot.data[index];
                          return ArtistListTile(
                            numberOfAlbums: temp.numberOfAlbums.toString(),
                            id: temp.id.toString(),
                            name: temp.name,
                            numberOfTracks: temp.numberOfTracks.toString(),
                          );
                        } else if (_currentIndex == 4) {
                          Playlist temp = snapshot.data[index];
                          return PlaylistListTile(
                            data: temp.data,
                            id: temp.id.toString(),
                            name: temp.name,
                          );
                        } else {
                          Genre temp = snapshot.data[index];
                          return ListTile(
                            leading: Text(temp.id.toString()),
                            title: Text(temp.name),
                          );
                        }
                      },
                    ),
                  );
                }
              },
            ),
          ],
        ),
        bottomNavigationBar: BottomNavigationBar(
          currentIndex: _currentIndex,
          type: BottomNavigationBarType.fixed,
          items: [
            BottomNavigationBarItem(
                icon: Icon(Icons.library_music), title: Text("All files")),
            BottomNavigationBarItem(
                icon: Icon(Icons.folder), title: Text("By folder")),
            BottomNavigationBarItem(
                icon: Icon(Icons.album), title: Text("All albums")),
            BottomNavigationBarItem(
                icon: Icon(Icons.supervised_user_circle),
                title: Text("All artists")),
            BottomNavigationBarItem(
                icon: Icon(Icons.queue_music), title: Text("All playlists")),
            BottomNavigationBarItem(
                icon: Icon(Icons.music_note), title: Text("All genres")),
          ],
          onTap: (index) {
            if (index == 0) {
              _futureAudioFiles = getAllAudioFiles();
            } else if (index == 1) {
              _futureAudioFiles = getAudioFilesFromFolder();
            } else if (index == 2) {
              _futureAlbums = getAllAlbums();
            } else if (index == 3) {
              _futureArtists = getAllArtists();
            } else if (index == 4) {
              _futurePlaylists = getAllPlaylists();
            } else if (index == 5) {
              _futureGenres = getAllGenres();
            }
            setState(() {
              _currentIndex = index;
            });
          },
        ),
      ),
    );
  }
}
