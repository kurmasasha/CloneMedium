package ru.javamentor.dao.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.model.Topic;

public interface BestTopicsDAO extends JpaRepository<Topic, Long> {
}
