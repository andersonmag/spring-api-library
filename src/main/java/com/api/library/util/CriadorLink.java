package com.api.library.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class CriadorLink {

    private static Pattern PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    public static String cria(String descricao) {
        String link;

        link = PATTERN.matcher(Normalizer.normalize(descricao, Normalizer.Form.NFD)).replaceAll("");
        link = link.toLowerCase().replaceAll("[^a-zZ-Z1-9]", "-");
        link = link.replaceAll("--", "-");
        return link;
    }

}
