package fileExplorer.utils;

import javax.swing.ImageIcon;

import java.awt.Image;

import java.net.URL;

public class ImageUtils {
    /**
     * Returns a scaled image based on the specified path and panel width.
     * 
     * @param imagePath  Path to the image.
     * @param panelWidth Width of the panel.
     * @return Scaled icon or null if the image is not found.
     */
    public static ImageIcon getImagePreview(String imagePath, int panelWidth) {
        URL imageUrl = getResource(imagePath);
        if (imageUrl != null) {
            return scaleImage(new ImageIcon(imageUrl), panelWidth, 350);
        }
        System.err.println("Image not found: " + imagePath);
        return null;
    }

    /**
     * Returns a scaled icon with fixed dimensions.
     * 
     * @param imagePath Path to the image.
     * @param imageSize Size of the image (width and height).
     * @return Scaled icon or null if the image is not found.
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
     * Returns a scaled icon with fixed dimensions (default size is 15x15).
     * 
     * @param imagePath Path to the image.
     * @return Scaled icon or null if the image is not found.
     */
    public static ImageIcon getImageIcon(String imagePath) {
        return getImageIcon(imagePath, 15);
    }

    /**
     * Scales the image while maintaining its aspect ratio.
     * 
     * @param imageIcon The original icon.
     * @param maxWidth  Maximum width of the resulting image.
     * @param maxHeight Maximum height of the resulting image.
     * @return Scaled icon.
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
     * Retrieves a resource by its path.
     * 
     * @param resourcePath Path to the resource.
     * @return URL of the resource or null if it is not found.
     */
    private static URL getResource(String resourcePath) {
        return ImageUtils.class.getClassLoader().getResource(resourcePath);
    }

}
