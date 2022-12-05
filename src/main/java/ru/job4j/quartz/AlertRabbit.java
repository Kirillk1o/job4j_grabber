package ru.job4j.quartz;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/*
 * Класс описывает планировщик для вывода в консоль сообщений с периодичностью 10 секунд.
 * Планировщик реализован с помощью библиотеки Quartz.
 */

public class AlertRabbit {
    private static final Logger LOGGER = LogManager.getLogger(AlertRabbit.class.getName());
    /*
     * В методе main реализована работа планировщика
     * sheduler: добавление задачи, которые хотим выполнять с периодичностью
     * job: задача с передачей в нее класса с описанием требуемых действий для выполнения
     * times: расписание. Описано с какой периодичностью будет настроен запуск
     * trigger: триггера - когда начинать запуск (сразу), с какой периодичностью
     * загрузка задачи и триггера в планировщик
     */

    /*
     * метод реализует чтение файла с настройками
     * @return возвращает значение интервала времени для запуска задачи, которое описано в файле
     */
    public static Properties getTimeIntervalFromProperties() {
        Properties properties;
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private static Connection initConection(Properties properties) {
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            properties.load(in);
            Class.forName(properties.getProperty("driver_class"));
            return DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) {
        try {
            Properties properties = getTimeIntervalFromProperties();
            Connection connection = initConection(properties);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(getTimeIntervalFromProperties().getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод реализует задачу, которую надо выполнять периодически
     */
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into rabbit (created_date) values (?)")) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.execute();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
