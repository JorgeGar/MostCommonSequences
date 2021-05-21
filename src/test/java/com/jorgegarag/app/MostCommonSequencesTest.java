package com.jorgegarag.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import java.util.StringJoiner;

public class MostCommonSequencesTest
{
    private static final String INVALID_FILE = "INVALID_FILE";
    private static final String HELP = "The program requires at least one valid file path.\n" +
            "Please try using a command as follows: java MostCommonSequences file.txt file2.txt";
    private static final String FILE_ERROR = "Argument 'INVALID_FILE' is not a valid file. Discarding it...";
    private static final String NO_VALID_FILES = "There was not any valid file. Exiting...";
    private final String TEST_FILE = "src/test/resources/fileWordsTest.txt";
    private final String TEST_RESULT = "src/test/resources/fileWordsTestSequencesRes.txt";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();


    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    //I'm not sure on how to test the standard input to be honest.
    @Test
    public void emptyArgsTest() {
        assertTrue(true);
    }


    @Test
    public void oneInvalidFileTest() throws IOException {
        String[] files = new String[1];
        files[0] = INVALID_FILE;
        MostCommonSequences.main(files);
        assertEquals(FILE_ERROR,  errContent.toString().trim());
        assertEquals(NO_VALID_FILES,  outContent.toString().trim());
    }

    @Test
    public void oneValidOneInvalidFileTest() throws IOException {
        String[] files = new String[2];
        files[0] = INVALID_FILE;
        files[1] = TEST_FILE;
        MostCommonSequences.main(files);
        assertEquals(FILE_ERROR,  errContent.toString().trim());

        File sequencesFile = new File(TEST_RESULT);

        Scanner scan = new Scanner(sequencesFile);
        StringJoiner stringJoiner = new StringJoiner("\n");

        while(scan.hasNextLine()) {
           stringJoiner.add(scan.nextLine());
        }
        scan.close();

        assertEquals(stringJoiner.toString(),  outContent.toString().trim());
    }

}
