package com.jorgegarag.app;

import java.util.*;

public class Worker {


    //This is an approach that uses the priority queue to enhance the time complexity
    //from the simpler "hashmap" approach. As we keep the priority queue with only
    //the top K elements, the time complexity results on O(N * log(K)); and, as we
    //still use the hashmap that needs to contain all the elements, the space complexity
    //is linear.
    //
    //On the other hand, as it is not defined which element will be ranked higher in
    //case of same frequency, I decided to use the alphabetical order, which, again,
    //should be the most common way to do it.
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
        /* loop alternative implementation.
        for(Map.Entry<String, Integer> sequence : sequences.entrySet()) {
            priorityQueue.add(sequence.getKey());
            if(priorityQueue.size() > top) {
                priorityQueue.poll();
            }
        }*/

        List<String> result = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            String element = priorityQueue.poll();
            String formattedElement = new StringBuilder(element)
                    .append(" - ")
                    .append(sequences.get(element))
                    .toString();
            result.add(formattedElement);
        }

        //It is not asked to return the list in a sorted way,
        //but it is commonly seen as a better result if the
        //results are listed from the most frequent to the least
        Collections.reverse(result);

        return result;
    }

    public String listToFormattedString(List<String> source){
        StringJoiner stringJoiner = new StringJoiner("\n");
        source.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

}