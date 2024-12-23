package fileExplorer.utils.iconRenderers;

import fileExplorer.utils.iconProviders.SidebarIconProvider;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.Icon;

import java.awt.Component;

/**
 * Класс рендерера для отображения элементов панели бокового меню с иконками.
 * Использует {@link SidebarIconProvider} для получения иконок для категорий и отображает их в списке.
 */
public class SidebarListRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;
    private transient final SidebarIconProvider sidebarIconProvider;

    /**
     * Конструктор класса. Инициализирует рендерер с указанным поставщиком иконок для бокового меню.
     * @param iconProvider поставщик иконок для бокового меню, используемый для получения иконок.
     */
    public SidebarListRenderer(SidebarIconProvider iconProvider) {
        this.sidebarIconProvider = iconProvider;
    }

    /**
     * Переопределённый метод, который устанавливает иконку для каждого элемента списка.
     * В случае, если элемент не является пустым, извлекается его название и используется {@link SidebarIconProvider}
     * для получения соответствующей иконки, которая затем отображается в списке.
     * @param list список категорий бокового меню
     * @param value объект, представляющий элемент списка (например, название категории)
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
            String itemName = value.toString();
            Icon icon = sidebarIconProvider.getIconForCategory(itemName);
            label.setIcon(icon);
        } else {
            label.setIcon(null);
        }

        return label;
    }
}
