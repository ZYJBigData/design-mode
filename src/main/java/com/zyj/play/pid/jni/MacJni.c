#include <stdio.h>
#include "MacJni.h"

JNIEXPORT void Java_MacJni_sayHello(JNIEnv * env, jobject obj){
    printf("hello from jni");
}