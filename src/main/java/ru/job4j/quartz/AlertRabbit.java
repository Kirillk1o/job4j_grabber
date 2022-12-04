package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/*
 * Класс описывает планировщик для вывода в консоль сообщений с периодичностью 10 секунд.
 * Планировщик реализован с помощью библиотеки Quartz.
 */

public class AlertRabbit {
    private Properties properties;
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
     * @return возвращает значение интервала времени для запуска задачи
     */
    public static int getTimeIntervalFromProperties(Properties properties) {
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Integer.parseInt(properties.getProperty("rabbit.properties"));
    }

    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(10)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    /**
     * Метод реализует задачу, которую надо выполнять периодически
     */
    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
        }
    }
}
