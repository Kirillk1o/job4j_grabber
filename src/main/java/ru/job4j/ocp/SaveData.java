package ru.job4j.ocp;

/*
нарушение принципа OCP происходит по причине того,
что если нам придется добавить еще 5 методов то нужно будет вносить изменения в класс целиком
для решения этой проблемы нужно создать отдельный интерфейс SaveInterface, где будет указан абстрактный метод void save();
и этот интерфейс мы будем имплементить в отдельные классы для работы с сохранением.
 */
public class SaveData {

    public void saveDataToFile() {
        System.out.println("Сохранение файла");
    }

    public void saveToDb() {
        System.out.println("Сохранение в БД");
    }
}
