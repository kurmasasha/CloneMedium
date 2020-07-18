package ru.javamentor.util.buffer;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DislikeBuffer {
    private Map<String, Set<Long>> data = new ConcurrentHashMap<>();

    public boolean isDislikedTopic(String sessionId, Long topicId) {
        if (data.get(sessionId) != null) {
            return data.get(sessionId).contains(topicId);
        }
        return false;
    }

    public void addDislike(String sessionId, Long topicId) {
        if (data.containsKey(sessionId)) {
            data.get(sessionId).add(topicId);
        } else {
            Set<Long> set = new HashSet<>();
            set.add(topicId);
            data.put(sessionId, set);
        }

    }
    public void deleteDislike(String sessionId, Long topicId) {
        data.get(sessionId).remove(topicId);
    }
}

