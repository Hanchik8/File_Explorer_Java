package fileExplorer.view.viewComponents;

import fileExplorer.model.FileIconProvider;
import fileExplorer.utils.ImageUtils;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.Font;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Панель, отображающая подробности о файле: имя, размер, дата последнего
 * изменения и иконки.
 */
public class FileDetailsPanel {
    private final JPanel fileDetailsPanel;
    private final FileIconProvider fileListRenderer;

    public FileDetailsPanel() {
        fileDetailsPanel = new JPanel();
        fileDetailsPanel.setLayout(new BoxLayout(fileDetailsPanel, BoxLayout.Y_AXIS));
        fileDetailsPanel.setPreferredSize(new Dimension(400, fileDetailsPanel.getHeight()));
        fileListRenderer = new FileIconProvider();
    }

    /**
     * Создаёт метку с иконкой в зависимости от расширения файла.
     * 
     * @param imagePath Путь к изображению для отображения.
     * @param extension Расширение файла.
     */
    private void createImageLabel(String imagePath, String extension) {
        JLabel imageLabel = new JLabel();
        int panelWidth = fileDetailsPanel.getWidth();

        if (extension == null) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/folderIcon.png", panelWidth));
        } else if (extension.equals("xls")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/xlsIcon.png", panelWidth));
        } else if (extension.equals("xlsx")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/xlsxIcon.png", panelWidth));
        } else if (extension.equals("doc")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/docIcon.png", panelWidth));
        } else if (extension.equals("docx")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/docxIcon.png", panelWidth));
        } else if (extension.equals("ppt") || extension.equals("pptx")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/pptIcon.png", panelWidth));
        } else if (extension.equals("txt")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/txtIcon.png", panelWidth));
        } else if (extension.equals("pdf")) {
            imageLabel.setIcon(ImageUtils.getImagePreview("resources/images/fileIcons/pdfIcon.png", panelWidth));
        } else {
            ImageIcon scaledImage = ImageUtils.scaleImage(new ImageIcon(imagePath), panelWidth, 350);
            imageLabel.setIcon(scaledImage);
        }

        fileDetailsPanel.add(imageLabel);
    }

    /**
     * Создаёт метку с именем файла и иконкой.
     * 
     * @param fileName Имя файла.
     */
    private void createNameLabel(String fileName) {
        JLabel nameLabel = new JLabel(fileName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setIcon(fileListRenderer.getIconForFile(fileName));

        fileDetailsPanel.add(nameLabel);
    }

    /**
     * Создаёт панель с дополнительной информацией о файле (путь, размер, дата
     * последнего изменения).
     * 
     * @param currentFile Файл, о котором нужно вывести информацию.
     */
    private void createExtraDetailsPanel(File currentFile) {
        JPanel extraDetailsPanel = new JPanel();
        extraDetailsPanel.setLayout(new BoxLayout(extraDetailsPanel, BoxLayout.Y_AXIS));

        extraDetailsPanel.add(new JLabel("Path: " + currentFile.getParent()));

        if (currentFile.isFile())
            extraDetailsPanel
                    .add(new JLabel("Size: " + (Math.round(currentFile.length() / 1024.0 * 100.0) / 100.0) + " KB"));

        Date lastModifiedDate = new Date(currentFile.lastModified());
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        extraDetailsPanel.add(new JLabel("Last Modified: " + formattedDate.format(lastModifiedDate)));

        fileDetailsPanel.add(extraDetailsPanel);
    }

    /**
     * Обновляет панель с деталями о файле.
     * 
     * @param currentFile Файл, для которого обновляются детали.
     * @param extension   Расширение файла.
     */
    public void updateFileDetailsPanel(File currentFile, String extension) {
        fileDetailsPanel.removeAll();

        createImageLabel(currentFile.getAbsolutePath(), extension);
        createNameLabel(currentFile.getName());
        createExtraDetailsPanel(currentFile);

        fileDetailsPanel.revalidate();
        fileDetailsPanel.repaint();
    }

    public JPanel getFileDetailsPanel() {
        return fileDetailsPanel;
    }
}
