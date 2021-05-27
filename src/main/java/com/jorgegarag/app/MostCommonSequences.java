package com.jorgegarag.app;

import java.io.*;
import java.util.*;

public class MostCommonSequences
{
    private static final String FILE_ERROR = "Argument '%s' is not a valid file. Discarding it...%n";
    private static final String NO_VALID_FILES = "There was not any valid file. Exiting...";
    private static final int SEQUENCE = 3; // This value could be get from args too.
    private static final int TOP = 100; // This value could be get from args too.

    public static void main( String[] args ) throws IOException {
        List<File> files = new ArrayList<>();
        FileHandler fileHandler = new FileHandler();

        if( args.length < 1 ) {
            files.add(fileHandler.generateTempFileFromInputStream(System.in));
        } else {

            for (String arg : args) {
                File temp = new File(arg);
                if (temp.exists()) {
                    files.add(temp);
                } else {
                    System.err.printf(FILE_ERROR, arg);
                }
            }

            if (files.size() < 1) {
                System.out.println(NO_VALID_FILES);
                return;
            }
        }

        Map<String, Integer> sequences = new HashMap<>();
        for(File file : files) {
            fileHandler.increaseSequenceMapFromFile(file, SEQUENCE, sequences);
        }

        Worker worker = new Worker();
        List<String> topSequences = worker.getTopSequences(sequences, TOP);

        System.out.println(worker.listToFormattedString(topSequences));
    }


}
