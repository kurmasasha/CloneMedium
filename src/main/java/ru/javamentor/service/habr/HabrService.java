package ru.javamentor.service.habr;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.javamentor.model.HabrArticle;

import java.util.List;

/**
 * Интерфейс получения лучших статей за сутки с хабра
 *
 * @version 1.0
 * @author Java Mentor
 */

public interface HabrService {
    List<HabrArticle> getAllInterestingTopics();
}

