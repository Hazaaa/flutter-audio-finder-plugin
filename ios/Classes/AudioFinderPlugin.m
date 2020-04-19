#import "AudioFinderPlugin.h"
#if __has_include(<audio_finder/audio_finder-Swift.h>)
#import <audio_finder/audio_finder-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "audio_finder-Swift.h"
#endif

@implementation AudioFinderPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAudioFinderPlugin registerWithRegistrar:registrar];
}
@end
