package ru.javamentor.util.buffer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LikeBuffer {
    private static final LikeBuffer LIKEBUFFER = new LikeBuffer();
    private Map<String, Set<Long>> data;

    private LikeBuffer() {
        data = new ConcurrentHashMap<>();
    }

    public static LikeBuffer getInstance() {
        return LIKEBUFFER;
    }

    public boolean canLikeInThisSession(String sessionId, Long topicId) {
        return (data.get(sessionId) == null) || (!data.get(sessionId).contains(topicId));
    }

    public void addLikeToCurrentSession(String sessionId, Long topicId) {
        if (canLikeInThisSession(sessionId, topicId)) {
            if (data.containsKey(sessionId)) {
                data.get(sessionId).add(topicId);
            } else {
                Set<Long> set = new HashSet<>();
                set.add(topicId);
                data.put(sessionId, set);
            }
        }
    }
}
