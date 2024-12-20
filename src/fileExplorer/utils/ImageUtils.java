package fileExplorer.utils;

import javax.swing.ImageIcon;

import java.awt.Image;

import java.net.URL;

public class ImageUtils {
    public static ImageIcon getImagePreview(String imagePath, int panelWidth) {
        URL imageUrl = getResource(imagePath);
        if (imageUrl != null) {
            return scaleImage(new ImageIcon(imageUrl), panelWidth, 350);
        }
        System.err.println("Image not found: " + imagePath);
        return null;
    }

    public static ImageIcon getImageIcon(String imagePath, int imageSize) {
        URL imageUrl = getResource(imagePath);
        if (imageUrl != null) {
            return scaleImage(new ImageIcon(imageUrl), imageSize, imageSize);
        }
        System.err.println("Image not found: " + imagePath);
        return null;
    }

    public static ImageIcon getImageIcon(String imagePath) {
        return getImageIcon(imagePath, 15);
    }

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

    private static URL getResource(String resourcePath) {
        return ImageUtils.class.getClassLoader().getResource(resourcePath);
    }
}
