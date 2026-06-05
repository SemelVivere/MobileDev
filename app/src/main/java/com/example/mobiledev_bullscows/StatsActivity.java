package com.example.mobiledev_bullscows;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private ListView lvGames;
    private Button btnClear;
    private List<GameRecord> gamesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        lvGames = findViewById(R.id.lvGames);
        btnClear = findViewById(R.id.btnClear);

        //Загрузка из памяти всех игр
        gamesList = StatsHelper.getAllGames(this);

        // Создание списка строк для отображения в ListView/ArrayList
        List<String> summaries = new ArrayList<>();
        for (GameRecord game : gamesList) {
            summaries.add(game.getSummary());
        }

        // ArrayAdapter — связующее между списком строк и ListView, парсит каждую строку на отдельный элемент списка
        adapter = new ArrayAdapter<>(
                this,                           // контекст (текущий экран)
                android.R.layout.simple_list_item_1, //Базовый шаблон для одной строчки
                summaries                       //список строк
        );
        //Привязка адаптера к ListView
        lvGames.setAdapter(adapter);

        // Обработка клика по элементу списка
        lvGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Здесь позже здесь будет переход к деталям, если получится ------------------------------------------
                GameRecord clickedGame = gamesList.get(position);
                Toast.makeText(StatsActivity.this,
                        "Игра от " + clickedGame.date,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Обработчик кнопки очистки
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gamesList.isEmpty()) {
                    Toast.makeText(StatsActivity.this,
                            "История уже пуста",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //Удаление игр из памяти
                StatsHelper.clearAllGames(StatsActivity.this);
                // Очищение списков
                gamesList.clear();
                summaries.clear();

                // Обновление данных адаптера
                adapter.notifyDataSetChanged();
                Toast.makeText(StatsActivity.this,
                        "История очищена",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}