package fileExplorer.utils;

import javax.swing.ImageIcon;

import java.awt.Image;

import java.net.URL;

/**
 * Утилитный класс для работы с изображениями.
 * Предоставляет методы для получения и масштабирования изображений.
 */
public class ImageUtils {
    /**
     * Получает предварительный просмотр изображения по указанному пути.
     * @param imagePath путь к изображению в ресурсах.
     * @return {@link ImageIcon} с изображением, если оно найдено, или {@code null}, если изображение не найдено.
     */
    public static ImageIcon getImagePreview(String imagePath) {
        URL imageUrl = getResource(imagePath);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        System.err.println("Image not found: " + imagePath);
        return null;
    }

    /**
     * Получает изображение с заданным размером.
     * @param imagePath путь к изображению в ресурсах.
     * @param imageSize максимальный размер изображения.
     * @return {@link ImageIcon} с масштабированным изображением, если оно найдено, или {@code null}, если изображение не найдено.
     */
    public static ImageIcon getImageIcon(String imagePath, int imageSize) {
        URL imageUrl = getResource(imagePath);
        if (imageUrl != null) {
            return scaleImage(new ImageIcon(imageUrl), imageSize, imageSize);
        }
        System.err.println("Image not found: " + imagePath);
        return null;
    }

    /**
     * Получает изображение с заданным путем и стандартным размером 15x15 пикселей.
     * @param imagePath путь к изображению в ресурсах.
     * @return {@link ImageIcon} с изображением, если оно найдено, или {@code null}, если изображение не найдено.
     */
    public static ImageIcon getImageIcon(String imagePath) {
        return getImageIcon(imagePath, 15);
    }

    /**
     * Масштабирует изображение до заданных размеров, сохраняя пропорции.
     * @param imageIcon {@link ImageIcon}, который нужно масштабировать.
     * @param maxWidth максимальная ширина изображения.
     * @param maxHeight максимальная высота изображения.
     * @return масштабированное {@link ImageIcon}.
     */
    public static ImageIcon scaleImage(ImageIcon imageIcon, int maxWidth, int maxHeight) {
        int originalWidth = imageIcon.getIconWidth();
        int originalHeight = imageIcon.getIconHeight();

        double scaleX = (double) maxWidth / originalWidth;
        double scaleY = (double) maxHeight / originalHeight;
        double scale = Math.min(scaleX, scaleY);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        Image scaledImage = imageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Получает ресурс изображения по заданному пути.
     * @param resourcePath путь к ресурсу изображения.
     * @return {@link URL} ресурса изображения или {@code null}, если изображение не найдено.
     */
    private static URL getResource(String resourcePath) {
        return ImageUtils.class.getClassLoader().getResource(resourcePath);
    }
}
