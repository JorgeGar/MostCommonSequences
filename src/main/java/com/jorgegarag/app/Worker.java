package com.jorgegarag.app;

import java.util.*;

public class Worker {

    public List<String> getTopSequences(Map<String, Integer> sequences, int top) {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>((word1, word2) -> {
            int frequency1 = sequences.get(word1);
            int frequency2 = sequences.get(word2);
            int res = frequency1 - frequency2;
            return res == 0 ? word2.compareTo(word1) : res;
        });

        sequences.forEach((key, value) -> {
            priorityQueue.add(key);
            if (priorityQueue.size() > top) {
                priorityQueue.poll();
            }
        });

        List<String> result = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            String element = priorityQueue.poll();
            String formattedElement = new StringBuilder(element)
                    .append(" - ")
                    .append(sequences.get(element))
                    .toString();
            result.add(formattedElement);
        }

        Collections.reverse(result);

        return result;
    }

    public String listToFormattedString(List<String> source){
        StringJoiner stringJoiner = new StringJoiner("\n");
        source.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

}