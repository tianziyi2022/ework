package com.hebutgo.ework;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Test {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toString().replace('T',' '));
    }
}
