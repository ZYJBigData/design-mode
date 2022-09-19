package com.zyj.play.pid.jni;

public class MacJni {

    public native void sayHello();

    static {
        System.load("/Users/yingjiezhang/IdeaProjects/design-mode/src/main/java/com/zyj/play/pid/jni/libmacjni.jnilib");
    }


    public static void main(String[] args) {
        MacJni macJni = new MacJni();
        macJni.sayHello();

    }
}