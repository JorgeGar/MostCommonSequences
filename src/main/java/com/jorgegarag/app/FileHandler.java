package com.jorgegarag.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileHandler {

    public void MapFromFile(File file, int sequence, Map<String, Integer> sequences) throws FileNotFoundException {

        //This regular expression will have issues handling abbreviations
        //and version marks such as Vol. 1 or V.1, but I'm not sure if this
        //is important for the challenge, and I have decided to focus on the
        //rest of the code, since this could be checked easily using any
        //regex analyzer, such as regexr.com
        //
        //Also, it will consider ' character as a word as long as it is not
        //part of a word. So "he's" or "Smiths'" will be a word, but "hey'" will be too
        //or even "hey ' guy" will be 3 words.
        //I would need to expend more time reading about regular expressions to correct it.
        //Sorry :S
        Scanner scan = new Scanner(file).useDelimiter("[^\\w']+");

        StringJoiner wordSeq = new StringJoiner(" ");
        String prev1 = "";
        String prev2 = "";
        String current  = "";

        if(scan.hasNext()) {
            prev1 = scan.next().toLowerCase();
            wordSeq.add(prev1);
        }

        if(scan.hasNext()) {
            prev2 = scan.next().toLowerCase();
            wordSeq.add(prev2);
        }

        if(scan.hasNext()) {
            current = scan.next().toLowerCase();
            wordSeq.add(current);
        }

        String wordSeqResult = wordSeq.toString();
        sequences.put(wordSeqResult, sequences.getOrDefault(wordSeqResult, 0) + 1);
        wordSeq = new StringJoiner(" ");

        while(scan.hasNext()) {
            String word = scan.next().toLowerCase();
            prev1 = prev2;
            prev2 = current;
            current = word;

            wordSeq.add(prev1)
                    .add(prev2)
                    .add(current);

            wordSeqResult = wordSeq.toString();
            sequences.put(wordSeqResult, sequences.getOrDefault(wordSeqResult, 0) + 1);
            wordSeq = new StringJoiner(" ");
        }
        scan.close();
    }
}
