package explorer.view.viewComponents;

import explorer.controller.FileDetailsController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDetailsPanel {
    private JPanel fileDetailsPanel;
    private FileDetailsController fileDetailsController;  // Экземпляр контроллера

    public FileDetailsPanel() {
        fileDetailsPanel = new JPanel();
        fileDetailsPanel.setLayout(new BoxLayout(fileDetailsPanel, BoxLayout.Y_AXIS));
        fileDetailsPanel.setPreferredSize(new Dimension(400, fileDetailsPanel.getHeight()));

        // Создание контроллера, передаем саму панель
        fileDetailsController = new FileDetailsController(this);
    }

    /**
     * Создаёт метку с иконкой в зависимости от расширения файла.
     * @param imagePath Путь к изображению для отображения.
     * @param extension Расширение файла.
     */
    private void createImageLabel(String imagePath, String extension) {
        JLabel imageLabel = new JLabel();

        if (extension == null) {
            imageLabel.setIcon(scaleImage("src/imageIcon/fileIcons/folderIcon.png"));
        } else if (extension.equals("xlsx")) {
            imageLabel.setIcon(scaleImage("src/imageIcon/fileIcons/exelIcon.png"));
        } else if (extension.equals("txt")) {
            imageLabel.setIcon(scaleImage("src/imageIcon/fileIcons/noteIcon.png"));
        } else if (extension.equals("pptx")) {
            imageLabel.setIcon(scaleImage("src/imageIcon/fileIcons/pptIcon.png"));
        } else if (extension.equals("docx")) {
            imageLabel.setIcon(scaleImage("src/imageIcon/fileIcons/docIcon.png"));
        } else {
            imageLabel.setIcon(scaleImage(imagePath));
        }

        fileDetailsPanel.add(imageLabel);
    }

    /**
     * Создаёт метку с именем файла и иконкой.
     * @param fileName Имя файла.
     * @param extension Расширение файла.
     */
    private void createNameLabel(String fileName, String extension) {
        JLabel nameLabel = new JLabel(fileName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        if (extension == null) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/folderIcon.png"));
        } else if (extension.equals("xlsx")) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/exelIcon.png"));
        } else if (extension.equals("txt")) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/noteIcon.png"));
        } else if (extension.equals("pptx")) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/pptIcon.png"));
        } else if (extension.equals("docx")) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/docIcon.png"));
        } else if (extension.equals("png")) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/pngIcon.png"));
        } else if (extension.equals("jpg") || extension.equals("jpeg")) {
            nameLabel.setIcon(scaleImageIcon("src/imageIcon/fileIcons/pngIcon.png"));
        }

        fileDetailsPanel.add(nameLabel);
    }

    /**
     * Создаёт панель с дополнительной информацией о файле (путь, размер, дата последнего изменения).
     * @param currentFile Файл, о котором нужно вывести информацию.
     */
    private void createExtraDetailsPanel(File currentFile) {
        JPanel extraDetailsPanel = new JPanel();
        extraDetailsPanel.setLayout(new BoxLayout(extraDetailsPanel, BoxLayout.Y_AXIS));

        extraDetailsPanel.add(new JLabel("Path: " + currentFile.getParent()));

        if (currentFile.isFile())
            extraDetailsPanel.add(new JLabel("Size: " + (Math.round(currentFile.length() / 1024.0 * 100.0) / 100.0) + " KB"));

        Date lastModifiedDate = new Date(currentFile.lastModified());
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        extraDetailsPanel.add(new JLabel("Last Modified: " + formattedDate.format(lastModifiedDate)));

        fileDetailsPanel.add(extraDetailsPanel);
    }

    /**
     * Обновляет панель с деталями о файле.
     * @param currentFile Файл, для которого обновляются детали.
     * @param extension Расширение файла.
     */
    public void updateFileDetailsPanel(File currentFile, String extension) {
        fileDetailsPanel.removeAll();

        createImageLabel(currentFile.getAbsolutePath(), extension);
        createNameLabel(currentFile.getName(), extension);
        createExtraDetailsPanel(currentFile);

        fileDetailsPanel.revalidate();
        fileDetailsPanel.repaint();
    }

    /**
     * Масштабирует изображение по заданному пути.
     * @param iconPath Путь к изображению.
     * @return Масштабированная иконка.
     */
    private ImageIcon scaleImage(String iconPath) {
        ImageIcon imageIcon = new ImageIcon(iconPath);

        double scaleX = (double) fileDetailsPanel.getWidth() / imageIcon.getIconWidth();
        double scaleY = (double) 350 / imageIcon.getIconHeight();
        double scale = Math.min(scaleX, scaleY) - 0.001;

        int imageIconWidth = (int)(imageIcon.getIconWidth() * scale);
        int imageIconHeight = (int)(imageIcon.getIconHeight() * scale);

        Image scaledImage = imageIcon.getImage().getScaledInstance(imageIconWidth, imageIconHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Масштабирует иконку для имени файла.
     * @param iconPath Путь к иконке.
     * @return Масштабированная иконка.
     */
    private ImageIcon scaleImageIcon(String iconPath) {
        ImageIcon imageIcon = new ImageIcon(iconPath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public JPanel getFileDetailsPanel() {
        return fileDetailsPanel;
    }

    /**
     * Этот метод будет вызывать контроллер для обновления деталей о файле.
     * @param selectedFile Выбранный файл.
     */
    public void updateFileDetails(File selectedFile) {
        String extension = getFileExtension(selectedFile);
        fileDetailsController.updateFileDetails(selectedFile);
    }

    /**
     * Получает расширение файла.
     * @param file Файл, для которого нужно получить расширение.
     * @return Расширение файла или null, если файл - директория.
     */
    private String getFileExtension(File file) {
        if (file.isDirectory()) {
            return null;
        }

        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex == -1) {
            return ""; // Без расширения
        }

        return fileName.substring(dotIndex + 1).toLowerCase();
    }
}
