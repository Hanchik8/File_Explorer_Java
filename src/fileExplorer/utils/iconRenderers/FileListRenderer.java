package fileExplorer.utils.iconRenderers;

import fileExplorer.utils.iconProviders.FileIconProvider;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.Icon;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;

/**
 * Класс, отвечающий за рендеринг элементов списка файлов с иконками.
 * Использует {@link FileIconProvider} для получения иконок для файлов и отображает их в списке.
 */
public class FileListRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;
    private transient final FileIconProvider fileIconProvider;

    /**
     * Конструктор класса. Инициализирует рендерер с указанным поставщиком иконок.
     * @param iconProvider поставщик иконок для файлов, используемый для получения иконок.
     */
    public FileListRenderer(FileIconProvider iconProvider) {
        this.fileIconProvider = iconProvider;
    }

    /**
     * Переопределённый метод, который устанавливает иконку для каждого элемента списка.
     * В случае, если элемент не является пустым, извлекается его имя и используется {@link FileIconProvider}
     * для получения соответствующей иконки, которая затем отображается в списке.
     * @param list список файлов
     * @param value объект, представляющий элемент списка (например, имя файла)
     * @param index индекс элемента
     * @param isSelected флаг, указывающий, выбран ли элемент
     * @param cellHasFocus флаг, указывающий, имеет ли элемент фокус
     * @return компонент, который будет отображаться в списке (с иконкой, если она есть)
     */
    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null) {
            String filename = value.toString();
            Icon icon = fileIconProvider.getIconForFile(filename);
            label.setIcon(icon);
        } else {
            label.setIcon(null);
        }
        return label;
    }
}
