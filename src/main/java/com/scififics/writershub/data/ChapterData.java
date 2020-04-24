package com.scififics.writershub.data;

import com.scififics.writershub.models.Chapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChapterData {

    private static final Map<Integer, Chapter> chapters = new HashMap<>();

    public static void add(Chapter chapter) {
        chapters.put(chapter.getId(), chapter);
    }

    public static Chapter getById(int id) {
        return chapters.get(id);
    }

    public static Collection<Chapter> getAll() {
        return chapters.values();
    }

    public static void remove(int id) {
        chapters.remove(id);
    }

}
