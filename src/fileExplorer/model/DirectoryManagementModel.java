package fileExplorer.model;

import fileExplorer.view.MainView;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class manages directory navigation and undo/redo/moveToParent operations
 * for a file explorer.
 */
public class DirectoryManagementModel {
    private MainView mainView; // Reference to the view component for updating view
    private String currentDirectory = "Root"; // // Tracks the current directory
    private Stack<String> undoStack; // Stack for undoing directory navigation
    private Stack<String> redoStack; // Stack for redoing directory navigation
    private boolean isUndoOrRedoPressed = false;

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
     * Navigates to the previous directory in the undo stack.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentDirectory);
            currentDirectory = undoStack.pop();
            isUndoOrRedoPressed = true;
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
            isUndoOrRedoPressed = true;
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
    public void updateDirectory(String newDirectory) {
        redoStack.clear();
        File[] listOfFiles;
        if (isRootDirectory(newDirectory)) {
            listOfFiles = getInitialDirectories();
        } else {
            if (!currentDirectory.equals(newDirectory)) {
                undoStack.push(currentDirectory);
            }
            currentDirectory = newDirectory;
            listOfFiles = filterFiles(listDirectoryContent(new File(newDirectory)));
        }
        updateView(listOfFiles, currentDirectory);
    }

    /**
     * Refreshes the current directory and retrieves its contents.
     * 
     * @return an array of files/directories in the current directory
     */
    public void updateDirectory() {
        File[] listOfFiles;
        if (isRootDirectory(currentDirectory)) {
            listOfFiles = getInitialDirectories();
        } else {
            listOfFiles = filterFiles(listDirectoryContent(new File(currentDirectory)));
        }
        updateView(listOfFiles, currentDirectory);
    }

    private File[] filterFiles(File[] fileList) {
        String[] listOfFormats = { "txt", "png", "jpg", "jpeg", "ppt", "pptx", "docx", "doc", "xls", "xlsx" };
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

        File[] filteredFiles = new File[acceptableFiles.size()];
        for (int i = 0; i < filteredFiles.length; i++) {
            filteredFiles[i] = acceptableFiles.get(i);
        }

        return filteredFiles;
    }

    /**
     * Lists the contents of a directory.
     * 
     * @param directory the directory whose contents are to be listed
     * @return an array of files/directories in the directory
     */
    private File[] listDirectoryContent(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null)
            return contents;
        else
            return new File[0];
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

    public void updateView(File[] files, String newPath) {
        if (files != null) {
            String[] fileNames = new String[files.length];
            if ("Root".equals(newPath)) {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getAbsolutePath();
                }
                mainView.getTopMenu().getUpperBtn().setEnabled(false);
            } else {
                for (int i = 0; i < files.length; i++) {
                    fileNames[i] = files[i].getName();
                }
                mainView.getTopMenu().getUpperBtn().setEnabled(true);
            }
            mainView.updateView(fileNames, currentDirectory);

            updateNavigationButtonsState();
        }
    }

    // Обновление состояния кнопок навигации
    private void updateNavigationButtonsState() {
        mainView.getTopMenu().getForwardBtn().setEnabled(!redoStack.isEmpty());
        mainView.getTopMenu().getBackBtn().setEnabled(!undoStack.isEmpty());
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }
}
