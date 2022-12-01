package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return content(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(data -> data < 0x80);
    }

    private String content(Predicate<Character> filter) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
        String output = "";
        int data;
        while ((data = input.read()) > 0) {
            if (filter.test((char) data)) {
                output += (char) data;
            }
        }
        return output;
    }
}