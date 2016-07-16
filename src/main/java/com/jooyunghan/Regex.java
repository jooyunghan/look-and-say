package com.jooyunghan;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static String replaceAll(String s, String regex, UnaryOperator<String> f) {
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile(regex).matcher(s);
        while (m.find()) {
            m.appendReplacement(sb, f.apply(m.group()));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
