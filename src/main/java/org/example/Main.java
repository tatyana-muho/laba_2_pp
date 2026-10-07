package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        try {
            FileReader fileReader = new FileReader(inputFilePath, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String firstString = bufferedReader.readLine();
            String secondString = bufferedReader.readLine();

            bufferedReader.close();

            if (firstString == null || secondString == null) {
                System.out.println("Ошибка: Файл должен содержать две строки.");
                return;
            }

            StringProcessor processor = new StringProcessor();
            String result = processor.process(firstString, secondString);

            FileWriter fileWriter = new FileWriter(outputFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Исходная строка 1: " + firstString + "\n");
            bufferedWriter.write("Разделители: " + secondString + "\n");
            bufferedWriter.write("--------------------------------------------------\n");
            bufferedWriter.write("РЕЗУЛЬТАТ:\n" + result);

            bufferedWriter.close();

            System.out.println("Обработка завершена. Результат в файле " + outputFilePath);

        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}