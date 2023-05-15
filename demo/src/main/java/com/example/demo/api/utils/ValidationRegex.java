package com.example.demo.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {

    private static final Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean isRegexEmail(String target) {

        Matcher matcher = emailRegex.matcher(target);
        return matcher.find();
    }
}

