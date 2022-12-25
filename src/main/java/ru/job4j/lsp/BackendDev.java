package ru.job4j.lsp;
/*
В данном случаем нарушение принципы lsp состоит в том, что мы указали не все рализации род класса.
Класс должен обеспечить такой же функционал как и в род классе +
расширять его функционал
 */
public class BackendDev extends Developer {

    public void writecode() {
        System.out.println("Пишу код");
    }

    public void readDocumentation() {
        System.out.println("Читаю документацию");
    }

    public void sendQueryToDB() {
        System.out.println("отправляю запрос в бд");
    }
}
