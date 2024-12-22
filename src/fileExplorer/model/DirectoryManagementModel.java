package fileExplorer.model;

import fileExplorer.model.enums.SortCriteria;
import fileExplorer.view.MainView;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DirectoryManagementModel {
    private MainView mainView;
    private String currentDirectory = "Root";
    private Stack<String> undoStack;
    private Stack<String> redoStack;
    private boolean isDirectoryChanged = false;

    public DirectoryManagementModel(MainView explorerView) {
        this.mainView = explorerView;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

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

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentDirectory);
            currentDirectory = undoStack.pop();

            isDirectoryChanged = true;
            updateDirectory();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentDirectory);
            currentDirectory = redoStack.pop();

            isDirectoryChanged = true;
            updateDirectory();
        }
    }

    public void moveToParentDirectory() {
        File current = new File(currentDirectory);
        if (current.getParent() != null) {
            updateDirectory(current.getParent());
        } else {
            currentDirectory = "Root";
            updateDirectory();
        }
    }

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

    public File[] listDirectoryContent(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null)
            return filterFiles(contents);
        else
            return new File[0];
    }

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

    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return null;
    }

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

    public String getCurrentDirectory() {
        return currentDirectory;
    }
}
