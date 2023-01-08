package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Выявление содержимого заданной корневой папки.
 */
public class FolderData {
    // Корневая папка.
    private final File rootFolder;
    // Список всех файлов.
    private List<File> files;
    FolderData(String path) {
        rootFolder = new File(path);
        obtainTheContentsOfFolder();
        if (isFolderEmpty()) {
            System.out.println("В указанной иерархии папок" +
                    " отсутствуют какие-либо файлы или такого каталога не существует.");
            return;
        }
        // Сканируем файлы.
        new FileScanner(files, rootFolder);
    }

    /**
     * Получение всех файлов данной корневой папки и её подпапок.
     */
    void obtainTheContentsOfFolder() {
        // Если корневая папка пуста, то и не ищем в ней ничего.
        if (rootFolder != null && rootFolder.list() != null
                && rootFolder.list().length == 0) {
            return;
        }
        try (Stream<Path> walk = Files.walk(Paths.get(rootFolder.getPath()))) {
            files = walk.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка при обходе всех директорий.");
        }
    }

    /**
     * Проверка того, есть ли в корневой папке какие-либо файлы.
     * @return true - в папке нет файлов; false - в папке есть файлы.
     */
    private boolean isFolderEmpty() {
        if (rootFolder.getPath().isEmpty() || files == null) {
            return true;
        }

        boolean isEmpty = true;
        try {
            for (var file : files) {
                if (file.isFile()) {
                    isEmpty = false;
                }
            }
        } catch (SecurityException e) {
            System.out.println("Ошибка доступа.");
        }
        return isEmpty;
    }
}
