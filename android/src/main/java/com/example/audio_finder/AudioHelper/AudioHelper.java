package com.example.audio_finder.AudioHelper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioHelper {

    public AudioHelper() {
    }

    private static Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

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

    public static List<Map<String, Object>> getAllAudioFilesFromDevice(final Context context) {
        return getAudioData(context, uri, audioDataColumns, null, null, null);
    }

    public static List<Map<String, Object>> getAudioFilesFromFolder(final Context context, final String folderName) {
        return getAudioData(context, uri, audioDataColumns, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%" + folderName + "%"}, null);
    }

    public static List<Map<String, Object>> getAudioFilesByPurpose(final Context context, final String purpose) {
        return getAudioData(context, uri, audioDataColumns, getAudioPurposeQuery(purpose), null, null);
    }

    public static List<Map<String, Object>> findAudioFileByName(final Context context, final String fileName) {
        return getAudioData(context, uri, audioDataColumns, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%" + fileName + "%"}, null);
    }

    public static List<Map<String, Object>> getAudioData(final Context context, final Uri uri, final String[] dataColumns, final String selection, final String[] arguments, final String sortOrder){
        final List<Map<String, Object>> resultAudioList;

        Cursor cursor = context.getContentResolver()
                .query(
                        uri,
                        dataColumns,
                        selection,
                        arguments,
                        sortOrder
                );

        if (cursor != null) {
            resultAudioList = extractAudioDataFromCursor(cursor);
            cursor.close();
        } else {
            resultAudioList = new ArrayList<>();
        }

        return resultAudioList;
    }


    private static List<Map<String, Object>> extractAudioDataFromCursor(Cursor cursor) {

        final List<Map<String, Object>> tempAudioList = new ArrayList<>();

        while (cursor.moveToNext()) {

            HashMap<String, Object> audioDataMap = new HashMap<>();

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

            tempAudioList.add(audioDataMap);
        }

        return tempAudioList;
    }

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
