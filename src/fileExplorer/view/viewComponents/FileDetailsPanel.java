package fileExplorer.view.viewComponents;

import fileExplorer.utils.iconProviders.FileIconProvider;
import fileExplorer.utils.ImageUtils;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import java.awt.Font;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDetailsPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private transient final FileIconProvider fileListRenderer;

    public FileDetailsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        fileListRenderer = new FileIconProvider();
    }

    private void createImageLabel(String imagePath, File currentFile, String extension) {
        JLabel imageLabel = new JLabel();
        int panelWidth = getWidth();

        if (extension != null && (extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg"))) {
            ImageIcon scaledImage = ImageUtils.scaleImage(new ImageIcon(imagePath), panelWidth, 350);
            imageLabel.setIcon(scaledImage);
        } else {
            imageLabel.setIcon(fileListRenderer.getIconForFile(currentFile.getName(), panelWidth));
        }

        add(imageLabel);
    }

    private void createNameLabel(File currentFile) {
        String fileName = currentFile.getName();
        if (fileName.isEmpty())
            fileName = currentFile.getAbsolutePath();

        JLabel nameLabel = new JLabel(fileName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setIcon(fileListRenderer.getIconForFile(fileName));

        add(nameLabel);
    }

    private void createExtraDetailsPanel(File currentFile) {
        JPanel extraDetailsPanel = new JPanel();
        extraDetailsPanel.setLayout(new BoxLayout(extraDetailsPanel, BoxLayout.Y_AXIS));

        if (!(currentFile.getParent() == null))
            extraDetailsPanel.add(new JLabel("Path: " + currentFile.getParent()));

        if (currentFile.isFile())
            extraDetailsPanel
                    .add(new JLabel("Size: " + (Math.round(currentFile.length() / 1024.0 * 100.0) / 100.0) + " KB"));

        Date lastModifiedDate = new Date(currentFile.lastModified());
        SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        extraDetailsPanel.add(new JLabel("Last Modified: " + formattedDate.format(lastModifiedDate)));

        add(extraDetailsPanel);
    }

    public void updateFileDetailsPanel(File currentFile, String extension) {
        removeAll();

        createImageLabel(currentFile.getAbsolutePath(), currentFile, extension);
        createNameLabel(currentFile);
        createExtraDetailsPanel(currentFile);

        revalidate();
        repaint();
    }
}
