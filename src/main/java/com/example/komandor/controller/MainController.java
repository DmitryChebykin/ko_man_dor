package com.example.komandor.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView(value = "/ui/MainController.fxml")
public class MainController {
    @FXML
    Text header;

    @FXML
    public void initialize() {

    }
}