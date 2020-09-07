package ru.javamentor.service.topic.best;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.dao.topic.best.BestTopicsDAO;
import ru.javamentor.model.Topic;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация BestTopicsService
 *
 * @author Java Mentor
 * @version 1.0
 */
@Service
public class BestTopicsServiceImpl implements BestTopicsService {
    private final BestTopicsDAO bestTopicsDAO;

    @Autowired
    public BestTopicsServiceImpl(BestTopicsDAO bestTopicsDAO) {
        this.bestTopicsDAO = bestTopicsDAO;
    }

    @Transactional
    @Override
    public List<Topic> bestFive() {
        return bestTopicsDAO.findAll(Sort.by(Sort.Direction.DESC, "likes"))
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }
}
