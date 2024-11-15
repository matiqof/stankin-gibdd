package com.example.stankingibdd.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoder {

    public static String encodeUrl(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }
}