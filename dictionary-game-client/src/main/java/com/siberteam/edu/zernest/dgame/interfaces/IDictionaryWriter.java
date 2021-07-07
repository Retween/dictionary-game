package com.siberteam.edu.zernest.dgame.interfaces;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public interface IDictionaryWriter {
    default void writeDictionary(Set<String> dictionary, File outputFile) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String word : dictionary) {
                bw.write(word + "\n");
            }
        }
    }
}
