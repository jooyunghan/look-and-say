package com.jooyunghan;

import static com.jooyunghan.Regex.replaceAll;
import static java.lang.String.format;

/**
 * Created by jooyung.han on 7/16/16.
 */
public class ForMain {
    public static void main(String[] args) {
        System.out.println(ant(100));
    }

    private static String ant(int n) {
        String s = "1";
        for (int i = 0; i < n; i++) {
            s = next(s);
        }
        return s;
    }

    public static String next(String s) {
        char c = s.charAt(0);
        int count = 1;
        String result = "";
        for (int i = 1; i < s.length(); i++) {
            if (c == s.charAt(i)) count++;
            else {
                result += count;
                result += c;
                c = s.charAt(i);
                count = 1;
            }
        }
        result += count;
        result += c;
        return result;
    }
}