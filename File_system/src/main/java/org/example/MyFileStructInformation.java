package org.example;

import java.io.File;
import java.util.Objects;

/**
 * Класс обёртка над файлом.
 */
public class MyFileStructInformation implements StructInformation {
    // Текущий файл.
    private final File currentFile;
    // Текущий файл.
    private final String text;

    MyFileStructInformation(File currentFile, String text) {
        this.currentFile = currentFile;
        this.text = text;
    }

    public File getFile() {
        return currentFile;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return currentFile.getName();
    }

    public String getPath() {
        return currentFile.getPath();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return Objects.equals(getPath(), ((MyFileStructInformation)obj).getPath());
    }

    @Override
    public int hashCode() {
        return getPath().hashCode();
    }
}
