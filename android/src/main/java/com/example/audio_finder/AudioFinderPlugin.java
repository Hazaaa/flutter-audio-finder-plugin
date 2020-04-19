package com.example.audio_finder;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.audio_finder.AudioHelper.AudioHelper;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * AudioFinderPlugin
 */
public class AudioFinderPlugin implements FlutterPlugin, MethodCallHandler {

    private static Context context;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "audio_finder");
        context = flutterPluginBinding.getApplicationContext();
        channel.setMethodCallHandler(new AudioFinderPlugin());
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old pre-Flutter-1.12 Android projects.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "audio_finder");
        context = registrar.activity().getApplicationContext();
        channel.setMethodCallHandler(new AudioFinderPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("getAllAudioFiles")) {
            result.success(AudioHelper.getAllAudioFilesFromDevice(context));
        } else if (call.method.equals("getAudioFilesFromFolder")) {
            if (!call.hasArgument("folderName")) {
                result.error("ARGUMENT_ERROR", "Argument 'folderName' is missing.", null);
            }
            result.success(AudioHelper.getAudioFilesFromFolder(context, (String) call.argument("folderName")));
        } else if (call.method.equals("getAudioFilesByPurpose")) {
            if (!call.hasArgument("purpose")) {
                result.error("ARGUMENT_ERROR", "Argument 'purpose' is missing.", null);
            }
            result.success(AudioHelper.getAudioFilesByPurpose(context, (String) call.argument("purpose")));
        } else if (call.method.equals("findAudioFileByName")) {
            if (!call.hasArgument("fileName")) {
                result.error("ARGUMENT_ERROR", "Argument 'fileName' is missing.", null);
            }
            result.success(AudioHelper.findAudioFileByName(context, (String) call.argument("fileName")));
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }
}
