package com.scififics.writershub.models;

import java.util.ArrayList;

public class SearchData {

    public static ArrayList<Chapter> findByTypeAndTerm (String type, String term, Iterable<Chapter> allChapters) {

        ArrayList<Chapter> results = new ArrayList<>();

        for (Chapter chapter : allChapters) {

            String aValue = getFieldValue(chapter, type);

            if (aValue != null && aValue.toLowerCase().contains(term.toLowerCase())) {
                results.add(chapter);
            }

        }

        return results;
    }

    public static String getFieldValue(Chapter chapter, String fieldName) {
        String theValue;
        if (fieldName.equals("title")){
            theValue = chapter.getTitle();
        } else if (fieldName.equals("description")){
            theValue = chapter.getDescription();
        } else {
            theValue = chapter.getContent();
        }

        return theValue;
    }
}
