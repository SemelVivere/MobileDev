import java.util.*;;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // object+vvod keyboard

        while (true) {
            System.out.println("\nВыберите задачу (1-5) или 0 для выхода:");
            String choice = scanner.nextLine(); // Что ввёл пользователь, 0 выход

            if (choice.equals("0")) {
                System.out.println("Выход");
                break; // Выход из бесконечного цикла)
            }

            try {
                // Выбор задачи от 1 до 5
                switch (choice) {
                    case "1": task1(scanner); break;
                    case "2": task2(scanner); break;
                    case "3": task3(scanner); break;
                    //case "4": task4(scanner); break; Не понял реализацию
                    case "5": task5(scanner); break;
                    default: System.out.println("Нет такой задачи"); //Tckb gjkmpjdfntkm dsitk pf ds,jh 1-5
                }
            } catch (Exception e) {
                // Если внутри задач произошла ошибка, программа не падает, а выводит ошибки сюда
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        scanner.close(); // Закрываем сканер, чтобы не было утечек памяти
    }


// Задача 1
public static void task1(Scanner sc) {
    System.out.println("--- Задача 1 ---");
    System.out.print("Введите количество строк: ");
    int rows = Integer.parseInt(sc.nextLine());
    System.out.print("Введите количество столбцов: ");
    int cols = Integer.parseInt(sc.nextLine());

    int[][] matrix = new int[rows][cols]; // Создание двумерного массива(таблица)
    System.out.println("Введите трехзначные числа (по одному в строке):");

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int num = Integer.parseInt(sc.nextLine());
            if (num < 100 || num > 999) { // Проверка на трехзначность
                throw new IllegalArgumentException("Число должно быть трехзначным!");
            }
            matrix[i][j] = num; // Помещаем число в ячейку таблицы
        }
    }

    // Массив-флажок для цифр 0-9.
    // По умолчанию везде false. Если цифра встретилась, ставим true.
    boolean[] hasDigit = new boolean[10];

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int num = matrix[i][j];
            // Разбираем число на цифры
            while (num > 0) {
                int digit = num % 10; // Остаток от деления на 10 - это последняя цифра
                hasDigit[digit] = true; // Запоминаем, что такая цифра была
                num = num / 10; // Деление на 10, чтобы отбросить последнюю цифру
            }
        }
    }

    // Считаем, сколько ячеек стали true
    int count = 0;
    for (int i = 0; i < 10; i++) {
        if (hasDigit[i]) count++;
    }

    System.out.println("Массив:");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            System.out.print(matrix[i][j] + " ");
        }
        System.out.println(); // Перенос строки для красоты
    }
    System.out.println("Различных цифр: " + count);
}

//Задание 2
public static void task2(Scanner sc) {
    System.out.println("--- Задача 2 ---");
    int[][] matrix = new int[5][5];
    System.out.println("Введите матрицу 5x5 (по 5 чисел в строке через пробел):");

    for (int i = 0; i < 5; i++) {
        String[] parts = sc.nextLine().split(" "); // Разбиваем строку по пробелам
        for (int j = 0; j < 5; j++) {
            matrix[i][j] = Integer.parseInt(parts[j]);
        }
    }

    boolean isSymmetric = true; // Предполагаем что матрица симметрична

    for (int i = 0; i < 5; i++) {
        // Для корректного сравнивания j начинается с i + 1, так проверка над главной диагональю
        for (int j = i + 1; j < 5; j++) {
            if (matrix[i][j] != matrix[j][i]) {
                isSymmetric = false; // Нашли несовпадение
                break; // Выходим из цикла, дальше проверять нет смысла
            }
        }
    }

    if (isSymmetric) {
        System.out.println("Матрица симметрична");
    } else {
        System.out.println("Матрица не симметрична");
    }
}


//Задание 3
public static void task3(Scanner sc) {
    System.out.println("--- Задача 3 ---");
    String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    int[] table = {30, 27, 8, 20, 10, 33, 25, 12, 19, 14, 7, 28, 9, 23, 6, 29, 3, 16, 15, 11,
            26, 5, 21, 13, 4, 18, 22, 1, 31, 32, 24, 2, 17};

    System.out.print("Введите ключевое слово: ");
    String key = sc.nextLine().toUpperCase(); // Меняем регистр
    System.out.print("Введите текст: ");
    String text = sc.nextLine().toUpperCase();

    String result = ""; // Сюда зашифрованный текст

    for (int i = 0; i < text.length(); i++) {
        char textChar = text.charAt(i); // Берем i-ю букву из текста
        // Берем i-ю букву из ключа. Остаток от деления (%) зацикливает ключ
        char keyChar = key.charAt(i % key.length());

        int textIndex = alphabet.indexOf(textChar); // Ищем букву текста в алфавите

        // Если символ не буква (например, пробел), просто добавляем его как есть
        if (textIndex == -1) {
            result += textChar;
            continue; // Переходим к следующей букве
        }

        int keyIndex = alphabet.indexOf(keyChar);

        int textNum = table[textIndex]; // Номер буквы текста из таблицы
        int keyNum = table[keyIndex];   // Номер буквы ключа из таблицы

        int newNum = textNum + keyNum;
        if (newNum > 33) {
            newNum -= 33; // Если вышли за пределы 33, возвращаемся в начало
        }

        // Ищем в таблице букву, у которой есть этот новый номер
        int newIndex = -1;
        for (int j = 0; j < table.length; j++) {
            if (table[j] == newNum) {
                newIndex = j;
                break;
            }
        }

        result += alphabet.charAt(newIndex); // Добавляем зашифрованную букву
    }
    System.out.println("Результат: " + result);
}

//Задача 4 -
//
// Задача 5

    public static void task5(Scanner sc) {
        System.out.println("--- Задача 5 ---");
        System.out.print("Введите слова через пробел: ");
        String[] words = sc.nextLine().split(" ");

        // Словарь: ключ - отсортированные буквы, значение - список слов
        HashMap<String, ArrayList<String>> groups = new HashMap<>();

        for (String word : words) {
            if (word.isEmpty()) continue;

            // Превращаем слово в массив букв, сортируем его и собираем обратно в строку
            char[] chars = word.toLowerCase().toCharArray();
            Arrays.sort(chars);
            String sortedWord = new String(chars); // Например, "eat" и "tea" оба станут "aet"

            // Проверяем, есть ли уже такой ключ в словаре
            if (groups.containsKey(sortedWord)) {
                // Если есть, достаем список и добавляем слово в него
                groups.get(sortedWord).add(word);
            } else {
                // Если нет, создаем новый список, кладем туда слово и сохраняем в словарь
                ArrayList<String> newList = new ArrayList<>();
                newList.add(word);
                groups.put(sortedWord, newList);
            }
        }

        System.out.println("Группы:");
        // Проходим по всем значениям (спискам) в словаре и печатаем их
        for (ArrayList<String> group : groups.values()) {
            System.out.println(group);
        }
    }


//Добавить выход для выбора таски
}