package com.example.audio_finder.AudioHelper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.Log;

public class AudioHelper {

    public AudioHelper() {
    }

    private static Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static Uri albumsUri = MediaStore.Audio.Albums.INTERNAL_CONTENT_URI;
    private static Uri artistsUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    private static Uri playlistsUri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
    private static Uri genresUri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;

    private static String[] audioDataColumns = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.AudioColumns.DATA, // Full path
            MediaStore.Audio.Media.MIME_TYPE, // Type of file
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.SIZE, // Size in bytes
            MediaStore.Audio.Media.DURATION, // Duration in milliseconds
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_ALARM,
            MediaStore.Audio.Media.IS_NOTIFICATION,
            MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.IS_RINGTONE
    };

    private static String[] artistDataColumns = {
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS,
            MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS
    };

    private static String[] albumDataColumns = {
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.FIRST_YEAR // Year of release
    };

    private static String[] playlistDataColumns = {
            MediaStore.Audio.Playlists._ID,
            MediaStore.Audio.Playlists.NAME,
            MediaStore.Audio.Playlists.DATA,
    };

    private static String[] genresDataColumns = {
            MediaStore.Audio.Genres._ID,
            MediaStore.Audio.Genres.NAME
    };


    // AUDIO FILES

    public static List<Map<String, Object>> getAllAudioFilesFromDevice(final Context context) {
        return getData(context, "audio", uri, audioDataColumns, null, null, null);
    }

    public static List<Map<String, Object>> getAudioFilesFromFolder(final Context context, final String folderName) {
        return getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%" + folderName + "%"}, null);
    }

    public static List<Map<String, Object>> getAudioFilesByPurpose(final Context context, final String purpose) {
        return getData(context, "audio", uri, audioDataColumns, getAudioPurposeQuery(purpose), null, null);
    }

    public static List<Map<String, Object>> findAudioFileByName(final Context context, final String fileName) {
        return getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%" + fileName + "%"}, null);
    }

    public static List<Map<String, Object>> getAudioFilesBySize(final Context context, final String size, final boolean moreThan) {
        if (moreThan) {
            return getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.SIZE + " >= ? ", new String[]{size}, null);
        } else {
            return getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.SIZE + " <= ? ", new String[]{size}, null);
        }
    }

    public static List<Map<String, Object>> getAudioFilesByLength(final Context context, final String length, final boolean longerThan) {
        if (longerThan) {
            return getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.DURATION + " >= ? ", new String[]{length}, null);
        } else {
            return getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.DURATION + " <= ? ", new String[]{length}, null);
        }
    }

    // ARTISTS

    public static List<Map<String, Object>> getAllArtists(final Context context) {
        return getData(context, "artist", artistsUri, artistDataColumns, null, null, null);
    }

    public static List<Map<String, Object>> getArtistById(final Context context, final String artistId, final boolean includeTracks) {
        List<Map<String, Object>> resultArtist = getData(context, "artist", artistsUri, artistDataColumns, MediaStore.Audio.Artists._ID + " = ? ", new String[]{artistId}, null);

        if (includeTracks && !resultArtist.isEmpty()) {
            List<Map<String, Object>> albumTracks = getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.ARTIST_ID + " = ? ", new String[]{"%" + artistId + "%"}, null);
            resultArtist.get(0).put("tracks", albumTracks);
        }

        return resultArtist;
    }

    public static List<Map<String, Object>> getArtistByName(final Context context, final String artistName, final boolean includeTracks) {
        List<Map<String, Object>> resultArtist = getData(context, "artist", artistsUri, artistDataColumns, MediaStore.Audio.Artists.ARTIST + " like ? ", new String[]{"%" + artistName + "%"}, null);

        if (includeTracks && !resultArtist.isEmpty()) {
            List<Map<String, Object>> albumTracks = getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.ARTIST + " like ? ", new String[]{"%" + artistName + "%"}, null);
            resultArtist.get(0).put("tracks", albumTracks);
        }

        return resultArtist;
    }

    // ALBUMS

    public static List<Map<String, Object>> getAllAlbums(final Context context) {
        return getData(context, "album", albumsUri, albumDataColumns, null, null, null);
    }

    public static List<Map<String, Object>> getAlbumById(final Context context, final String albumId, final boolean includeTracks) {
        List<Map<String, Object>> resultAlbum = getData(context, "album", albumsUri, albumDataColumns, MediaStore.Audio.AlbumColumns.ALBUM_ID + " = ? ", new String[]{albumId}, null);

        if (includeTracks && !resultAlbum.isEmpty()) {
            List<Map<String, Object>> albumTracks = getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.ALBUM_ID + " like ? ", new String[]{"%" + albumId + "%"}, null);
            resultAlbum.get(0).put("tracks", albumTracks);
        }

        return resultAlbum;
    }

    public static List<Map<String, Object>> getAlbumByName(final Context context, final String albumName, final boolean includeTracks) {
        List<Map<String, Object>> resultAlbum = getData(context, "album", albumsUri, albumDataColumns, MediaStore.Audio.AlbumColumns.ALBUM + " like ? ", new String[]{"%" + albumName + "%"}, null);

        if (includeTracks && !resultAlbum.isEmpty()) {
            List<Map<String, Object>> albumTracks = getData(context, "audio", uri, audioDataColumns, MediaStore.Audio.Media.ALBUM + " like ? ", new String[]{"%" + albumName + "%"}, null);
            resultAlbum.get(0).put("tracks", albumTracks);
        }

        return resultAlbum;
    }

    // PLAYLISTS

    public static List<Map<String, Object>> getAllPlaylists(final Context context) {
        return getData(context, "playlist", playlistsUri, playlistDataColumns, null, null, null);
    }

    // GENRES

    public static List<Map<String, Object>> getAllGenres(final Context context) {
        return getData(context, "genre", genresUri, genresDataColumns, null, null, null);
    }

    // Main methods

    public static List<Map<String, Object>> getData(final Context context, final String dataFor, final Uri uri, final String[] dataColumns, final String selection, final String[] arguments, final String sortOrder) {
        List<Map<String, Object>> resultAudioList;

        Cursor cursor = context.getContentResolver()
                .query(
                        uri,
                        dataColumns,
                        selection,
                        arguments,
                        sortOrder
                );

        if (cursor != null) {
            try {
                resultAudioList = extractDataFromCursor(cursor, dataFor);
            } catch (Exception e) {
                Log.d("Logged cursor error: ", "Error thrown while trying to extract data from cursor. Error details: " + e.getMessage());
                resultAudioList = new ArrayList<>();
            } finally {
                cursor.close();
            }
        } else {
            resultAudioList = new ArrayList<>();
        }

        return resultAudioList;
    }

    private static List<Map<String, Object>> extractDataFromCursor(Cursor cursor, String dataFor) {

        final List<Map<String, Object>> tempAudioList = new ArrayList<>();

        while (cursor.moveToNext()) {

            HashMap<String, Object> audioDataMap = new HashMap<>();

            if (dataFor.equals("audio")) {
                // Getting data from all columns
                String id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                String type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String artistID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String albumID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String isMusic = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                String isAlarm = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.IS_ALARM));
                String isNotification = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.IS_NOTIFICATION));
                String isPodcast = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.IS_PODCAST));
                String isRingtone = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.IS_RINGTONE));

                audioDataMap.put("id", Integer.parseInt(id));
                audioDataMap.put("path", path);
                audioDataMap.put("type", type);
                audioDataMap.put("name", name);
                audioDataMap.put("size", Integer.parseInt(size));
                audioDataMap.put("duration", Integer.parseInt(duration));
                audioDataMap.put("artist", artist);
                audioDataMap.put("artistId", Integer.parseInt(artistID));
                audioDataMap.put("album", album);
                audioDataMap.put("albumId", Integer.parseInt(albumID));
                audioDataMap.put("purpose", getAudioPurposeString(isMusic, isAlarm, isNotification, isPodcast, isRingtone));

            } else if (dataFor.equals("artist")) {

                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                String artistID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String numberOfTracks = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
                String numberOfAlbums = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));

                audioDataMap.put("name", artist);
                audioDataMap.put("id", Integer.parseInt(artistID));
                audioDataMap.put("numberOfTracks", Integer.parseInt(numberOfTracks));
                audioDataMap.put("numberOfAlbums", Integer.parseInt(numberOfAlbums));

            } else if (dataFor.equals("album")) {

                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String albumID = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
                String numberOfTracks = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                String year = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.FIRST_YEAR));

                audioDataMap.put("name", album);
                audioDataMap.put("id", Integer.parseInt(albumID));
                audioDataMap.put("artist", artist);
                audioDataMap.put("numberOfTracks", Integer.parseInt(numberOfTracks));
                audioDataMap.put("year", Integer.parseInt(year));

            } else if (dataFor.equals("playlist")) {
                String playlist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME));
                String playlistId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.DATA));

                audioDataMap.put("name", playlist);
                audioDataMap.put("id", Integer.parseInt(playlistId));
                audioDataMap.put("data", data);

            } else if (dataFor.equals("genre")) {
                String genre = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME));
                String genreId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres._ID));

                audioDataMap.put("name", genre);
                audioDataMap.put("id", Integer.parseInt(genreId));
            }

            tempAudioList.add(audioDataMap);
        }

        return tempAudioList;
    }

    // Helpers

    private static String getAudioPurposeString(String isMusic, String isAlarm, String isNotification, String isPodcast, String isRingtone) {
        if (isMusic.equals("1")) {
            return "music";
        }

        if (isAlarm.equals("1")) {
            return "alarm";
        }

        if (isNotification.equals("1")) {
            return "notification";
        }

        if (isPodcast.equals("1")) {
            return "podcast";
        }

        if (isRingtone.equals("1")) {
            return "ringtone";
        }
        return "";
    }

    private static String getAudioPurposeQuery(String purpose) {
        switch (purpose) {
            case "music":
                return MediaStore.Audio.Media.IS_MUSIC + " = 1 ";
            case "notification":
                return MediaStore.Audio.Media.IS_NOTIFICATION + " = 1 ";
            case "alarm":
                return MediaStore.Audio.Media.IS_ALARM + " = 1 ";
            case "podcast":
                return MediaStore.Audio.Media.IS_PODCAST + " = 1 ";
            case "ringtone":
                return MediaStore.Audio.Media.IS_RINGTONE + " = 1 ";
            default:
        }
        return "";
    }
}
