package src.model;

import src.view.View;

import java.io.File;
import java.util.Stack;

/**
 * This class manages directory navigation and undo/redo/moveToParent operations for a file explorer.
 */
public class DirectoryManagement {
    private View explorerView; // Reference to the view component for updating view
    private String currentDirectory = "Root"; // // Tracks the current directory
    private Stack<String> undoStack; // Stack for undoing directory navigation
    private Stack<String> redoStack; // Stack for redoing directory navigation

    /**
     * Constructor initializes the view and stacks for undo/redo functionality.
     * @param explorerView the view component for updating the view
     */
    public DirectoryManagement(View explorerView) {
        this.explorerView = explorerView;
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
     * @param newDirectory the directory to move to
     * @return an array of files/directories in the new directory
     */
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

    /**
     * Refreshes the current directory and retrieves its contents.
     * @return an array of files/directories in the current directory
     */
    public File[] updateDirectory() {
        if (isRootDirectory(currentDirectory)) {
            return getInitialDirectories();
        } else {
            return listDirectoryContent(new File(currentDirectory));
        }
    }

    /**
     * Lists the contents of a directory.
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
     * Checks if the given directory represents the root.
     * @param directory the directory to check
     * @return true if the directory is the root, false otherwise
     */
    private boolean isRootDirectory(String directory) {
        return directory == null || directory.equals("Root");
    }
}
