package com.jooyunghan;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jooyunghan.Regex.replaceAll;
import static java.lang.String.format;

/**
 * Created by jooyung.han on 7/15/16.
 */
public class RegexMain {
    public static void main(String[] args) {
        System.out.println(ant(10));
    }

    private static String ant(int n) {
        String s = "1";
        for (int i = 0; i < n; i++) {
            System.out.println(s);
            s = next(s);
        }
        return s;
    }

    public static String next(String s) {
        return replaceAll(s, "(.)\\1*", g -> format("%d%c", g.length(), g.charAt(0)));
    }
}

