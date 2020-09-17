package ru.javamentor.service.habr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.javamentor.model.HabrArticle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация интерфейса получения лучших статей за сутки с хабра
 *
 * @version 1.0
 * @autor Java Mentor
 */

@Service
public class HabrServiceImpl implements HabrService {
    @Override
    public List<HabrArticle> getAllInterestingTopics() {


        //список линков на статьи
        List<String> linkList = new ArrayList<>();

        //список страниц для парсинга линков статей
        List<String> pageList = new ArrayList<>();

        AtomicInteger ArticleId = new AtomicInteger();

        List<HabrArticle> ArticleList = new ArrayList<>();
        String linksPage = "https://habr.com/ru/top/page1/";
        pageList.add(linksPage);
        pageList.forEach(page -> {
            try {
                Document pageDoc = Jsoup.connect(page)
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
                Elements articles = pageDoc.getElementsByAttributeValueContaining("class", "post_preview");
                articles.forEach(article -> {
                    Element dirtyTitle = article.child(1);
                    if (linkList.size() < 10) {
                        linkList.add(dirtyTitle.child(0).absUrl("href"));
                    }
                });
                linkList.removeAll(Arrays.asList(null, ""));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        linkList.forEach(link -> {
            if (link != null) {
                try {
                    Document docLink = Jsoup.connect(link)
                            .userAgent("Chrome/4.0.249.0 Safari/532.5")
                            .referrer("http://www.google.com")
                            .get();
                    Elements articles = docLink.getElementsByAttributeValueContaining("class", "post__wrapper");
                    articles.forEach(article -> {
                        Element dirtyTitle = article.child(0);

                        //Картинка из статьи
                        String clearImageUrl = "";
                        if (dirtyTitle.select("img").first() != null) {
                            clearImageUrl = dirtyTitle.select("img").first().absUrl("src");
                        }

                        //Имя из статьи
                        String clearAuthorName = dirtyTitle.child(0).text();

                        //Время публикации
                        String clearPostTime = dirtyTitle.child(1).text();

                        //Заголовок публикации
                        String clearPostTitle = article.child(1).text();

                        //Текст статьи
                        String clearPostArticle = article.child(4).text();

                        HabrArticle habrArticle = new HabrArticle(ArticleId, clearPostTitle, clearAuthorName, clearPostTime, clearPostArticle, clearImageUrl);
                        ArticleId.getAndIncrement();

                        ArticleList.add(habrArticle);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return ArticleList;
    }

}


