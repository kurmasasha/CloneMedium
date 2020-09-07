package ru.javamentor.service.habr;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.javamentor.model.HabrArticle;

import java.util.List;

public interface HabrService {
    List<HabrArticle> getAllInterestingTopics();
}

