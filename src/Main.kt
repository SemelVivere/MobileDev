import java.util.Scanner

// Cканер для чтения ввода из консоли
val scanner = Scanner(System.`in`)

fun main() {
    println("Cборник задач Kotlin")

    // Запуска задач по очереди
    task1()
    task2()
    task3()
    task4()
    task5()
    task6()

    println("Все задачи выполнены ")
}

// ЗАДАЧА 1: Подсчет подряд идущих одинаковых символов
fun task1() {
    println("\n--- Задача 1 ---")
    print("Введите строку (например, AAADSSSRRTTHAAAA): ")
    val input = scanner.nextLine()

    if (input.isEmpty()) {
        println("Ошибка: строка не может быть пустой.")
        return
    }

    val result = buildString {
        var count = 1
        for (i in 1 until input.length) {
            if (input[i] == input[i - 1]) {
                count++
            }
            else {
                append(input[i - 1])
                if (count > 1) append(count)
                count = 1
            }
        }
        // Добавляем последний символ и его количество
        append(input.last())
        if (count > 1) append(count)
    }

    println("Результат: $result")
}


// ЗАДАЧА 2: Подсчет различных символов в алфавитном порядке
fun task2() {
    println("\n--- Задача 2 ---")
    print("Введите строку (например, AASADDSS): ")
    val input = scanner.nextLine()

    if (input.isEmpty()) {
        println("Ошибка: строка не может быть пустой.")
        return
    }

    // Создаем карту (словарь) для подсчета: Символ -> Количество
    val charCounts = mutableMapOf<Char, Int>()
    for (char in input) {
        // getOrDefault возвращает текущее значение или 0, если символа еще нет
        charCounts[char] = charCounts.getOrDefault(char, 0) + 1
    }

    println("Результат:")
    // Сортируем ключи (символы) в алфавитном порядке и выводим
    charCounts.keys.sorted().forEach { char ->
        println("$char - ${charCounts[char]}")
    }
}

// ЗАДАЧА 3: Перевод натурального числа из 10-ичной в двоичную cистему
fun task3() {
    println("\n--- Задача 3 ---")
    print("Введите натуральное число: ")
    val input = scanner.nextLine()

    try {
        val number = input.toUInt() // Используем UInt, так как число должно быть натуральным (>0)
        if (number == 0U) {
            println("Ошибка: число должно быть натуральным (больше 0).")
            return
        }
        // toString(2) - встроенный метод Kotlin для перевода в двоичную систему
        println("Результат: ${number.toString(2)}")
    }
    //Защита от некорректного ввода
    catch (e: NumberFormatException) {
        println("Ошибка ввода: введено не число или оно отрицательное.")
    }
}

// ЗАДАЧА 4: Калькулятор (ЧИСЛО1 ЧИСЛО2 ОПЕРАЦИЯ)
fun task4() {
    println("\n--- Задача 4 ---")
    println("Формат ввода: ЧИСЛО1 ЧИСЛО2 ОПЕРАЦИЯ (через пробел, например: 5.5 2.0 +)")
    print("Введите данные: ")
    val input = scanner.nextLine().trim()

    // Разделяем строку по одному или нескольким пробелам с помощью регулярного выражения
    val parts = input.split("\\s+".toRegex())

    if (parts.size != 3) {
        println("Ошибка: введено некорректное количество элементов. Ожидается 3 (Число1 Число2 Операция).")
        return
    }

    val (strNum1, strNum2, operator) = parts

    try {
        val num1 = strNum1.toDouble()
        val num2 = strNum2.toDouble()

        val result = when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> {
                if (num2 == 0.0) {
                    println("Ошибка: деление на ноль невозможно.")
                    return
                }
                num1 / num2
            }
            else -> {
                println("Ошибка: неподдерживаемая операция '$operator'. Допустимы: +, -, *, /")
                return
            }
        }
        println("Результат: $result")
    } catch (e: NumberFormatException) {
        println("Ошибка ввода: первое или второе значение не является корректным числом.")
    }
}


// ЗАДАЧА 5: Поиск целочисленного показателя степени (x^y = n)
fun task5() {
    println("\n--- Задача 5 ---")
    print("Введите целое число n: ")
    val strN = scanner.nextLine()
    print("Введите основание степени x: ")
    val strX = scanner.nextLine()

    try {
        val n = strN.toInt()
        val x = strX.toInt()

        if (n <= 0 || x <= 0) {
            println("Работаем только с натуральными числами (n > 0, x > 0).")
            return
        }

        var y = 0
        var currentPower = 1 // x^0 всегда равно 1

        // Ищем y, пока x^y меньше или равно n
        while (currentPower < n) {
            y++
            // Проверка на переполнение при возведении в степень
            try {
                currentPower = Math.multiplyExact(currentPower, x)
            } catch (e: ArithmeticException) {
                break // Если произошло переполнение, значит x^y уже точно больше n
            }
        }

        if (currentPower == n) {
            println("Результат: Показатель степени y = $y (так как $x^$y = $n)")
        } else {
            println("Целочисленный показатель не существует")
        }

    } catch (e: NumberFormatException) {
        println("Ошибка ввода: введите корректные целые числа.")
    }
}

// ЗАДАЧА 6: Создание нечетного числа из двух различных цифр
fun task6() {
    println("\n--- Задача 6 ---")
    print("Введите первую цифру (0-9): ")
    val strDigit1 = scanner.nextLine().trim()
    print("Введите вторую цифру (0-9): ")
    val strDigit2 = scanner.nextLine().trim()

    // Проверка: являются ли введенные данные одиночными цифрами
    if (!strDigit1.matches(Regex("^[0-9]$")) || !strDigit2.matches(Regex("^[0-9]$"))) {
        println("Ошибка: необходимо вводить ровно одну цифру от 0 до 9 в каждой строке.")
        return
    }

    val d1 = strDigit1.toInt()
    val d2 = strDigit2.toInt()

    if (d1 == d2) {
        println("Ошибка: цифры должны быть различными.")
        return
    }

    // Нечетное число всегда заканчивается на нечетную цифру (1, 3, 5, 7, 9)
    val isD1Odd = d1 % 2 != 0
    val isD2Odd = d2 % 2 != 0

    if (isD1Odd && isD2Odd) {
        // Если обе нечетные, можно составить число любым способом, например d1 потом d2
        println("Результат: ${d1}${d2}")
    } else if (isD1Odd) {
        // Если нечетная только первая, она должна стоять в конце (в разряде единиц)
        println("Результат: ${d2}${d1}")
    } else if (isD2Odd) {
        // Если нечетная только вторая, она должна стоять в конце
        println("Результат: ${d1}${d2}")
    } else {
        // Если обе четные, нечетное число составить невозможно
        println("Создать нечетное число невозможно")
    }
}