package com.example.mobiledev_bullscows;
// Логика работы хранения через SharedPreferences (хранение в памяти приложения)
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Реализация через - import android.content.SharedPreferences; - проблема с сохранением истории
//public class StatsHelper {
//
//    // Хранение записей название
//    private static final String PREFS_NAME = "BullsAndCowsStats";
//    // Для страницы с хранимыми записями
//    private static final String KEY_STATS = "stats_history";
//
//    // Сохранение результата игры
//    public static void saveStat(Context context, int moves) {
//        // Берём текущую дату и время в формате дд.мм.гггг чч:мм
//        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
//        String dateStr = sdf.format(new Date());
//
//        // Базовая строка для записи, дата + количество ходов
//        String newRecord = dateStr + " | Ходов: " + moves;
//
//        // Подгрузка старых значений + защита от перезаписи
//        List<String> stats = getStats(context);
//        // Добавление записи в начало
//        stats.add(0, newRecord);
//
//        // Обращаемся к памяти для изменения "запись"
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//
//        // Склеиваем список в одну длинную строку, разделяя элементы |||
//        StringBuilder sb = new StringBuilder();
//        for (String s : stats) {
//            sb.append(s).append("|||");
//        }
//
//        // Возврат в память для сохранения
//        editor.putString(KEY_STATS, sb.toString());
//        editor.apply(); // Сохраняем изменения
//    }
//
//    // Метод для чтения всей истории
//    public static List<String> getStats(Context context) {
//        // Открытие памяти на чтение
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        String savedStats = prefs.getString(KEY_STATS, ""); // Если записей нет, вернется пустая строка ""
//
//        List<String> result = new ArrayList<>();
//
//        // Алгоритм разборки строки в читаемый вид
//        if (!savedStats.isEmpty()) {
//            // Разбиваем длинную строку обратно на части по знаку |||
//            // !!!!!!!Регулярка обязательно через \\ для понимания символа |
//            String[] parts = savedStats.split("\\|\\|\\|");
//            for (String part : parts) {
//                if (!part.isEmpty()) {
//                    result.add(part);
//                }
//            }
//        }
//        return result;
//    }
//
//    // Метод для очистки истории (!!!кнопку в меню)
//    public static void clearStats(Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        // Удаление записей
//        prefs.edit().remove(KEY_STATS).apply();
//    }
//}

//Реализация через GSON - для переформатирования в JSOn и обратно, реализация хранения истории
public class StatsHelper {

    private static final String PREFS_NAME = "BullsAndCowsStats";
    private static final String KEY_GAMES = "games_history";

    // Один раз содаем объект и после используем его
    private static final Gson gson = new Gson();

    // Сохраняем одну игру
    public static void saveGame(Context context, GameRecord game) {
        // Загружаем существующий список игр
        List<GameRecord> games = getAllGames(context);
        // Добавление новой игру в список
        games.add(0, game);

        // Конвертация списка в JSON-строку
        String json = gson.toJson(games);
        //Сохрание в SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_GAMES, json).apply();
    }

    // Через getAllGames, логика - берём json из SharedPreferences, ковертируем её обратно в список обънктов json
    public static List<GameRecord> getAllGames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_GAMES, null);

        // Проверка на пустоту сохранений, возврат пустого списка
        if (json == null) {
            return new ArrayList<>();
        }
        //Конвертация JSON обратно в список объектов GameRecord
        Type type = new TypeToken<List<GameRecord>>(){}.getType();
        return gson.fromJson(json, type);
    }

    // Создание новой записи с текущей игрой
    public static GameRecord createNewGame(String secretNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()); //формат даты понятный
        String date = sdf.format(new Date());
        return new GameRecord(date, secretNumber);
    }
    // Удаление всех записей
    public static void clearAllGames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_GAMES).apply();
    }
}