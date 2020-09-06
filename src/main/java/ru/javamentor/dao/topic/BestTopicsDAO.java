package ru.javamentor.dao.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javamentor.model.Topic;

@Repository
public interface BestTopicsDAO extends JpaRepository<Topic, Long> {
}
