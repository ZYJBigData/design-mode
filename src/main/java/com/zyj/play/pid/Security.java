package com.zyj.play.pid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Security {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("nacosZYJ"));
    }
}
