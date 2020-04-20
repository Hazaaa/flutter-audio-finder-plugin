import 'package:audio_finder_example/audio_ListTile.dart';
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

  Future<List<AudioFile>> getAudioFilesByPurpose() async {
    if (!await checkStoragePermission()) {
      return List<AudioFile>();
    }

    List<AudioFile> temp;
    try {
      temp = await AudioFinder.getAudioFilesByPurpose(AudioPurpose.Music);
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<AudioFile>();
    }
  }

  Future<List<AudioFile>> getAudioFilesBySize() async {
    if (!await checkStoragePermission()) {
      return List<AudioFile>();
    }

    List<AudioFile> temp;
    try {
      temp = await AudioFinder.getAudioFilesBySize(0.2, true);
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<AudioFile>();
    }
  }

  Future<List<AudioFile>> getAudioFilesByLength() async {
    if (!await checkStoragePermission()) {
      return List<AudioFile>();
    }

    List<AudioFile> temp;
    try {
      temp = await AudioFinder.getAudioFilesByLength(80000, false);
      return temp;
    } on PlatformException catch (error) {
      print(error.message);
      return new List<AudioFile>();
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
              future: _futureAudioFiles,
              builder: (context, AsyncSnapshot<List<AudioFile>> snapshot) {
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
                        }),
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
                icon: Icon(Icons.notifications), title: Text("By purpose")),
            BottomNavigationBarItem(
                icon: Icon(Icons.chevron_right), title: Text("By Size")),
          ],
          onTap: (index) {
            if (index == 0) {
              _futureAudioFiles = getAllAudioFiles();
            } else if (index == 1) {
              _futureAudioFiles = getAudioFilesFromFolder();
            } else if (index == 2) {
              _futureAudioFiles = getAudioFilesByPurpose();
            } else if (index == 3) {
              _futureAudioFiles = getAudioFilesBySize();
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
