package org.example;

import java.io.File;

/**
 * Обработка путей.
 */
public interface PathCreator {

    /**
     * Добавление коревого пути к пути файла.
     * @param nameOfOldFile Путь к файлу отталкиваясь от выбранной дирректории.
     * @param rootFolder Корневая папка, заданная пользователем.
     * @return Путь к этому же файлу от корневой папки.
     */
    default File addPath(String nameOfOldFile, File rootFolder) {
        StringBuilder newPath = new StringBuilder(nameOfOldFile);
        newPath.insert(0, rootFolder.toString() + "\\");
        return new File(newPath.toString());
    }

    /**
     * Удаление корневого пути из пути к файлу.
     * @param file Путь к файлу.
     * @param rootFolder Корневая папка, заданная пользователем.
     * @return Путь к этому же файлу без пути к корневой папке.
     */
    default File erasePath(File file, File rootFolder) {
        StringBuilder newPath = new StringBuilder(file.getPath());
        newPath.delete(0, rootFolder.toString().length() + 1);
        return new File(newPath.toString());
    }
}
