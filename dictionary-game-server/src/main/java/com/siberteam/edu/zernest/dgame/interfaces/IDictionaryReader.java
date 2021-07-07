package com.siberteam.edu.zernest.dgame.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IDictionaryReader {
    default List<String> getDictionary(InputStream inputStream, int allWordsCount) throws IOException {
        List<String> dictionary = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word);
            }
        }
        Collections.shuffle(dictionary);
        return dictionary.subList(0, allWordsCount);
    }
}
