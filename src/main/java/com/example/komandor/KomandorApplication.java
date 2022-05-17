package com.example.komandor;

import com.example.komandor.application.FxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KomandorApplication {

    public static void main(String[] args) {
        Application.launch(FxApplication.class, args);
    }
}
