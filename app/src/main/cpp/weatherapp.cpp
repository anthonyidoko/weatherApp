// Write C++ code here.
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_weatherapp_data_repository_WeatherRepoImpl_getApiKey(JNIEnv *env, jobject object) {
    std::string key = "e3488bd2786f01af0ae501cbda4ab156";
    return env->NewStringUTF(key.c_str());
}

// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("weatherapp");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("weatherapp")
//      }
//    }