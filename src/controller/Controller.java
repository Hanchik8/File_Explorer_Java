package src.controller;

import src.model.Model;
import src.view.View;

public class Controller {
    public Controller(View view) {
        new Model(view);
    }
}
