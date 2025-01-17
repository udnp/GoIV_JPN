package com.kamron.pogoiv.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * Returns the normalized string for simplifying characters to detect pokemons.
     *
     * Actually scanned character result quality is often not enough in some reasons like following:
     *  - device screen resolution
     *  - font properties(size, forms and ligature)
     *  - letter complexity in some locales
     *
     * This method provides normalized string to simplify characters.
     * For examples, special characters such as â, é, ば etc are replaced with their normalized forms a, e, は etc.
     * So they can be compared more simply.
     *
     */
    public static String normalize(String s) {
        s = s.toLowerCase();
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{M}]", "");

        /* append more normalizers below for each locales, if needed */
        String lang = Locale.getDefault().getLanguage();

        if (lang.contains("ja")) {
            s = toNormalSizeLetters(s);
        }

        return s;
    }

    private static String toNormalSizeLetters(String s) {
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("[ぁぃぅぇぉっゃゅょァィゥェォッャュョ]");
        Matcher matcher = p.matcher(s);

        while (matcher.find()) {
            char c = matcher.group().charAt(0);
            c = (char)(c + ('あ' - 'ぁ'));
            matcher.appendReplacement(sb, String.valueOf(c));
        }
        s = matcher.appendTail(sb).toString();
        return s;
    }
}
