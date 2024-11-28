package src.model;

import src.view.View;

import java.io.File;

public class DirectoryManagement {
    private View explorerView;
    private String currentDirectory = "Root";

    public DirectoryManagement(View explorerView) {
        this.explorerView = explorerView;
    }

    public File[] updateDirectory(String newDirectory) {
        if (isRootDirectory(newDirectory)) {
           return getInitialDirectories();
        } else {
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
