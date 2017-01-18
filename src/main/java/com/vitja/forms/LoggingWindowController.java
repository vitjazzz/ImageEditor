package com.vitja.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Viktor on 10.11.2016.
 */
public class LoggingWindowController implements Initializable {
    @FXML
    private TextArea logsArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void appendLog(String message){
        logsArea.appendText(message + "\n");
    }
}
