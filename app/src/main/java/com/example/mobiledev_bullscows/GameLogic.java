package com.example.mobiledev_bullscows;
//Основа - ввод 4 цифр без повторений + проверка догадки игрока(посчитать быков и коров)

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {
    private String secretNumber; //Загаданное число в формате строки, для ноля

    //Конструктор для вызова при создании
    public GameLogic() {
        generateSecretNumber();
    }

    //Метод герерации числа
    private void generateSecretNumber() {
        // Создаём список из всех цифр от 0 до 9
        List<Character> digits = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) {
            digits.add(c);
        }

        // Перемешивание списка цифр, импользуем для гарантии возможности начал с0 и отсутвия повторений цифр
        Collections.shuffle(digits);
        secretNumber = "" + digits.get(0) + digits.get(1) + digits.get(2) + digits.get(3);
    }
    //геттер для просмотра загаданного числа(проверка)
    public String getSecretNumber() {
        return secretNumber;
    }


    //Основная логика проверки (бык хит, корова попадание)
    public int[] checkGuess(String guess) {
        int bulls = 0; // количество быков
        int cows = 0;  // количество коров

        // Проверка 4х позиций
        for (int i = 0; i < 4; i++) {
            char guessChar = guess.charAt(i);     // цикл для проверки цифры её позиции
            char secretChar = secretNumber.charAt(i);

            if (guessChar == secretChar) {
                // Цифра совпала и стоит на своём месте — это бык
                bulls++;
            } else if (secretNumber.indexOf(guessChar) != -1) {
                // Цифры на позициях не совпали, но цифра из догадки есть в загаданном числе
                // indexOf вернул не -1, значит она там есть — это корова
                cows++;
            }
        }

        return new int[]{bulls, cows};
    }
    // Начало новой игры, запрос нового числа
    public void restartGame() {
    generateSecretNumber();
    }
}
