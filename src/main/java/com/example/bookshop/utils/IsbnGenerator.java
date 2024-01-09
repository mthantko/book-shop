package com.example.bookshop.utils;

import java.util.Random;

public class IsbnGenerator {

    public static String generate() {
        return "ISBN-" + (new Random().nextInt(1000) +
                            1000);
    }
}
