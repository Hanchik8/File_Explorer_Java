package src.model;

import src.view.View;

import java.io.File;
import java.util.Stack;

public class DirectoryManagement {
    private View explorerView;
    private String currentDirectory = "Root";
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public DirectoryManagement(View explorerView) {
        this.explorerView = explorerView;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentDirectory);
            currentDirectory = undoStack.pop();
            updateDirectory();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentDirectory);
            currentDirectory = redoStack.pop();
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
        if (isRootDirectory(newDirectory)) {
           return getInitialDirectories();
        } else {
            if (!currentDirectory.equals(newDirectory)) {
                undoStack.push(currentDirectory);
            }
            currentDirectory = newDirectory;
            return listDirectoryContent(new File(newDirectory));
        }
    }

    public File[] updateDirectory() {
        if (isRootDirectory(currentDirectory)) {
            return getInitialDirectories();
        } else {
            return listDirectoryContent(new File(currentDirectory));
        }
    }

    private File[] listDirectoryContent(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null)
            return contents;
        else
            return new File[0];
    }

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

    private boolean isRootDirectory(String directory) {
        return directory == null || directory.equals("Root");
    }
}
