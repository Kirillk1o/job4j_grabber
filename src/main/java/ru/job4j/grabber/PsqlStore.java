package ru.job4j.grabber;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {
    private static final Logger LOGGER = LogManager.getLogger(PsqlStore.class.getName());
    private Connection connection;

    /*
     метод подключения к бд.
     */
    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver_class"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            connection = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("login"),
                    cfg.getProperty("password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    сохраняет объявление в бд.
     */
    @Override
    public void save(Post post) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into post(name, link, discription, created) values (?, ?, ?, ?) on colflict (link) do nothing",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getLink());
            statement.setString(3, post.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /*
    метод извлечения объявления из бд.
     */
    @Override
    public List<Post> getAll() {
        List<Post> post = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    post.add(getPost(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return post;
    }

    /*
    метод получения всех результатов (колонок) находящиеся в таблице post
     */
    private Post getPost(ResultSet resultSet) {
        try {
            return new Post(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("link"),
                    resultSet.getString("discription"),
                    resultSet.getTimestamp("created").toLocalDateTime());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    метод извлечения объявления из базы по id.
     */
    @Override
    public Post findById(int id) {
        Post result = null;
        try (PreparedStatement statement =
                     connection.prepareStatement("select * from post where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = getPost(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) {
        Properties config = new Properties();
        try (InputStream in = PsqlStore.class.getClassLoader().
                getResourceAsStream("rabbit.properties");) {
            config.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (PsqlStore psql = new PsqlStore(config)) {
            Post post = new Post("Java разработчик в Tinkoff Black", "https://career.habr.com/vacancies/1000107412",
                    "Tinkoff Black — ядро экосистемы Тинькофф банка. Именно с этого направления начинается знакомство пользователя с продуктами компании.",
                    LocalDateTime.now());
            psql.save(post);
            System.out.println(psql.findById(post.getId()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
