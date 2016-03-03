# Temperature-App-NDK
An android app that displays temperature values for 5 days (Mon-Fri) and ambient temperature and converts the temperatures using JNI NDK.

### Features
- Minimum SDK 21
- Generates random list of temperature on activity
- Temperature conversion from celcius to fahrenheit on click of a button
- Displays ambient temperature on top of acitvity using SensorManager
- JNI native code is in C++
- JNI methods accept list of temperature (Class Forecast) values and return the converted List
- Generated .so file in (/app/src/main/libs)


### Build tools:
- Android Studio
- Android target SDK 23 and min SDK 21
- Gradle 2.8
- Android Plugin 1.5.0
- Android NDK

### Commands to run
- clean build in Android Studio
- build
- ndk-build on the commandline to generate .so files the libs folder (/app/src/main/libs)
- run the app in Android Studio
