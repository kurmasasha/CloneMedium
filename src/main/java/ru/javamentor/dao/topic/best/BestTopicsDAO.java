package ru.javamentor.dao.topic.best;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javamentor.model.Topic;

/**
 * Repository для получения статей набравших
 * наибольшее количество лайков
 *
 * @author Java Mentor
 * @version 1.0
 */
@Repository
public interface BestTopicsDAO extends JpaRepository<Topic, Long> {
}
