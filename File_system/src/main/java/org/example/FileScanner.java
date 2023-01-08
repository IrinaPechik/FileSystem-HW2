package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для сканирования внутренностей файлов.
 */
public class FileScanner implements PathCreator {
    private final File rootFolder;
    private final FileGraph fileGraph;
    // Команда, которая указывает на зависимость с другими файлами.
    private static final String command = "require";
    FileScanner(List<File> files, File rootFolder) {
        this.rootFolder = rootFolder;
        this.fileGraph = new FileGraph(rootFolder);
        scan(files);
        fileGraph.startSort();
    }

    /**
     * Сканирование каждого файла заданной файловой системы.
     * @param files Список всех файлов.
     */
    private void scan(List<File> files) {
        // Читаем каждый файл из всех найденных.
        for (var file : files) {
            readFile(file);
        }
    }

    /**
     * Чтение всего содержимого файла.
     */
    void readFile(File file) {
        // Хранит всё содержимое файла.
        List<String> fileContent = new ArrayList<>();

        // Читает все содержимое файла.
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileContent.add(scanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("Произошла ошибка при чтении файла.");
        }

        processContentOfTheFile(file, fileContent);
    }

    /**
     * Выделение в содержимом файле текста и зависимостей от других файлов.
     * При этом важно, что зависимости указываются без названия общей корневой папки.
     * @param file Текущий файл.
     * @param fileContent Всё содержимое текущего файла.
     */
    private void processContentOfTheFile(File file, List<String> fileContent) {
        int counter = 0;
        // Текстовое содержимое файла.
        StringBuffer text = new StringBuffer();
        // Список зависимостей от других файлов.
        List<File> dependencies = new ArrayList<>();

        for (String str: fileContent) {
            // Текст помещаем в отдельную строковую переменную, учитывая перенос строки.
            if (counter == fileContent.size() - 1) {
                text.append(str);
            } else {
                text.append(str);
                text.append("\n");
            }
            counter++;
            // Выявление зависимостей.
            if (str.contains(command)) {
                // Ищем путь к файлу, от которого зависимость у данного.
                StringBuilder s = new StringBuilder(str.split(command)[1]);
                s.delete(0,2);
                s.deleteCharAt(s.length() - 1);
                dependencies.add(addPath(s.toString(), rootFolder));
            }
        }

        MyFileStructInformation inf = new MyFileStructInformation(file, text.toString());
        if (dependencies.size() == 0) {
            fileGraph.addDependency(inf, null);
        } else {
            for (var edge : dependencies) {
                fileGraph.addDependency(inf, edge);
            }
        }
    }
}
