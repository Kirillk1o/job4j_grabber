package ru.job4j.ocp;

import java.util.List;

public class CarShop {
   List<String> shop = List.of("Bmw", "Mercedes", "Honda");

    List<String> getCar() {
        return shop;
    }
}
