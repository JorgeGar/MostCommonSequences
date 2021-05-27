package com.jorgegarag.app;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private final String TEST_SEQUENCES = "src/test/resources/fileWordsTestSequencesFreq.txt";
    private final String TEST_FILE = "src/test/resources/fileWordsTest.txt";
    private final int TEST_SEQUENCE = 3;
    private final FileHandler fileHandler = new FileHandler();

    @Test
    public void MapFromFileTest() throws FileNotFoundException {

        Map<String, Integer> expected = new HashMap<>();
        File sequencesFile = new File(TEST_SEQUENCES);

        Scanner scan = new Scanner(sequencesFile);

        while(scan.hasNextLine()) {
            String frequency = "1";
            String sequence = scan.nextLine();
            if(scan.hasNextLine()) {
                frequency = scan.nextLine();
            }
            expected.put(sequence, Integer.parseInt(frequency) );
        }
        scan.close();


        File testFile = new File(TEST_FILE);

        Map<String, Integer> result = new HashMap<>();

        fileHandler.increaseSequenceMapFromFile(testFile, TEST_SEQUENCE, result);

        assertEquals(expected.size(), result.size());
        for(String check : expected.keySet()) {
            assertTrue(result.containsKey(check));
            assertEquals(expected.get(check), result.get(check));
        }
    }

    @Test
    public void generateTempFileFromInputStreamTest() throws IOException {
        File testFile = new File(TEST_FILE);
        InputStream testInput = new FileInputStream(testFile);
        File resultFile = fileHandler.generateTempFileFromInputStream(testInput);
        Scanner testFileScan =  new Scanner(testFile);
        Scanner resultFileScan = new Scanner(resultFile);
        while(testFileScan.hasNextLine()) {
            String testLine = testFileScan.nextLine().trim();
            String resultLine = resultFileScan.hasNextLine() ? resultFileScan.nextLine().trim() : "FAIL";
            assertEquals(testLine, resultLine);
        }
    }
}
