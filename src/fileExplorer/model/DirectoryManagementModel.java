package fileExplorer.model;

import fileExplorer.model.enums.SortCriteria;
import fileExplorer.view.MainView;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Класс DirectoryManagementModel управляет директориями,
 * предоставляя функционал для работы с файловой системой.
 * Включает функции сортировки, фильтрации, навигации и поиска файлов.
 */
public class DirectoryManagementModel {
    private MainView mainView;
    private String currentDirectory = "Root";
    private Stack<String> undoStack;
    private Stack<String> redoStack;
    private boolean isDirectoryChanged = false;

    /**
     * Конструктор инициализирует модель управления директориями.
     * @param explorerView объект представления для взаимодействия с интерфейсом.
     */
    public DirectoryManagementModel(MainView explorerView) {
        this.mainView = explorerView;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Сортирует список файлов по заданному критерию.
     * @param files массив файлов для сортировки.
     * @param criteria критерий сортировки (имя, размер, дата).
     * @return отсортированный массив файлов.
     */
    public File[] updateAndSortFileList(File[] files, SortCriteria criteria) {
        Arrays.sort(files, (file1, file2) -> {
            switch (criteria) {
                case NAME:
                    return file1.getName().compareToIgnoreCase(file2.getName());
                case SIZE:
                    return Long.compare(file1.length(), file2.length());
                case DATE:
                    return Long.compare(file1.lastModified(), file2.lastModified());
                default:
                    return 0;
            }
        });

        return files;
    }

    /**
     * Ищет файлы по имени в указанной директории и её поддиректориях.
     * @param directory директория для поиска.
     * @param fileName имя файла для поиска.
     * @param searchedFiles список найденных файлов.
     */
    public void searchFileByName(String directory, String fileName, ArrayList<File> searchedFiles) {
        File[] fileList = listDirectoryContent(new File(directory));
        for (File file : fileList) {
            if (file.isDirectory()) {
                if (file.getName().toLowerCase().contains(fileName.toLowerCase())) {
                    searchedFiles.add(file);
                }
                searchFileByName(file.getAbsolutePath(), fileName, searchedFiles);
            } else if (file.getName().toLowerCase().contains(fileName.toLowerCase())) {
                searchedFiles.add(file);
            }
        }
        File[] files = searchedFiles.toArray(new File[0]);
        updateView(files, "Search");
    }

    /**
     * Возвращает на шаг назад в истории директорий.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentDirectory);
            currentDirectory = undoStack.pop();

            isDirectoryChanged = true;
            updateDirectory();
        }
    }

    /**
     * Перемещает на шаг вперёд в истории директорий.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentDirectory);
            currentDirectory = redoStack.pop();

            isDirectoryChanged = true;
            updateDirectory();
        }
    }

    /**
     * Перемещается в родительскую директорию.
     */
    public void moveToParentDirectory() {
        File current = new File(currentDirectory);
        if (current.getParent() != null) {
            updateDirectory(current.getParent());
        } else {
            currentDirectory = "Root";
            updateDirectory();
        }
    }

    /**
     * Обновляет текущую директорию на указанную.
     * @param newDirectory новая директория.
     * @return список файлов в новой директории.
     */
    public File[] updateDirectory(String newDirectory) {
        redoStack.clear();
        File[] listOfFiles;
        if (isRootDirectory(newDirectory)) {
            listOfFiles = getRootDirectories();
        } else {
            if (!currentDirectory.equals(newDirectory)) {
                undoStack.push(currentDirectory);
            }
            currentDirectory = newDirectory;
            listOfFiles = listDirectoryContent(new File(newDirectory));
        }
        isDirectoryChanged = true;
        updateView(listOfFiles, currentDirectory);

        return listOfFiles;
    }

    /**
     * Обновляет текущее состояние директории.
     * @return список файлов в текущей директории.
     */
    public File[] updateDirectory() {
        File[] listOfFiles;
        if (isRootDirectory(currentDirectory)) {
            listOfFiles = getRootDirectories();
        } else {
            listOfFiles = filterFiles(listDirectoryContent(new File(currentDirectory)));
        }
        updateView(listOfFiles, currentDirectory);

        return listOfFiles;
    }

    /**
     * Возвращает список файлов и поддиректорий в указанной директории.
     * @param directory директория для получения содержимого.
     * @return массив файлов и директорий, прошедших фильтрацию.
     */
    public File[] listDirectoryContent(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null)
            return filterFiles(contents);
        else
            return new File[0];
    }

