package com.scififics.writershub.models;

import java.util.ArrayList;

public class StoryData {

    /*
        * Returns the results of searching the story data by field and search term
        *
        * @param column Story category to be searched
        * @param value Value of the category to be searched for
        * @param allStories the list of stories to search
        * @return List of all stories matching the criteria
     */
    public static ArrayList<Story> findByColumnAndValue (String column, String value, Iterable<Story> allStories) {

        ArrayList<Story> results = new ArrayList<>();
        if (value.toLowerCase().equals("all")){
            return (ArrayList<Story>) allStories;
        }

        if (column.equals("all")){
            results = findByValue(value, allStories);
            return results;
        }

        for (Story story : allStories) {

            String aValue = getFieldValue(story, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(story);
            }
        }

        return results;
    }

    public static String getFieldValue(Story story, String fieldName) {
        String theValue;
        if (fieldName.equals("title")){
            theValue = story.getTitle();
        } else {
            theValue = story.getWorld().toString();
        }

        return theValue;
    }

    /*
        * Search all Story fields for the given term
        *
        * @param value The search term
        * @param allStories The list of stories
        * @return List of all stories with at least one field containing the value
     */
    public static ArrayList<Story> findByValue(String value, Iterable<Story> allStories) {

        ArrayList<Story> results = new ArrayList<>();

        for (Story story : allStories) {

            if (story.getTitle().toLowerCase().contains(value.toLowerCase())) {
                results.add(story);
            } else if (story.getWorld().toString().toLowerCase().contains(value.toLowerCase())) {
                results.add(story);
            }
        }

        return results;
    }

}
