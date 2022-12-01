package ru.job4j.io;

import java.io.*;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
        for (int i = 0; i < content.length(); i += 1) {
            output.write(content.charAt(i));
        }
    }
}
