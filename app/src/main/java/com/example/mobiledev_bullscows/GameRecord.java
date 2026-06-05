package com.example.mobiledev_bullscows;

import java.util.List;
import java.util.ArrayList;

public class GameRecord {
    String date;              // ДатаВремя
    String secretNumber;      // Сохраняем число генерированное
    int movesCount;           // Количество ходов
    List<String> guessHistory; // история ходов(с учётом коров быков)

    //конструктор для gson,
    public GameRecord() {
        this.guessHistory = new ArrayList<>();
    }
    // Конструктор с параметрами
    public GameRecord(String date, String secretNumber) {
        this.date = date;
        this.secretNumber = secretNumber;
        this.movesCount = 0;
        this.guessHistory = new ArrayList<>();
    }

    //Метод для добавления хода игрока в историю
    public void addGuess(String guess, int bulls, int cows) {
        movesCount++;
        // Строка формата - Ход *: **** -> Быков: *, Коров: *
        String record = "Ход " + movesCount + ": " + guess
                + " -> Быков: " + bulls + ", Коров: " + cows;
        guessHistory.add(record);
    }
    //Краткая информация по играм
    public String getSummary() {
        return date + " - " + movesCount + " ходов";
    }
    //Метод с полной инфой
    public String getFullInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Дата: ").append(date).append("\n");
        sb.append("Загаданное число: ").append(secretNumber).append("\n");
        sb.append("Всего ходов: ").append(movesCount).append("\n\n");
        sb.append("История ходов:\n");
        for (String guess : guessHistory) {
            sb.append(guess).append("\n");
        }
        return sb.toString();
    }
}

