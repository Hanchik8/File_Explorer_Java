package fileExplorer.utils.iconProviders;

import fileExplorer.utils.ImageUtils;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, предоставляющий иконки для категорий боковой панели.
 * Загружает иконки для популярных категорий, таких как "Рабочий стол", "Документы", "Загрузки" и другие.
 */
public class SidebarIconProvider {
    private final Map<String, ImageIcon> sidebarIcons;

    /**
     * Конструктор класса. Инициализирует карту с иконками и загружает иконки для категорий.
     */
    public SidebarIconProvider() {
        sidebarIcons = new HashMap<>();
        loadSidebarIcons();
    }

    /**
     * Загружает иконки для популярных категорий боковой панели, таких как "Рабочий стол", "Документы" и другие.
     * Иконки сохраняются в карте {sidebarIcons} по ключу — названию категории.
     */
    private void loadSidebarIcons() {
        sidebarIcons.put("Desktop", ImageUtils.getImagePreview("resources/images/sidebarIcons/desktop.png"));
        sidebarIcons.put("Documents", ImageUtils.getImagePreview("resources/images/sidebarIcons/documentsIcon.png"));
        sidebarIcons.put("Downloads", ImageUtils.getImagePreview("resources/images/sidebarIcons/downloads.png"));
        sidebarIcons.put("Pictures", ImageUtils.getImagePreview("resources/images/sidebarIcons/picturesIcon.png"));
        sidebarIcons.put("Music", ImageUtils.getImagePreview("resources/images/sidebarIcons/musicIcon.png"));
        sidebarIcons.put("Videos", ImageUtils.getImagePreview("resources/images/sidebarIcons/videosIcon.png"));
        sidebarIcons.put("My Computer", ImageUtils.getImagePreview("resources/images/sidebarIcons/myComputer.png"));
    }

    /**
     * Получает иконку для категории боковой панели.
     * @param category категория, для которой требуется иконка (например, "Документы", "Загрузки").
     * @return {@link ImageIcon} — иконка для категории, масштабированная до 20x20 пикселей.
     */
    public ImageIcon getIconForCategory(String category) {
        return ImageUtils.scaleImage(sidebarIcons.get(category), 20, 20);
    }
}
