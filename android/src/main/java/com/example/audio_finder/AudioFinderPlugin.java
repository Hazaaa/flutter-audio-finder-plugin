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
        switch (call.method) {
            case "getAllAudioFiles":
                result.success(AudioHelper.getAllAudioFilesFromDevice(context));
                break;
            case "getAudioFilesFromFolder":
                if (!call.hasArgument("folderName")) {
                    result.error("ARGUMENT_ERROR", "Argument 'folderName' is missing.", null);
                }
                result.success(AudioHelper.getAudioFilesFromFolder(context, (String) call.argument("folderName")));
                break;
            case "getAudioFilesByPurpose":
                if (!call.hasArgument("purpose")) {
                    result.error("ARGUMENT_ERROR", "Argument 'purpose' is missing.", null);
                }
                result.success(AudioHelper.getAudioFilesByPurpose(context, (String) call.argument("purpose")));
                break;
            case "findAudioFileByName":
                if (!call.hasArgument("fileName")) {
                    result.error("ARGUMENT_ERROR", "Argument 'fileName' is missing.", null);
                }
                result.success(AudioHelper.findAudioFileByName(context, (String) call.argument("fileName")));
                break;
            case "getAudioFilesBySize":
                if (!call.hasArgument("size")) {
                    result.error("ARGUMENT_ERROR", "Argument 'size' is missing.", null);
                }
                if (!call.hasArgument("moreThan")) {
                    result.error("ARGUMENT_ERROR", "Argument 'moreThan' is missing.", null);
                }
                result.success(AudioHelper.getAudioFilesBySize(context, (String) call.argument("size"), (boolean) call.argument("moreThan")));
                break;
            case "getAudioFilesByLength":
                if (!call.hasArgument("length")) {
                    result.error("ARGUMENT_ERROR", "Argument 'length' is missing.", null);
                }
                if (!call.hasArgument("longerThan")) {
                    result.error("ARGUMENT_ERROR", "Argument 'longerThan' is missing.", null);
                }
                result.success(AudioHelper.getAudioFilesBySize(context, (String) call.argument("length"), (boolean) call.argument("longerThan")));
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }
}
