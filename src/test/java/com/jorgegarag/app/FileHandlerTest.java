package com.jorgegarag.app;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileHandlerTest {
    private final String TEST_SEQUENCES = "src/test/resources/fileWordsTestSequencesFreq.txt";
    private final String TEST_FILE = "src/test/resources/fileWordsTest.txt";
    private final int TEST_SEQUENCE = 3;

    @Test
    public void MapFromFileTest() throws FileNotFoundException {

        Map<String, Integer> expected = new HashMap<>();
        File sequencesFile = new File(TEST_SEQUENCES);

        Scanner scan = new Scanner(sequencesFile).useDelimiter("[-\\t\\r\\n]+");

        while(scan.hasNext()) {
            String frequency = "1";
            String sequence = scan.next();
            if(scan.hasNext()) {
                frequency = scan.next();
            }
            expected.put(sequence, Integer.parseInt(frequency) );
        }
        scan.close();


        File testFile = new File(TEST_FILE);
        FileHandler fileHandler = new FileHandler();

        Map<String, Integer> result = new HashMap<>();

        fileHandler.MapFromFile(testFile, TEST_SEQUENCE, result);

        assertEquals(expected.size(), result.size());
        for(String check : expected.keySet()) {
            assertTrue(result.containsKey(check));
            assertEquals(expected.get(check), result.get(check));
        }
    }
}
