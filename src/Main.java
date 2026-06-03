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
                    //case "3": task3(scanner); break;
                    //case "4": task4(scanner); break;
                    //case "5": task5(scanner); break;
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

//Добавить выход
}