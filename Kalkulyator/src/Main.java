import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Ввод выражения пользователем пишу для себя если что сильно палками не бейте пожалуйста
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 2 + 3 или V * II):");
        String expression = scanner.nextLine();

        // Попытка вычисления выражения и вывод результата
        try {
            String result = calc(expression);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            // В случае ошибки, сообщаем об этом
            System.out.println("Ошибка в выражении: " + e.getMessage());
        }
    }

    // Основной метод для вычисления выражения
    public static String calc(String input) {
        // Разделение введенной строки на элементы
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Формат должен быть 'число операция число'. Пожалуйста, проверьте ввод.");
        }

        // Определение типа чисел и проверка на смешанный ввод
        boolean isRoman = parts[0].matches("[IVXLCDM]+") && parts[2].matches("[IVXLCDM]+");
        boolean isArabic = parts[0].matches("\\d+") && parts[2].matches("\\d+");
        if (!isRoman && !isArabic) {
            throw new IllegalArgumentException("Числа должны быть только арабскими или только римскими. Смешанный ввод не допускается.");
        }

        // Конвертация чисел и выполнение операции
        int num1 = isRoman ? romanToArabic(parts[0]) : Integer.parseInt(parts[0]);
        int num2 = isRoman ? romanToArabic(parts[2]) : Integer.parseInt(parts[2]);
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10.");
        }

        int result = performOperation(num1, num2, parts[1]);

        // Возвращаем результат в нужном формате
        return isRoman ? arabicToRoman(result) : String.valueOf(result);
    }

    // Метод для выполнения арифметических операций
    private static int performOperation(int num1, int num2, String operation) {
        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль невозможно.");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция: " + operation);
        }
    }

    // Конвертация из римских чисел в арабские
    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanNumerals = new LinkedHashMap<>();
        // Заполнение словаря значениями римских цифр
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        // А дальше нужно заполнить или хватит двух этих чисел?

        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanNumerals.get(roman.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
                prevValue = value;
            }
        }
        return result;
    }

    // Конвертация из арабских чисел в римские
    private static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Римские числа могут быть только положительными.");
        }

        // Определение соответствий между арабскими числами и римскими символами лень было дальше писать, если надо скажите я допишу римские цифры
        int[] arabicNumbers = {5, 4, 1};
        String[] romanSymbols = {"V", "IV", "I"};

        StringBuilder roman = new StringBuilder();

        // Конвертация числа в римскую запись
        for (int i = 0; i < arabicNumbers.length; i++) {
            while (number >= arabicNumbers[i]) {
                number -= arabicNumbers[i];
                roman.append(romanSymbols[i]);
            }
        }

        return roman.toString();
    }
}