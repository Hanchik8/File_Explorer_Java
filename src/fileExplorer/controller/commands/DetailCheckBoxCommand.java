package fileExplorer.controller.commands;

import fileExplorer.view.MainView;

/**
 * Реализация команды для обработки состояния чекбокса "Details" в панели инструментов.
 * Когда состояние чекбокса изменяется, этот класс управляет отображением или скрытием панели с деталями файла.
 */
public class DetailCheckBoxCommand implements Command {
    private final MainView mainView;

    /**
     * Конструктор для создания команды для обработки состояния чекбокса "Details".
     * @param mainView основной вид, содержащий панель инструментов и другие компоненты интерфейса.
     */
    public DetailCheckBoxCommand(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Выполняет команду, основанную на текущем состоянии чекбокса "Details".
     * Если чекбокс выбран, отображается панель с деталями файла, иначе она скрывается.
     */
    @Override
    public void execute() {
        boolean isSelected = mainView.getToolbarPanel().getDetailsCheckBox().isSelected();
        mainView.showHideFileDetailsPanel(isSelected);
    }
}
