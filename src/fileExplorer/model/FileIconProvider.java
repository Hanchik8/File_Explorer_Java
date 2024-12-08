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
        fileIcons.put("jpeg", ImageUtils.getImagePreview("resources/images/fileIcons/jpgIcon.png", 25));
        fileIcons.put("png", ImageUtils.getImagePreview("resources/images/fileIcons/pngIcon.png", 25));
        fileIcons.put("pdf", ImageUtils.getImagePreview("resources/images/fileIcons/pdfIcon.png", 25));
        fileIcons.put("pptx", ImageUtils.getImagePreview("resources/images/fileIcons/pptxIcon.png", 25));
        fileIcons.put("ppt", ImageUtils.getImagePreview("resources/images/fileIcons/pptxIcon.png", 25));
        fileIcons.put("ai", ImageUtils.getImagePreview("resources/images/fileIcons/aiIcon.png", 25));
        fileIcons.put("avi", ImageUtils.getImagePreview("resources/images/fileIcons/aviIcon.png", 25));
        fileIcons.put("css", ImageUtils.getImagePreview("resources/images/fileIcons/cssIcon.png", 25));
        fileIcons.put("csv", ImageUtils.getImagePreview("resources/images/fileIcons/csvIcon.png", 25));
        fileIcons.put("doc", ImageUtils.getImagePreview("resources/images/fileIcons/docIcon.png", 25));
        fileIcons.put("docx", ImageUtils.getImagePreview("resources/images/fileIcons/docxIcon.png", 25));
        fileIcons.put("exe", ImageUtils.getImagePreview("resources/images/fileIcons/exeIcon.png", 25));
        fileIcons.put("gif", ImageUtils.getImagePreview("resources/images/fileIcons/gifIcon.png", 25));
        fileIcons.put("html", ImageUtils.getImagePreview("resources/images/fileIcons/htmlIcon.png", 25));
        fileIcons.put("java", ImageUtils.getImagePreview("resources/images/fileIcons/javaIcon.png", 25));
        fileIcons.put("js", ImageUtils.getImagePreview("resources/images/fileIcons/jsIcon.png", 25));
        fileIcons.put("mov", ImageUtils.getImagePreview("resources/images/fileIcons/movIcon.png", 25));
        fileIcons.put("mp3", ImageUtils.getImagePreview("resources/images/fileIcons/mp3Icon.png", 25));
        fileIcons.put("mp4", ImageUtils.getImagePreview("resources/images/fileIcons/mp4Icon.png", 25));
        fileIcons.put("sql", ImageUtils.getImagePreview("resources/images/fileIcons/sqlIcon.png", 25));
        fileIcons.put("xls", ImageUtils.getImagePreview("resources/images/fileIcons/xlsIcon.png", 25));
        fileIcons.put("xlsx", ImageUtils.getImagePreview("resources/images/fileIcons/xlsxIcon.png", 25));
        fileIcons.put("zip", ImageUtils.getImagePreview("resources/images/fileIcons/zipIcon.png", 25));
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

