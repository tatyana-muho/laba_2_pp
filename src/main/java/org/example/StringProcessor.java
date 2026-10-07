package org.example;

import java.text.*;
import java.util.*;
import java.util.regex.*;

public class StringProcessor {

    public String process(String input, String delimiters) {
        StringBuilder report = new StringBuilder();

        StringTokenizer tokenizer = new StringTokenizer(input, delimiters);

        List<String> allTokens = new ArrayList<>();
        List<Double> numbers = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            allTokens.add(token);

            try {
                double d = Double.parseDouble(token);
                numbers.add(d);
            } catch (NumberFormatException e) {
            }
        }

        report.append("Найдено лексем: ").append(allTokens.size()).append("\n");
        report.append("Найдено чисел: ").append(numbers.size()).append("\n");

        Collections.sort(allTokens, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
        report.append("Отсортированные лексемы (по длине): ").append(allTokens).append("\n");

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        report.append("\n--- Форматирование ---\n");
        if (!numbers.isEmpty()) {
            double firstNum = numbers.get(0);
            report.append("Число: ").append(firstNum).append("\n");
            report.append("Как валюта (US): ").append(currencyFormat.format(firstNum)).append("\n");
            report.append("Как процент: ").append(percentFormat.format(firstNum)).append("\n");
            report.append("DecimalFormat: ").append(decimalFormat.format(firstNum)).append("\n");
        }

        report.append("\n--- Поиск времени ---\n");
        Pattern timePattern = Pattern.compile("\\b(\\d{2})-(\\d{2})\\b");
        Matcher matcher = timePattern.matcher(input);
        String foundTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
        sdf.setLenient(false);

        while (matcher.find()) {
            String timeStr = matcher.group();
            try {
                sdf.parse(timeStr);
                foundTime = timeStr;
                report.append("Найдено валидное время: ").append(timeStr).append("\n");
                break;
            } catch (ParseException e) {
            }
        }

        StringBuilder modifiedString = new StringBuilder(input);
        Random random = new Random();
        int randomNumber = random.nextInt(100);

        if (foundTime != null) {
            int index = modifiedString.indexOf(foundTime);
            if (index != -1) {
                modifiedString.insert(index + foundTime.length(), " " + randomNumber + " ");
                report.append("Вставлено число ").append(randomNumber).append(" после времени ").append(foundTime).append("\n");
            }
        } else {
            modifiedString.insert(0, randomNumber + " ");
            report.append("Время не найдено. Вставлено число ").append(randomNumber).append(" в начало строки.\n");
        }

        int maxLen = 0;
        int startIndexToRemove = -1;
        int endIndexToRemove = -1;

        Pattern p = Pattern.compile("[^.!?;:]+[.!?;:]");
        Matcher m = p.matcher(modifiedString.toString());

        while(m.find()) {
            int len = m.group().length();
            if (len > maxLen) {
                maxLen = len;
                startIndexToRemove = m.start();
                endIndexToRemove = m.end();
            }
        }

        if (startIndexToRemove != -1) {
            String removed = modifiedString.substring(startIndexToRemove, endIndexToRemove);
            modifiedString.delete(startIndexToRemove, endIndexToRemove);
            report.append("Удалена самая длинная подстрока с пунктуацией: \"").append(removed).append("\"\n");
        } else {
            report.append("Подстрок с пунктуацией не найдено.\n");
        }

        report.append("\n--- Итоговая строка ---\n");
        report.append(modifiedString.toString());

        return report.toString();
    }
}