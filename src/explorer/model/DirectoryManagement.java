package explorer.model;

import explorer.view.ExplorerView;

import java.io.File;
import java.util.Stack;

public class DirectoryManagement {
    private ExplorerView explorerView;
    private String currentDirectory = "Root"; // Текущая директория
    private Stack<String> undoStack; // Стек для команды "Назад"
    private Stack<String> redoStack; // Стек для команды "Вперёд"

    public DirectoryManagement(ExplorerView explorerView) {
        this.explorerView = explorerView;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Возвращает содержимое директории при переходе "назад".
     *
     * @return массив файлов в новой текущей директории.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentDirectory);
            currentDirectory = undoStack.pop();
            updateDirectory();
        }
    }

    /**
     * Возвращает содержимое директории при переходе "вперёд".
     *
     * @return массив файлов в новой текущей директории.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentDirectory);
            currentDirectory = redoStack.pop();
            updateDirectory();
        }
    }

    /**
     * Переходит в родительскую директорию.
     *
     * @return содержимое родительской директории.
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
     * Переключается на указанную директорию.
     *
     * @param newDirectory путь к новой директории.
     */
    public void updateDirectory(String newDirectory) {
        File[] listOfFiles;
        if (isRootDirectory(newDirectory)) {
            listOfFiles = getInitialDirectories();
        } else {
            if (!currentDirectory.equals(newDirectory)) {
                undoStack.push(currentDirectory);
            }
            currentDirectory = newDirectory;
            listOfFiles = listDirectoryContent(new File(newDirectory));
        }
        updateView(listOfFiles, currentDirectory);
    }

    public void updateDirectory() {
        File[] listOfFiles;
        if (isRootDirectory(currentDirectory)) {
            listOfFiles = getInitialDirectories();
        } else {
            listOfFiles = listDirectoryContent(new File(currentDirectory));
        }
        updateView(listOfFiles, currentDirectory);
    }

    /**
     * Возвращает список файлов в указанной директории.
     *
     * @param directory директория, содержимое которой нужно получить.
     * @return массив файлов или пустой массив, если директория пуста.
     */
    private File[] listDirectoryContent(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null)
            return contents;
        else
            return new File[0];
    }

    /**
     * Возвращает содержимое корневых директорий.
     *
     * @return массив корневых директорий.
     */
    public File[] getInitialDirectories() {
        currentDirectory = "Root";

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return File.listRoots();
        } else if (os.contains("mac")) {
            return new File("/Volumes").listFiles();
        } else if (os.contains("linux")) {
            File[] mntPathFiles = new File("/mnt").listFiles();
            File[] mediaPathFiles = new File("/media").listFiles();
            File[] linuxDisks = new File[mntPathFiles.length + mediaPathFiles.length];

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
     * Проверяет, является ли директория корневой.
     *
     * @param directory путь к директории.
     * @return true, если директория является корневой.
     */
    private boolean isRootDirectory(String directory) {
        return directory == null || directory.equals("Root");
    }

    // Метод для изменения директории
    public void updateView(File[] files, String newPath) {
        if (files != null) {
            String[] fileNames = new String[files.length];
            if ("Root".equals(newPath)) {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getAbsolutePath();
                }
                explorerView.getTopMenu().getUpperBtn().setEnabled(false);
            } else {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getName();
                }
                explorerView.getTopMenu().getUpperBtn().setEnabled(true);
            }
            explorerView.getMainPanel().updateFileListModel(fileNames);
            explorerView.getTopMenu().setCurrentPath(newPath);

            updateNavigationButtonsState();
        }
    }

    // Обновление состояния кнопок навигации
    private void updateNavigationButtonsState() {
        explorerView.getTopMenu().getForwardBtn().setEnabled(!redoStack.isEmpty());
        explorerView.getTopMenu().getBackBtn().setEnabled(!undoStack.isEmpty());
    }
}
