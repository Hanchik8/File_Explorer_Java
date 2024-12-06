package explorer.model;

import explorer.view.ExplorerView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;


/**
 * Класс в котором содержатся методы для манипуляции с файлами такие как
 * создание, удаление, коприрование, перемещение файлов
 */
public class FileManipulation {
    private ExplorerView explorerView;
    private File copiedFile;  // переменная для сохранения скопированного файла
    private boolean cutPressed = false; // Флаг, указывающий, что файл был "вырезан" для перемещения.

    public FileManipulation(ExplorerView explorerView) {
        this.explorerView = explorerView;
    }

    /**
     * Открывает файл с использованием системных приложений.
     * Метод использует возможности класса `Desktop` для открытия файла
     * в приложении, которое поддерживает данный тип файла.
     *
     * @param file файл, который нужно открыть.
     */
    public void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.err.println("Ups! Unable to open this file: " + e.getMessage());
            }
        }
    }

    /**
     * Создаёт новый файл или директорию в указанной родительской директории.
     * Если файл или директория с таким именем уже существует, создаётся уникальное имя.
     *
     * @param parentDirectory родительская директория, где будет создан файл или директория.
     * @param fileName имя создаваемого файла или директории.
     */
    public void createFile(File parentDirectory, String fileName) {
        File newFile = new File(parentDirectory, fileName);
        try {
            if (newFile.getName().contains("Directory")) {
                newFile = ensureUniqueFileName(newFile);
                newFile.mkdir();
            } else {
                newFile = ensureUniqueFileName(newFile);
                newFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error! Unable to create file or directory. " + e.getMessage());
        }
    }

    /**
     * Сохраняет файл в переменную `copiedFile` для последующего копирования или вставки.
     *
     * @param file файл, который нужно скопировать.
     */
    public void copyFile(File file) {
        copiedFile = file;
        updateBtnState(false);
        explorerView.getEditPanel().getPasteBtn().setEnabled(true);
    }

    /**
     * Подготавливает файл для перемещения (вырезания).
     * Файл сохраняется в переменную `copiedFile`, а переменная `cutPressed` устанавливается
     * в `true`, чтобы указать, что следующий вызов метода `pasteFile()` должен переместить файл,
     * а не скопировать.
     *
     * @param oldPlace файл, который нужно вырезать.
     */
    public void cutFile(File oldPlace) {
        copyFile(oldPlace);
        cutPressed = true;
        updateBtnState(false);
        explorerView.getEditPanel().getPasteBtn().setEnabled(true);
    }

    /**
     * Вставляет файл (скопированный или вырезанный) в указанную директорию.
     * Если файл уже существует в целевой директории, создаёт файл с уникальным именем.
     * Если файл был вырезан (перемещение), перемещает его в новую директорию.
     * Если файл был скопирован, создаёт копию.
     *
     * @param currentDirectory директория, в которую нужно вставить файл.
     */
    public void pasteFile(File currentDirectory) {
        if (copiedFile != null) {
            Path destinationPath = currentDirectory.toPath().resolve(copiedFile.getName());
            try {
                File renamedFile = ensureUniqueFileName(destinationPath.toFile());
                if (cutPressed) {
                    Files.move(copiedFile.toPath(), renamedFile.toPath());
                    cutPressed = false;
                } else {
                    Files.copy(copiedFile.toPath(), renamedFile.toPath());
                }
            } catch (IOException e) {
                System.err.println("Error while pasting file: " + e.getMessage());
            }
        }
    }

    /**
     * Удаляет указанный файл.
     * Если файл существует, он будет удалён. Для папок должна быть пустота.
     *
     * @param file файл, который нужно удалить.
     */
    public void deleteFile(File file) {
        file.delete();
        updateBtnState(false);
    }

    /**
     * Убедиться, что файл имеет уникальное имя в указанной директории.
     * Если файл с таким именем уже существует, добавляет порядковый номер к имени.
     *
     * @param file исходный файл, для которого нужно проверить уникальность имени.
     * @return объект File с уникальным именем.
     */
    private File ensureUniqueFileName(File file) {
        String initialFileName = file.getName();
        String[] fileNameAndExtension = getFileNameAndExtension(initialFileName);
        File parentDir = file.getParentFile();

        int counter = 1;
        while (file.exists()) {
            String newName;
            if (fileNameAndExtension == null) {
                newName = initialFileName + " (" + counter + ")";
            } else {
                newName = fileNameAndExtension[0] + " (" + counter + ")" + fileNameAndExtension[1];
            }
            file = new File(parentDir, newName);
            counter++;
        }

        return file;
    }

    /**
     * Разделяет имя файла на две части: имя без расширения и расширение.
     *
     * @param fileName полное имя файла (например, "example.txt").
     * @return массив строк, где [0] — имя файла без расширения, [1] — расширение с точкой.
     * Если расширение не найдено, возвращает null.
     */
    public static String[] getFileNameAndExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            String fileNameWithoutExtension = fileName.substring(0, lastDotIndex);
            String fileExtension = "." + fileName.substring(lastDotIndex + 1);
            return new String[] {fileNameWithoutExtension, fileExtension};
        }
        return null;
    }

    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

    public void updateFileDetails(File selectedFile) {
        explorerView.getFileDetailsPanel().updateFileDetailsPanel(selectedFile, getFileExtension(selectedFile.getName()));
        updateBtnState(true);
    }
    public void updateBtnState(boolean isBtnActive) {
        explorerView.getEditPanel().getCutBtn().setEnabled(isBtnActive);
        explorerView.getEditPanel().getCopyBtn().setEnabled(isBtnActive);
        explorerView.getEditPanel().getDeleteBtn().setEnabled(isBtnActive);
        explorerView.getEditPanel().getRenameBtn().setEnabled(isBtnActive);
    }
}
