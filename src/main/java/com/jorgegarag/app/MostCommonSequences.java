package com.jorgegarag.app;

import java.io.*;
import java.util.*;

public class MostCommonSequences
{
    private static final String FILE_ERROR = "Argument '%s' is not a valid file. Discarding it...%n";
    private static final String NO_VALID_FILES = "There was not any valid file. Exiting...";
    private static final int SEQUENCE = 3; // This value could be get from args too.
    private static final int TOP = 100; // This value could be get from args too.

    //TODO: Add a mechanism to avoid infinite loop if no arguments nor standard input is provided.
    public static void main( String[] args ) throws IOException {
        List<File> files = new ArrayList<>();
        if( args.length < 1 ) {
            File input = new File("input.txt");
            FileWriter fw = new FileWriter(input);
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()) {
                fw.write(scanner.nextLine());
            }
            scanner.close();
            fw.close();

            files.add(input);
        } else {

            //I split the functionality in two loops for the sake of code readability,
            // since it is not expected to have a large amount of arguments.
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

        //As it was not specified if the most common sequences, when providing multiple books,
        //should be treated separately or as a hole group, I decided to implement this second approach,
        //since it is a bit more complex. Nevertheless, this is also more memory consuming, since all
        //words will be stored in the same Hash. Treating them separately, on the other hand, will be
        //slightly easier, but would clean the hash each time, so it will consume less memory.
        //
        //I understand that if we allow multiple sources, it makes sense to analyze them as a whole,
        //because if you would only one to treat them separately, you can always run this in different
        //containers and parallelize the execution to improve the timings.
        FileHandler fileHandler = new FileHandler();
        Map<String, Integer> sequences = new HashMap<>();
        for(File file : files) {
            fileHandler.MapFromFile(file, SEQUENCE, sequences);
        }

        Worker worker = new Worker();
        List<String> topSequences = worker.getTopSequences(sequences, TOP);

        System.out.println(worker.listToFormattedString(topSequences));
    }
}
