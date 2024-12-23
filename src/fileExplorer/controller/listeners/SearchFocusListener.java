package fileExplorer.controller.listeners;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Слушатель для обработки фокуса на поле поиска.
 * При получении фокуса текстовое поле очищается, если в нем отображается текст-заполнитель.
 * При потере фокуса, если поле пустое, восстанавливается текст-заполнитель.
 */
public class SearchFocusListener implements FocusListener {
    private final String placeholderText = "Search...";

    /**
     * Метод, вызываемый при получении фокуса на поле.
     * Если в поле находится текст-заполнитель, то он удаляется, а текстовое поле становится черным.
     * @param e событие получения фокуса.
     */
    @Override
    public void focusGained(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        if (field.getText().equals(placeholderText)) {
            field.setText("");
            field.setForeground(Color.BLACK);
        }
    }

    /**
     * Метод, вызываемый при потере фокуса полем.
     * Если поле пустое, восстанавливается текст-заполнитель с серым цветом.
     * @param e событие потери фокуса.
     */
    @Override
    public void focusLost(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        if (field.getText().isEmpty() || field.getText().equals(placeholderText)) {
            field.setText(placeholderText);
            field.setForeground(Color.GRAY);
        }
    }
}
