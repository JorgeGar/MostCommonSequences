package com.jorgegarag.app;

import java.io.*;
import java.util.*;

public class FileHandler {
    private final String splitPattern = "[!\\u0022#$%&()*+,-./:;<=>?@\\u005B\\u005D^_`{|}~\\s\\t\\n\\r]+";

    public void increaseSequenceMapFromFile(File file, int sequence, Map<String, Integer> sequences) throws FileNotFoundException {
        Scanner scan = new Scanner(file).useDelimiter(splitPattern);

        List<String> sequenceBatch = new ArrayList<>();

        for(int i = 0; i < sequence && scan.hasNext(); i++) {
            String word = scan.next().toLowerCase();
            sequenceBatch.add(word);
        }

        String wordSeqResult = String.join(" ",sequenceBatch);
        sequences.put(wordSeqResult, sequences.getOrDefault(wordSeqResult, 0) + 1);

        while(scan.hasNext()) {
            String word = scan.next().toLowerCase();
            sequenceBatch.remove(0);
            sequenceBatch.add(word);

            wordSeqResult = String.join(" ",sequenceBatch);
            sequences.put(wordSeqResult, sequences.getOrDefault(wordSeqResult, 0) + 1);
        }
        scan.close();
    }

    public File generateTempFileFromInputStream(InputStream source) throws IOException {
        File input = new File("input.txt");
        FileWriter fw = new FileWriter(input);
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
}
