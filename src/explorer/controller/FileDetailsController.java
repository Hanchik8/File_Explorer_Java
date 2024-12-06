package explorer.controller;

import explorer.view.viewComponents.FileDetailsPanel;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDetailsController {
    private FileDetailsPanel fileDetailsPanel;

    public FileDetailsController(FileDetailsPanel fileDetailsPanel) {
        this.fileDetailsPanel = fileDetailsPanel;
    }

    /**
     * Обновляет панель с деталями о файле.
     * @param currentFile Файл, для которого нужно обновить детали.
     */
    public void updateFileDetails(File currentFile) {
        // Получаем расширение файла
        String extension = getFileExtension(currentFile);

        // Обновляем панель с деталями о файле
        fileDetailsPanel.updateFileDetailsPanel(currentFile, extension);
    }

    /**
     * Получает расширение файла.
     * @param file Файл, для которого нужно получить расширение.
     * @return Расширение файла или null, если файл - директория.
     */
    private String getFileExtension(File file) {
        if (file.isDirectory()) {
            return null;
        }

        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex == -1) {
            return ""; // Без расширения
        }

        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    /**
     * Проверяет, является ли файл директорией, и если да, вычисляет её размер.
     * @param file Файл, для которого нужно проверить размер.
     * @return Размер файла в килобайтах, если это файл, или размер директории в килобайтах.
     */
    public double getFileSize(File file) {
        if (file.isDirectory()) {
            try {
                Path path = file.toPath();
                long sizeInBytes = calculateDirectorySize(path);
                return Math.round((sizeInBytes / 1024.0) * 100.0) / 100.0; // Размер в KB
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return Math.round((file.length() / 1024.0) * 100.0) / 100.0; // Размер файла в KB
        }
        return 0;
    }

    /**
     * Вычисляет размер директории с помощью обхода файловой системы.
     * @param path Путь к директории.
     * @return Размер директории в байтах.
     * @throws Exception Исключение в случае ошибки при обработке директории.
     */
    private long calculateDirectorySize(Path path) throws Exception {
        final long[] size = {0};
        Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(file -> size[0] += file.toFile().length());
        return size[0];
    }
}
