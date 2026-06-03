package com.example.mobiledev_bullscows;
// Логика работы хранения через SharedPreferences (хранение в памяти приложения)
import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatsHelper {

    // Хранение записей название
    private static final String PREFS_NAME = "BullsAndCowsStats";
    // Для страницы с хранимыми записями
    private static final String KEY_STATS = "stats_history";

    // Сохранение результата игры
    public static void saveStat(Context context, int moves) {
        // Берём текущую дату и время в формате дд.мм.гггг чч:мм
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        String dateStr = sdf.format(new Date());

        // Базовая строка для записи, дата + количество ходов
        String newRecord = dateStr + " | Ходов: " + moves;

        // Подгрузка старых значений + защита от перезаписи
        List<String> stats = getStats(context);
        // Добавление записи в начало
        stats.add(0, newRecord);

        // Обращаемся к памяти для изменения "запись"
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Склеиваем список в одну длинную строку, разделяя элементы |||
        StringBuilder sb = new StringBuilder();
        for (String s : stats) {
            sb.append(s).append("|||");
        }

        // Возврат в память для сохранения
        editor.putString(KEY_STATS, sb.toString());
        editor.apply(); // Сохраняем изменения
    }

    // Метод для чтения всей истории
    public static List<String> getStats(Context context) {
        // Открытие памяти на чтение
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedStats = prefs.getString(KEY_STATS, ""); // Если записей нет, вернется пустая строка ""

        List<String> result = new ArrayList<>();

        // Алгоритм разборки строки в читаемый вид
        if (!savedStats.isEmpty()) {
            // Разбиваем длинную строку обратно на части по знаку |||
            // !!!!!!!Регулярка обязательно через \\ для понимания символа |
            String[] parts = savedStats.split("\\|\\|\\|");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    result.add(part);
                }
            }
        }
        return result;
    }

    // Метод для очистки истории (!!!кнопку в меню)
    public static void clearStats(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Удаление записей
        prefs.edit().remove(KEY_STATS).apply();
    }
}
