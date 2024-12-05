package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Font;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Панель, отображающая подробности о файле: имя, размер, дата последнего изменения и иконки.
 */
public class FileDetailsPanel {
    private JPanel fileDetailsPanel;

    public FileDetailsPanel(){
        fileDetailsPanel = new JPanel();
        fileDetailsPanel.setLayout(new BoxLayout(fileDetailsPanel, BoxLayout.Y_AXIS));
        fileDetailsPanel.setPreferredSize(new Dimension(400, fileDetailsPanel.getHeight()));
    }

    /**
     * Создаёт метку с иконкой в зависимости от расширения файла.
     * @param imagePath Путь к изображению для отображения.
     * @param extension Расширение файла.
     */
    private void createImageLabel(String imagePath, String extension) {
        JLabel imageLabel = new JLabel();
        int panelWidth = fileDetailsPanel.getWidth();

        if (extension == null) {
            imageLabel.setIcon(ImageUtils.getImagePreview("images/fileIcons/folderIcon.png", panelWidth));
        } else if (extension.equals("xlsx")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("images/fileIcons/exelIcon.png", panelWidth));
        } else if (extension.equals("txt")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("images/fileIcons/noteIcon.png", panelWidth));
        } else if (extension.equals("pptx")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("images/fileIcons/pptIcon.png", panelWidth));
        } else if (extension.equals("docx")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("images/fileIcons/docIcon.png", panelWidth));
        } else {
            ImageIcon scaledImage = ImageUtils.scaleImage(new ImageIcon(imagePath), panelWidth, 350);
            imageLabel.setIcon(scaledImage);
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
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/folderIcon.png", 25));
        } else if (extension.equals("xlsx")) {
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/exelIcon.png", 25));
        } else if (extension.equals("txt")) {
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/noteIcon.png", 25));
        } else if (extension.equals("pptx")) {
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/pptIcon.png", 25));
        } else if (extension.equals("docx")) {
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/docIcon.png", 25));
        } else if (extension.equals("png")) {
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/pngIcon.png", 25));
        } else if (extension.equals("jpg") || extension.equals("jpeg")) {
            nameLabel.setIcon(ImageUtils.getImageIcon("images/fileIcons/pngIcon.png", 25));
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
//            calculateDirectorySize(currentFile.toPath());

        Date lastModifiedDate = new Date(currentFile.lastModified());
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        extraDetailsPanel.add(new JLabel("Last Modified: " + formattedDate.format(lastModifiedDate)));

        fileDetailsPanel.add(extraDetailsPanel);
    }

    public static long calculateDirectorySize(Path path){
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory");
        }

        final long[] size = {0};

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size[0] += attrs.size();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    // Логируем или обрабатываем ошибку чтения файла
                    System.err.println("Failed to access file: " + file + " (" + exc.getMessage() + ")");
                    return FileVisitResult.CONTINUE;
                }
            });

            return size[0];
        } catch (IOException e) {
            System.err.println("Error");
        }
        return 0;
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

    public JPanel getFileDetailsPanel() {
        return fileDetailsPanel;
    }
}
