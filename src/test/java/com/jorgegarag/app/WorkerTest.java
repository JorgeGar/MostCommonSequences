package com.jorgegarag.app;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.assertEquals;


public class WorkerTest {
    private final String TEST_SEQUENCES = "src/test/resources/fileWordsTestSequencesFreq.txt";
    private final int TEST_TOP_ELEMENTS = 1;

    private Worker worker = new Worker();

    @Test
    public void getTopSequencesTest() throws FileNotFoundException {
        List<String> sequences = new ArrayList<>();
        List<Integer> frequencies = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        File sequencesFile = new File(TEST_SEQUENCES);

        Scanner scan = new Scanner(sequencesFile).useDelimiter("[-\\t\\r\\n]+");

        int top = 0;
        while(scan.hasNext()) {
            String frequency = "1";
            String sequence = scan.next();
            if(scan.hasNext()) {
                frequency = scan.next();
            }
            sequences.add(sequence);
            frequencies.add( Integer.parseInt(frequency) );
            map.put(sequence, Integer.parseInt(frequency) );
            top++;
        }
        scan.close();

        List<String> actual = worker.getTopSequences(map, TEST_TOP_ELEMENTS);
        assertEquals(TEST_TOP_ELEMENTS, actual.size());
        int i = 0;
        for( String element : actual ) {
            assertEquals(sequences.get(i)+" - "+frequencies.get(i),element);
            i++;
        }
    }

    @Test
    public void listToFormattedStringTest() {
        List<String> singleton = Collections.singletonList("TEST");
        assertEquals("TEST", worker.listToFormattedString(singleton));

        List<String> expected = new ArrayList<>();
        expected.add("TEST");
        expected.add("A full");
        expected.add("list");

        String expectedString = "TEST\nA full\nlist";
        assertEquals(expectedString, worker.listToFormattedString(expected));

    }
}
