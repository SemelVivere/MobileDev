package com.example.mobiledev_bullscows;

import java.util.List;


public class GameRecord {
    String date;              // ДатаВремя
    String secretNumber;      // Сохраняем число генерированное
    int movesCount;           // Количество ходов
    List<String> guessHistory; // история ходов(с учётом коров быков)
}

