package fileExplorer.model;

import fileExplorer.model.enums.SortCriteria;
import fileExplorer.view.MainView;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * This class manages directory navigation and undo/redo/moveToParent operations
 * for a file explorer.
 */
public class DirectoryManagementModel {
    private MainView mainView; // Reference to the view component for updating view
    private String currentDirectory = "Root"; // Tracks the current directory
    private Stack<String> undoStack; // Stack for undoing directory navigation
    private Stack<String> redoStack; // Stack for redoing directory navigation
    private boolean isDirectoryChanged = false;
    private boolean isShowHiddenFiles = false;

    /**
     * Constructor initializes the view and stacks for undo/redo functionality.
     *
     * @param explorerView the view component for updating the view
     */
    public DirectoryManagementModel(MainView explorerView) {
        this.mainView = explorerView;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Обновляет и сортирует список файлов по выбранному критерию.
     *
     * @param files    массив файлов для отображения.
     * @param criteria критерий сортировки.
     */
    public File[] updateAndSortFileList(File[] files, SortCriteria criteria) {
        // Фильтруем скрытые файлы
        files = Arrays.stream(files)
                .filter(file -> !file.isHidden() || isShowHiddenFiles) // Исключаем скрытые файлы
                .toArray(File[]::new);

        // Сортируем файлы
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

        return files; // Возвращаем отсортированный список без скрытых файлов
    }

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
     * Navigates to the previous directory in the undo stack.
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
     * Navigates to the next directory in the redo stack.
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
     * Moves to the parent directory of the current directory.
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
     * Updates the current directory and retrieves its contents.
     *
     * @param newDirectory the directory to move to
     * @return an array of files/directories in the new directory
     */
    public File[] updateDirectory(String newDirectory) {
        redoStack.clear();
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
        isDirectoryChanged = true;
        updateView(listOfFiles, currentDirectory);

        return listOfFiles;
    }

    /**
     * Refreshes the current directory and retrieves its contents.
     *
     * @return an array of files/directories in the current directory
     */
    public File[] updateDirectory() {
        File[] listOfFiles;
        if (isRootDirectory(currentDirectory)) {
            listOfFiles = getInitialDirectories();
        } else {
            listOfFiles = filterFiles(listDirectoryContent(new File(currentDirectory)));
        }
        updateView(listOfFiles, currentDirectory);

        return listOfFiles;
    }

    /**
     * Lists the contents of a directory.
     *
     * @param directory the directory whose contents are to be listed
     * @return an array of files/directories in the directory
     */
    public File[] listDirectoryContent(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null)
            return filterFiles(contents);
        else
            return new File[0];
    }

    /**
     * Filters the provided file list to include only acceptable files and
     * directories.
     *
     * @param fileList array of files to filter
     * @return filtered array of files
     */
    private File[] filterFiles(File[] fileList) {
        String[] listOfFormats = { "txt", "png", "jpg", "jpeg", "ppt", "pptx", "docx", "doc", "xls", "xlsx" };
        ArrayList<File> acceptableFiles = new ArrayList<>();
        for (File file : fileList) {
            if (!file.isHidden() || isShowHiddenFiles) {
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
     * Retrieves the initial set of directories based on the operating system.
     *
     * @return an array of files representing the root directories
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
     * Retrieves the file extension of the provided file name.
     *
     * @param fileName the name of the file
     * @return the file extension as a string
     */
    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

    /**
     * Checks if the given directory represents the root.
     *
     * @param directory the directory to check
     * @return true if the directory is the root, false otherwise
     */
    private boolean isRootDirectory(String directory) {
        return directory == null || directory.equals("Root");
    }

    /**
     * Updates the view with the provided files and path.
     *
     * @param files   array of files to display
     * @param newPath the new path for the directory
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
     * Updates the state of navigation buttons based on undo/redo stacks.
     */
    private void updateButtonsState() {
        mainView.getNavigationPanel().getForwardBtn().setEnabled(!redoStack.isEmpty());
        mainView.getNavigationPanel().getBackBtn().setEnabled(!undoStack.isEmpty());
        if (isDirectoryChanged)
            updateSortCriteria();
    }

    private void updateSortCriteria() {
        mainView.getToolbarPanel().getSortComboBox().setSelectedItem("Name");
        isDirectoryChanged = false;
    }

    /**
     * Retrieves the current directory being viewed.
     *
     * @return the current directory as a string
     */
    public String getCurrentDirectory() {
        return currentDirectory;
    }

    /**
     * @param isSelected boolean showHiddenFilesCheckBox.isSelected() in ToolBarPanel
     */
    public void updateShowHiddenFiles(boolean isSelected) {
        isShowHiddenFiles = isSelected;
        updateDirectory();
    }
}
