package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите путь к кореневой папке:");
        // Путь к корневой папке.
        String path;
        Scanner scanner = new Scanner(System.in);
        // Читаем путь к корневой папке.
        path = scanner.nextLine();
        new FolderData(path);
    }
}