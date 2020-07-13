package ru.javamentor.util.buffer;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LikeBuffer {

    private Map<String, Set<Long>> data = new ConcurrentHashMap<>();

    public boolean isLikedTopic(String sessionId, Long topicId) {
        if (data.get(sessionId) != null) {
            return data.get(sessionId).contains(topicId);
        }
        //Тестовв
        return false;
    }

    public void addLike(String sessionId, Long topicId) {
            if (data.containsKey(sessionId)) {
                data.get(sessionId).add(topicId);
            } else {
                Set<Long> set = new HashSet<>();
                set.add(topicId);
                data.put(sessionId, set);
            }

    }
    public void deleteLike(String sessionId, Long topicId) {
        data.get(sessionId).remove(topicId);
    }
}
