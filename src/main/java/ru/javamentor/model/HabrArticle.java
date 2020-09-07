package ru.javamentor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
@Getter
@Setter
public class HabrArticle {
    private AtomicInteger id;
    private String title;
    private String author;
    private String date;
    private String text;
    private String img;

    public HabrArticle(AtomicInteger id, String title, String author, String date, String text, String img) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.date = date;
    this.text = text;
    this.img = img;
    }
}
