package fileExplorer.model;

import fileExplorer.utils.ImageUtils;

import javax.swing.Icon;
import java.util.HashMap;
import java.util.Map;

public class FileIconProvider {
    private final Map<String, Icon> fileIcons;
    private final Icon defaultFolderIcon;

    public FileIconProvider() {
        fileIcons = new HashMap<>();
        loadFileIcons();
        // Default folder icon is preloaded to avoid repeated calls to ImageUtils
        defaultFolderIcon = ImageUtils.getImagePreview("resources/images/fileIcons/folderIcon.png", 25);
    }

    private void loadFileIcons() {
        // Load standard icons for known file types
        fileIcons.put("txt", ImageUtils.getImagePreview("resources/images/fileIcons/txtIcon.png", 25));
        fileIcons.put("pdf", ImageUtils.getImagePreview("resources/images/fileIcons/pdfIcon.png", 25));
        fileIcons.put("jpg", ImageUtils.getImagePreview("resources/images/fileIcons/jpgIcon.png", 25));
        fileIcons.put("jpeg", ImageUtils.getImagePreview("resources/images/fileIcons/jpgIcon.png", 25));
        fileIcons.put("png", ImageUtils.getImagePreview("resources/images/fileIcons/pngIcon.png", 25));
        fileIcons.put("doc", ImageUtils.getImagePreview("resources/images/fileIcons/docIcon.png", 25));
        fileIcons.put("docx", ImageUtils.getImagePreview("resources/images/fileIcons/docxIcon.png", 25));
        fileIcons.put("gif", ImageUtils.getImagePreview("resources/images/fileIcons/gifIcon.png", 25));
        fileIcons.put("xls", ImageUtils.getImagePreview("resources/images/fileIcons/xlsIcon.png", 25));
        fileIcons.put("xlsx", ImageUtils.getImagePreview("resources/images/fileIcons/xlsxIcon.png", 25));
        fileIcons.put("ppt", ImageUtils.getImagePreview("resources/images/fileIcons/pptIcon.png", 25));
        fileIcons.put("pptx", ImageUtils.getImagePreview("resources/images/fileIcons/pptIcon.png", 25));
        fileIcons.put("folder", defaultFolderIcon);
    }

    public Icon getIconForFile(String filename) {
        // If the file name is not provided or doesn't have an extension, return the folder icon by default
        if (filename == null || filename.isEmpty() || getFileExtension(filename).isEmpty()) {
            return defaultFolderIcon;
        }

        String extension = getFileExtension(filename).toLowerCase();
        // Return the appropriate file icon based on the file extension, or a default unknown icon if not found
        return fileIcons.getOrDefault(extension, ImageUtils.getImagePreview("resources/images/fileIcons/unknownIcon.png", 25));
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        // Return an empty string if no extension is found
        return (dotIndex > 0 && dotIndex < filename.length() - 1) ? filename.substring(dotIndex + 1) : "";
    }
}
