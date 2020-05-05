package com.scififics.writershub.models;

import java.util.ArrayList;

public class ChapterData {

    /*
        * Returns the results of searching the chapter data by field and search term.
        *
        * @param column Chapter category to be searched
        * @param value Value of the category to be searched for
        * @param allChapters the list of chapters to search
        * @return List of all chapters matching the criteria
     */
    public static ArrayList<Chapter> findByColumnAndValue(String column, String value, Iterable<Chapter> allChapters) {

        ArrayList<Chapter> results = new ArrayList<>();
        if (value.toLowerCase().equals("all")){
            return (ArrayList<Chapter>) allChapters;
        }

        if (column.equals("all")){
            results = findByValue(value, allChapters);
            return results;
        }

        for (Chapter chapter : allChapters) {

            String aValue = getFieldValue(chapter, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(chapter);
            }
        }

        return results;
    }

    public static String getFieldValue(Chapter chapter, String fieldName) {
        String theValue;
        if (fieldName.equals("title")){
            theValue = chapter.getTitle();
        } else {
            theValue = chapter.getStory().toString();
        }

        return theValue;
    }

    /*
        * Search all Chapter fields for the given term
        *
        * @param value The search term
        * @param allChapters The list of chapters
        * @return List of all chapters with at least one field containing the value
     */

    public static ArrayList<Chapter> findByValue(String value, Iterable<Chapter> allChapters) {

        ArrayList<Chapter> results = new ArrayList<>();

        for (Chapter chapter : allChapters) {

            if (chapter.getTitle().toLowerCase().contains(value.toLowerCase())) {
                results.add(chapter);
            } else if (chapter.getStory().toString().toLowerCase().contains(value.toLowerCase())) {
                results.add(chapter);
            }
        }

        return results;
    }
}
