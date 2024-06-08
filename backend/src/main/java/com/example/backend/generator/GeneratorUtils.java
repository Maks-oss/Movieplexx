package com.example.backend.generator;

import java.util.List;
import java.util.Random;

public class GeneratorUtils {

    public static <T> T getRandomElement(List<T> list, T previousElement) {
        Random rand = new Random();
        T newElement;
        do {
            newElement = list.get(rand.nextInt(list.size()));
        } while (newElement.equals(previousElement) && list.size() > 1);
        return newElement;
    }
}
