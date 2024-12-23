package fileExplorer.view;

import fileExplorer.controller.MainController;

import fileExplorer.view.viewComponents.CenterPanel;
import fileExplorer.view.viewComponents.ToolbarPanel;
import fileExplorer.view.viewComponents.FileDetailsPanel;
import fileExplorer.view.viewComponents.SidebarPanel;
import fileExplorer.view.viewComponents.NavigationPanel;
import fileExplorer.view.viewComponents.PopupToolbarPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;

import java.io.File;
import java.util.HashMap;

/**
 * Основное окно файлового менеджера, которое управляет всеми панелями и их взаимодействием.
 * Оно отвечает за отображение всех компонентов, таких как панели навигации, инструментов,
 * центра файлов, боковой панели и панели с деталями файлов.
 */
public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    private HashMap<String, JComponent> viewPanels = new HashMap<>();
    private JSplitPane splitPane1;
    private JSplitPane splitPane2;

    /**
     * Конструктор класса. Инициализирует основные компоненты интерфейса и подключает контроллер.
     */
    public MainView() {
        setTitle("File Explorer Lunar Seekers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        add(setupExplorerView());

        new MainController(this);
    }

    /**
     * Настройка основного представления файлового менеджера.
     * Создаются и добавляются все основные панели (панель навигации, панель инструментов,
     * центральная панель, боковая панель, панель деталей файлов).
     * @return панель с полным представлением файлового менеджера
     */
    public JPanel setupExplorerView() {
        JPanel topPanelPart = new JPanel(new BorderLayout());
        NavigationPanel navigationPanel = new NavigationPanel();
        topPanelPart.add(navigationPanel, BorderLayout.NORTH);
        viewPanels.put("navigation", navigationPanel);

        ToolbarPanel toolbarPanel = new ToolbarPanel();
        topPanelPart.add(toolbarPanel, BorderLayout.SOUTH);
        viewPanels.put("toolbar", toolbarPanel);

        JPanel explorerPanel = new JPanel(new BorderLayout());

        explorerPanel.add(topPanelPart, BorderLayout.NORTH);
        CenterPanel centerPanel = new CenterPanel();
        viewPanels.put("center", centerPanel);
        SidebarPanel sidebarPanel = new SidebarPanel();
        viewPanels.put("sidebar", sidebarPanel);
        FileDetailsPanel fileDetailsPanel = new FileDetailsPanel();
        viewPanels.put("fileDetails", fileDetailsPanel);

        getToolbarPanel();

        splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(sidebarPanel),
                new JScrollPane(centerPanel));

        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitPane1, new JScrollPane(fileDetailsPanel));

        explorerPanel.add(splitPane2);

        JPopupMenu popupToolbarPanel = new PopupToolbarPanel();
        viewPanels.put("popupToolbar", popupToolbarPanel);

        return explorerPanel;
    }

    /**
     * Обновление списка файлов и пути в панели навигации и центре.
     * Обновляются также кнопки в панели инструментов.
     * @param fileNames  список имен файлов
     * @param currentPath текущий путь
     */
    public void updateView(String[] fileNames, String currentPath) {
        CenterPanel centerPanel = (CenterPanel) viewPanels.get("center");
        centerPanel.updateFileListModel(fileNames);

        NavigationPanel navigationPanel = (NavigationPanel) viewPanels.get("navigation");
        navigationPanel.setCurrentPath(currentPath);
        updateBtnState(false);
    }

    /**
     * Обновление состояния кнопок на панели инструментов.
     * @param isBtnActive флаг, указывающий, активны ли кнопки
     */
    public void updateBtnState(boolean isBtnActive) {
        ToolbarPanel toolbarPanel = (ToolbarPanel) viewPanels.get("toolbar");
        toolbarPanel.getCutBtn().setEnabled(isBtnActive);
        toolbarPanel.getCopyBtn().setEnabled(isBtnActive);
        toolbarPanel.getDeleteBtn().setEnabled(isBtnActive);
        toolbarPanel.getRenameBtn().setEnabled(isBtnActive);

        PopupToolbarPanel popupToolbarPanel = (PopupToolbarPanel) viewPanels.get("popupToolbar");
        popupToolbarPanel.updateJItemState(isBtnActive);
    }

    /**
     * Обновление панели с деталями файла.
     * @param selectedFile выбранный файл
     * @param fileExtension расширение файла
     */
    public void updateFileDetails(File selectedFile, String fileExtension) {
        FileDetailsPanel fileDetailsPanel = (FileDetailsPanel) viewPanels.get("fileDetails");
        fileDetailsPanel.updateFileDetailsPanel(selectedFile, fileExtension);
    }

    /**
     * Отображение или скрытие панели с деталями файла.
     * @param showDetails флаг, указывающий, нужно ли показывать панель с деталями
     */
    public void showHideFileDetailsPanel(boolean showDetails) {
        if (showDetails) {
            FileDetailsPanel fileDetailsPanel = (FileDetailsPanel) viewPanels.get("fileDetails");
            splitPane2.setRightComponent(new JScrollPane(fileDetailsPanel));
            updateComponentSize();
        } else {
            splitPane2.setRightComponent(null);
        }
        splitPane2.revalidate();
        splitPane2.repaint();
    }

    /**
     * Обновление размера компонентов после изменения размеров окна.
     */
    public void updateComponentSize() {
        splitPane1.setDividerLocation(getWidth() / 5);
        splitPane2.setDividerLocation(getWidth() / 4 * 3);
    }

    /**
     * Получение панели навигации.
     * @return панель навигации
     */
    public NavigationPanel getNavigationPanel() {
        return (NavigationPanel) viewPanels.get("navigation");
    }

    /**
     * Получение центральной панели.
     * @return центральная панель
     */
    public CenterPanel getCenterPanel() {
        return (CenterPanel) viewPanels.get("center");
    }

    /**
     * Получение панели инструментов.
     * @return панель инструментов
     */
    public ToolbarPanel getToolbarPanel() {
        return (ToolbarPanel) viewPanels.get("toolbar");
    }

    /**
     * Получение боковой панели.
     * @return боковая панель
     */
    public SidebarPanel getSidebarPanel() {
        return (SidebarPanel) viewPanels.get("sidebar");
    }

    /**
     * Получение панели с контекстным меню.
     * @return панель с контекстным меню
     */
    public PopupToolbarPanel getPopupToolbarPanel() { return (PopupToolbarPanel) viewPanels.get("popupToolbar");};
}
