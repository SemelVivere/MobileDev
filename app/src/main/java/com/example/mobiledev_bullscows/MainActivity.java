package com.example.mobiledev_bullscows;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GameLogic gameLogic;
    private int moveCount;

    //Добавление новой переменной( карточка игры)
    private GameRecord currentGame;

    private EditText etGuess;
    private TextView tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Вызов элементов по id из xml
        etGuess = findViewById(R.id.etGuess);
        tvHistory = findViewById(R.id.tvHistory);
        Button btnCheck = findViewById(R.id.btnCheck);

        //Запуск новой игры
        startNewGame();

        // При клике на кнопку — проверка коров быков
        btnCheck.setOnClickListener(v -> checkUserGuess());
    }
    //Обращение к GameLogic (новое число + новый счётчик ходов)
    private void startNewGame() {
        gameLogic = new GameLogic();
        currentGame = StatsHelper.createNewGame(gameLogic.getSecretNumber()); //Создание записи с информацией по текущей игре, дата + загаданное число
        //moveCount = 0;
        etGuess.setText("");
        tvHistory.setText("История:\n");
    }

    private void checkUserGuess() {
        String guess = etGuess.getText().toString();

        // Проверка: ровно 4 символа
        if (guess.length() != 4) {
            Toast.makeText(this, "Введите ровно 4 цифры!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка: цифры не повторяются
        if (!hasUniqueDigits(guess)) {
            Toast.makeText(this, "Цифры не должны повторяться!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Считаем ход
        moveCount++;

        // Спрашиваем у GameLogic результат
        int[] result = gameLogic.checkGuess(guess);
        int bulls = result[0];
        int cows = result[1];

        //Добавление хода в историю игры
        currentGame.addGuess(guess,bulls,cows);

        // Дописываем строчку в историю|Выводим в интерфейс
        String line = "Ход " + moveCount + ": " + guess + " -> Быков: " + bulls + ", Коров: " + cows + "\n";
        tvHistory.append(line);

        // Чистое поле для следующей попытки
        etGuess.setText("");

        // Если угадали всё — победа
        if (bulls == 4) {
            Toast.makeText(this, "Угадали за " + moveCount + " ходов!", Toast.LENGTH_LONG).show();
            //StatsHelper.saveStat(this, moveCount);
            StatsHelper.saveGame(this, currentGame);
            // Автоматически начинаем заново через 10сек
            etGuess.postDelayed(this::startNewGame, 10000);
        }
    }

    // Проверка, что все 4 цифры разные
    private boolean hasUniqueDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_stats) {
            // Создаем "намерение" открыть StatsActivity
            Intent intent = new Intent(this, StatsActivity.class);
            // Запуск нового экрана
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_new_game) {
            startNewGame();
            Toast.makeText(this, "Новая игра", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_stats) {
//            //Уведомление перехода к списку игр
//            Toast.makeText(this ,"Здесь будет список игр", Toast.LENGTH_SHORT).show();
//
//            //showStats();
//            return true;
//        } else if (item.getItemId() == R.id.action_new_game) {
//            startNewGame();
//            Toast.makeText(this, "Новая игра начата", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void showStats() {
//        List<String> stats = StatsHelper.getStats(this);
//
//        if (stats.isEmpty()) {
//            Toast.makeText(this, "Статистика пуста", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Собираем все записи в один текст
//        StringBuilder sb = new StringBuilder();
//        for (String record : stats) {
//            sb.append(record).append("\n");
//        }
//        // Показываем в диалоге
//        new AlertDialog.Builder(this)
//                .setTitle("История игр")
//                .setMessage(sb.toString())
//                .setPositiveButton("OK", null)
//                .show();
//    }
}