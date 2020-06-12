package com.scififics.writershub.models;

import java.util.ArrayList;

public class SearchData {

    /*
        Searches all chapters by type and term

        @param type - Determines what field is being searched. For example, title or description
        @param term - The search term provided.
        @param allChapters - A list of all chapters to be searched
        @return - A list of all chapters for which the term is included in the type
     */
    public static ArrayList<Chapter> findByTypeAndTerm (String type, String term, Iterable<Chapter> allChapters) {

        ArrayList<Chapter> results = new ArrayList<>();

        if (type.equals("tags")) {
            results = findByTag(term, allChapters);
            return results;
        }

        for (Chapter chapter : allChapters) {

            String aValue = getFieldValue(chapter, type);

            if (aValue != null && aValue.toLowerCase().contains(term.toLowerCase())) {
                results.add(chapter);
            }

        }

        return results;
    }

    /*
        Gets the value of the field

        @param chapter - the chapter which contains the field needed
        @param fieldName - the field which holds the needed data
        @return - the toString of the field
     */
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

    /*
        Gets a list of chapters associated with the searched tag

        @param term - the search term
        @allChapters - a list of chapters to be searched
        @return - a list of chapters tagged by the search term
     */
    public static ArrayList<Chapter> findByTag (String term, Iterable<Chapter> allChapters) {

        ArrayList<Chapter> results = new ArrayList<>();

        for (Chapter chapter : allChapters) {
            if (chapter.getTags() != null) {
                for (Tag tag : chapter.getTags()) {
                    if (tag.toString().toLowerCase().contains(term.toLowerCase()) && !results.contains(chapter)) {
                        results.add(chapter);
                    }
                }
            }
        }
        return results;
    }
}
