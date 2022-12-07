

package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static String retrieveDescription(String link) throws IOException {
        Document document = Jsoup.connect(link).get();
        Element descriptionElement = document.selectFirst(".basic-section--appearance-vacancy-description");
        return descriptionElement.text();
    }

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 5; i++) {
            Connection connection = Jsoup.connect(PAGE_LINK + "?page=" + i);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                String vacancyDate = row.select(".basic-date").first().attr("datetime");
                LocalDateTime localDateTime = new HabrCareerDateTimeParser().parse(vacancyDate);
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String description = null;
                try {
                    description = retrieveDescription(link);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("%s %s %s %s%n", vacancyName, link, description, localDateTime);
            });
        }
    }
}
