package fileExplorer.utils.iconProviders;

import fileExplorer.utils.ImageUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, предоставляющий иконки для различных типов файлов.
 * Загружает иконки для определённых расширений файлов и предоставляет методы для получения соответствующих иконок.
 */
public class FileIconProvider {
    private final Map<String, ImageIcon> fileIcons;
    private final ImageIcon defaultFolderIcon;

    /**
     * Конструктор класса. Инициализирует карту с иконками и загружает иконки для файлов.
     */
    public FileIconProvider() {
        fileIcons = new HashMap<>();
        loadFileIcons();
        defaultFolderIcon = ImageUtils.getImagePreview("resources/images/fileIcons/folderIcon.png");
    }

    /**
     * Загружает иконки для популярных типов файлов.
     * Иконки сохраняются в карте {fileIcons} по ключу — расширению файла.
     */
    private void loadFileIcons() {
        fileIcons.put("txt", ImageUtils.getImagePreview("resources/images/fileIcons/txtIcon.png"));
        fileIcons.put("pdf", ImageUtils.getImagePreview("resources/images/fileIcons/pdfIcon.png"));
        fileIcons.put("jpg", ImageUtils.getImagePreview("resources/images/fileIcons/jpgIcon.png"));
        fileIcons.put("jpeg", ImageUtils.getImagePreview("resources/images/fileIcons/jpgIcon.png"));
        fileIcons.put("png", ImageUtils.getImagePreview("resources/images/fileIcons/pngIcon.png"));
        fileIcons.put("doc", ImageUtils.getImagePreview("resources/images/fileIcons/docIcon.png"));
        fileIcons.put("docx", ImageUtils.getImagePreview("resources/images/fileIcons/docxIcon.png"));
        fileIcons.put("gif", ImageUtils.getImagePreview("resources/images/fileIcons/gifIcon.png"));
        fileIcons.put("xls", ImageUtils.getImagePreview("resources/images/fileIcons/xlsIcon.png"));
        fileIcons.put("xlsx", ImageUtils.getImagePreview("resources/images/fileIcons/xlsxIcon.png"));
        fileIcons.put("ppt", ImageUtils.getImagePreview("resources/images/fileIcons/pptIcon.png"));
        fileIcons.put("pptx", ImageUtils.getImagePreview("resources/images/fileIcons/pptIcon.png"));
        fileIcons.put("folder", defaultFolderIcon);
    }

    /**
     * Получает иконку для файла по его имени. Если иконка для данного расширения не найдена, возвращается иконка папки.
     * @param filename имя файла, для которого требуется иконка.
     * @return {@link Icon} — иконка для файла, масштабированная до 25x25 пикселей.
     */
    public Icon getIconForFile(String filename) {
        if (filename == null || filename.isEmpty() || getFileExtension(filename).isEmpty()) {
            return ImageUtils.scaleImage(defaultFolderIcon, 25, 25);
        }

        String extension = getFileExtension(filename).toLowerCase();
        return ImageUtils.scaleImage(fileIcons.getOrDefault(extension, defaultFolderIcon), 25, 25);
    }

    /**
     * Получает иконку для файла по его имени с возможностью указания размера панели для масштабирования.
     * @param filename имя файла, для которого требуется иконка.
     * @param panelWidth ширина панели, на которой будет отображена иконка. Высота будет масштабироваться пропорционально.
     * @return {@link Icon} — иконка для файла, масштабированная до заданной ширины.
     */
    public Icon getIconForFile(String filename, int panelWidth) {
        if (filename == null || filename.isEmpty() || getFileExtension(filename).isEmpty()) {
            return ImageUtils.scaleImage(defaultFolderIcon, panelWidth, 350);
        }

        String extension = getFileExtension(filename).toLowerCase();
        return ImageUtils.scaleImage(fileIcons.getOrDefault(extension, defaultFolderIcon), panelWidth, 350);
    }

    /**
     * Получает расширение файла.
     * @param filename имя файла.
     * @return расширение файла, или пустую строку, если расширение не найдено.
     */
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex > 0 && dotIndex < filename.length() - 1) ? filename.substring(dotIndex + 1) : "";
    }
}
