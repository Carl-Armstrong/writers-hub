package com.scififics.writershub.models;

import java.util.ArrayList;

public class SearchData {

    public static ArrayList<Chapter> findByTypeAndTerm (String type, String term, Iterable<Chapter> allChapters) {

        ArrayList<Chapter> results = new ArrayList<>();

        for (Chapter chapter : allChapters) {
            results.add(chapter);
        }

        /*
        for each chapter : all chapters {
          if (type.toString.equals(term) && !results.contains(chapter)) {
            results.add(chapter);
          }
        }

        return results;
         */
        return results;
    }
}
