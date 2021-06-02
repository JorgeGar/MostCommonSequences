package com.jorgegarag.app;

import java.io.*;
import java.util.*;

public class FileHandler {
    private final String splitPattern = "[!\\u0022#$%&()*+,-./:;<=>?@\\u005B\\u005D^_`{|}~\\s\\t\\n\\r]+";
    private LinkedList<String> sequenceBatch;
    private Scanner scan;

    public void increaseSequenceMapFromFile(File file, int sequence, Map<String, Integer> sequences) throws FileNotFoundException {
        scan = new Scanner(file).useDelimiter(splitPattern);

        sequenceBatch = new LinkedList<>();

        initFirstSequence(sequence, sequences);

        scan.forEachRemaining( nextWord -> {
            sequenceBatch.removeFirst();
            sequenceBatch.add(nextWord.toLowerCase());

            String wordSeqResult = String.join(" ", sequenceBatch);
            sequences.put(wordSeqResult, sequences.getOrDefault(wordSeqResult, 0) + 1);
            }
        );
        scan.close();
    }

    public File generateTempFileFromInputStream(InputStream source) throws IOException {
        File input = new File("input.txt");
        OutputStreamWriter fw = new FileWriter(input);
        Scanner scanner = new Scanner(source);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            fw.write(line);
            fw.write(System.lineSeparator());
        }
        scanner.close();
        fw.close();
        return input;
    }

    private void initFirstSequence(int sequence, Map<String, Integer> sequences) {
        for(int i = 0; i < sequence && scan.hasNext(); i++) {
            String word = scan.next().toLowerCase();
            sequenceBatch.add(word);
        }

        String wordSeqResult = String.join(" ",sequenceBatch);
        sequences.put(wordSeqResult, sequences.getOrDefault(wordSeqResult, 0) + 1);
    }
}
