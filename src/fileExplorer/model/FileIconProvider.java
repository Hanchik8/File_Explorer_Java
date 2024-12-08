package fileExplorer.model;


import fileExplorer.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FileIconProvider {
    private final Map<String, Icon> fileIcons;

    public FileIconProvider() {
        fileIcons = new HashMap<>();
        loadFileIcons();
    }

    private void loadFileIcons() {

        fileIcons.put("txt", ImageUtils.getImagePreview("resources/images/fileIcons/txtIcon.png", 25));
        fileIcons.put("jpg", ImageUtils.getImagePreview("resources/images/fileIcons/jpgIcon.png", 25));
        fileIcons.put("png", ImageUtils.getImagePreview("resources/images/fileIcons/pngIcon.png", 25));
        fileIcons.put("pdf", ImageUtils.getImagePreview("resources/images/fileIcons/pdfIcon.png", 25));
        fileIcons.put("pptx", ImageUtils.getImagePreview("resources/images/fileIcons/pptxIcon.png", 25));
        fileIcons.put("folder", ImageUtils.getImagePreview("resources/images/fileIcons/folderIcon.png",25));
    }

    public Icon getIconForFile(String filename) {
        String extension = getFileExtension(filename);
        return fileIcons.getOrDefault(extension,  ImageUtils.getImagePreview("resources/images/fileIcons/folderIcon.png",25));
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex > 0 && dotIndex < filename.length() - 1) ? filename.substring(dotIndex + 1) : "";
    }

    private ImageIcon scaleImageIcon(String iconPath) {
        ImageIcon imageIcon = new ImageIcon(iconPath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}

