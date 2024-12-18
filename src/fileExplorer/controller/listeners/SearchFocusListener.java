package fileExplorer.controller.listeners;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class SearchFocusListener implements FocusListener {
    private final String placeholderText = "Search...";

    @Override
    public void focusGained(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        if (field.getText().equals(placeholderText)) {
            field.setText("");
            field.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        if (field.getText().isEmpty() || field.getText().equals(placeholderText)) {
            field.setText(placeholderText);
            field.setForeground(Color.GRAY);
        }
    }
}
