package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    /*
    метод парсит 5 страниц
    c названием вакансии, ссылкой на вакансию, описанием и датой размещения.
     */
    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link + "?page=" + i).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            /*
            получаем все вакансии страницы
             */
            Elements rows = doc.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                String vacancyName = titleElement.text();
                String linkString = String.format("%s%s", SOURCE_LINK, titleElement.attr("href"));
                String description = retrieveDescription(linkString);
                String vacancyDate = row.select(".bacic-date").first().attr("datetime");
                list.add(new Post(vacancyName, linkString, description, this.dateTimeParser.parse(vacancyDate)));
            });
        }
        return list;
    }

    /*
    метод для загрузки деталей объявления (полное описание)
     */
    private String retrieveDescription(String link) {
        Document document = null;
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements descriptionElement = document.select(".style-ugc");
        return descriptionElement.text();
    }
}