    /**
     * Фильтрует файлы по разрешённым форматам и исключает скрытые файлы.
     * @param fileList массив файлов для фильтрации.
     * @return массив отфильтрованных файлов.
     */
    private File[] filterFiles(File[] fileList) {
        String[] listOfFormats = { "txt", "png", "jpg", "jpeg", "ppt", "pptx", "docx", "doc", "xls", "xlsx", "pdf"};
        ArrayList<File> acceptableFiles = new ArrayList<>();
        for (File file : fileList) {
            if (!file.isHidden()) {
                if (file.isDirectory()) {
                    acceptableFiles.add(file);
                } else {
                    String fileExtension = getFileExtension(file.getName());
                    for (String format : listOfFormats) {
                        if (fileExtension != null && fileExtension.equals(format)) {
                            acceptableFiles.add(file);
                        }
                    }
                }
            }
        }
        return acceptableFiles.toArray(new File[0]);
    }

    /**
     * Получает список корневых директорий в зависимости от операционной системы.
     * @return массив корневых директорий.
     */
    public File[] getRootDirectories() {
        currentDirectory = "Root";
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return File.listRoots();
        } else if (os.contains("mac")) {
            return new File("/Volumes").listFiles();
        } else if (os.contains("linux")) {
            File[] mntPathFiles = new File("/mnt").listFiles();
            File[] mediaPathFiles = new File("/media").listFiles();
            File[] linuxDisks = new File[(mntPathFiles != null ? mntPathFiles.length : 0)
                    + (mediaPathFiles != null ? mediaPathFiles.length : 0)];

            int index = 0;
            if (mntPathFiles != null) {
                for (File file : mntPathFiles) {
                    if (file.isDirectory()) {
                        linuxDisks[index++] = file;
                    }
                }
            }
            if (mediaPathFiles != null) {
                for (File file : mediaPathFiles) {
                    if (file.isDirectory()) {
                        linuxDisks[index++] = file;
                    }
                }
            }
            return linuxDisks;
        }
        return new File[0];
    }

    /**
     * Получает расширение файла.
     * @param fileName имя файла.
     * @return строка с расширением файла или null, если расширение отсутствует.
     */
    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

    /**
     * Проверяет, является ли указанная директория корневой.
     * @param directory строка с путём директории.
     * @return true, если директория корневая, иначе false.
     */
    private boolean isRootDirectory(String directory) {
        return directory == null || directory.equals("Root");
    }

    /**
     * Обновляет представление на основе текущей директории и списка файлов.
     * @param files массив файлов для отображения.
     * @param newPath путь к директории, которую нужно отобразить.
     */
    public void updateView(File[] files, String newPath) {
        if (files != null) {
            String[] fileNames = new String[files.length];
            if ("Root".equals(newPath)) {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getAbsolutePath();
                }
                mainView.getNavigationPanel().getUpperBtn().setEnabled(false);
            } else {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getName();
                }
                mainView.getNavigationPanel().getUpperBtn().setEnabled(true);
            }
            mainView.updateView(fileNames, currentDirectory);

            updateButtonsState();
        }
    }

    /**
     * Обновляет состояние кнопок навигации на панели.
     */
    private void updateButtonsState() {
        mainView.getNavigationPanel().getForwardBtn().setEnabled(!redoStack.isEmpty());
        mainView.getNavigationPanel().getBackBtn().setEnabled(!undoStack.isEmpty());
        if (isDirectoryChanged)
            updateSortCriteria();
    }

    /**
     * Обновляет критерий сортировки на панели инструментов.
     */
    private void updateSortCriteria() {
        mainView.getToolbarPanel().getSortComboBox().setSelectedItem("Name");
        isDirectoryChanged = false;
    }

    /**
     * Получает текущую директорию.
     * @return строка с текущей директорией.
     */
    public String getCurrentDirectory() {
        return currentDirectory;
    }
}
