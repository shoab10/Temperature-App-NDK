#include "com_example_shoab_myproject_MainActivity.h"

#ifndef _MY_LIBRARY
#define _MY_LIBRARY
#ifdef __cplusplus
extern "C" {
#endif

jfloat  Java_com_example_shoab_myproject_MainActivity_cToF
        (JNIEnv * env, jobject obj, jfloat c){
    return ((c*9/5)+32);
}

/*
 * Class:     com_example_shoab_myproject_MainActivity
 * Method:    fToc
 * Signature: (F)F
 */
jfloat  Java_com_example_shoab_myproject_MainActivity_fToc
        (JNIEnv *env, jobject obj, jfloat f){
    return ((f-32)*5/9);
}

/*
 * Class:     com_example_shoab_myproject_MainActivity
 * Method:    cToF
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
jobject Java_com_example_shoab_myproject_MainActivity_celToFar
        (JNIEnv *env, jobject obj, jobject forecasts){
    //Getting the class ArrayList in native code
    jclass java_util_ArrayList;
    jmethodID java_util_ArrayList_;
    jmethodID java_util_ArrayList_size;
    jmethodID java_util_ArrayList_get;
    jmethodID java_util_ArrayList_add;

    java_util_ArrayList      = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    java_util_ArrayList_     = env->GetMethodID(java_util_ArrayList, "<init>", "()V");
    java_util_ArrayList_size = env->GetMethodID (java_util_ArrayList, "size", "()I");
    java_util_ArrayList_get  = env->GetMethodID(java_util_ArrayList, "get", "(I)Ljava/lang/Object;");
    java_util_ArrayList_add  = env->GetMethodID(java_util_ArrayList, "add", "(Ljava/lang/Object;)Z");

    //getting forecast class
    jclass ForecastClass = env->FindClass("com/example/shoab/myproject/model/Forecast");
    jmethodID ForecastClassGetTemp = env->GetMethodID(ForecastClass,"getTemp","()F");
    jmethodID ForecastClassSetTemp = env->GetMethodID(ForecastClass,"setTemp","(F)V");

    //Getting forecast array list length
    jint len = env->CallIntMethod(forecasts, java_util_ArrayList_size);

    jobject result = env->NewObject(java_util_ArrayList, java_util_ArrayList_, len);
    for (jint i=0; i<len; i++) {
        //Getting and modifying each forecast element
        jobject element = (env->CallObjectMethod(forecasts, java_util_ArrayList_get, i));
        jfloat t= env->CallFloatMethod(element,ForecastClassGetTemp);
        t = (t*9/5)+32;
        env->CallVoidMethod(element,ForecastClassSetTemp,t);
        env->CallBooleanMethod(result,java_util_ArrayList_add,element);
//        env->DeleteLocalRef(element);
   }
    return result;
}

/*
 * Class:     com_example_shoab_myproject_MainActivity
 * Method:    fToc
 * Signature: (Ljava/util/List;)Ljava/util/List;
 */
jobject Java_com_example_shoab_myproject_MainActivity_farToCel
        (JNIEnv *env, jobject obj, jobject forecasts){
    //Getting the class ArrayList in native code
    jclass java_util_ArrayList;
    jmethodID java_util_ArrayList_;
    jmethodID java_util_ArrayList_size;
    jmethodID java_util_ArrayList_get;
    jmethodID java_util_ArrayList_add;

    java_util_ArrayList      = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    java_util_ArrayList_     = env->GetMethodID(java_util_ArrayList, "<init>", "()V");
    java_util_ArrayList_size = env->GetMethodID (java_util_ArrayList, "size", "()I");
    java_util_ArrayList_get  = env->GetMethodID(java_util_ArrayList, "get", "(I)Ljava/lang/Object;");
    java_util_ArrayList_add  = env->GetMethodID(java_util_ArrayList, "add", "(Ljava/lang/Object;)Z");


    //Getting forecast class
    jclass ForecastClass = env->FindClass("com/example/shoab/myproject/model/Forecast");
    jmethodID ForecastClassGetTemp = env->GetMethodID(ForecastClass,"getTemp","()F");
    jmethodID ForecastClassSetTemp = env->GetMethodID(ForecastClass,"setTemp","(F)V");

    //Getting forecast array list length
    jint len = env->CallIntMethod(forecasts, java_util_ArrayList_size);

    jobject result = env->NewObject(java_util_ArrayList, java_util_ArrayList_, len);
    for (jint i=0; i<len; i++) {
        //Getting and modifying each forecast element
        jobject element = (env->CallObjectMethod(forecasts, java_util_ArrayList_get, i));
        jfloat t= env->CallFloatMethod(element,ForecastClassGetTemp);
        t = (t-32)*5/9;
        env->CallVoidMethod(element,ForecastClassSetTemp,t);
        env->CallBooleanMethod(result,java_util_ArrayList_add,element);
//        env->DeleteLocalRef(element);
    }
    return result;
}
#ifdef __cplusplus
};
#endif
#endif


