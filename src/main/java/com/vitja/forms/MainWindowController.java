package com.vitja.forms;

import com.vitja.facade.FacadeImageHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Viktor on 25.09.2016.
 */
public class MainWindowController implements Initializable{
    @FXML
    private Button chooseBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane mainImagePane;

    @FXML
    private Button resizeBtn;

    @FXML
    private Button cutBtn;

    @FXML
    private Button showLogsBtn;

    private FacadeImageHelper facadeImageHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        /*textField.setOnAction(event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += textField.getText();
            textField.clear();

            try {
                connection.send(message);
                textArea.appendText(message + "\n");
            } catch (Exception e) {
                textArea.appendText("Failed to send\n");
            }
        });*/

        facadeImageHelper = new FacadeImageHelper(mainImagePane);

        facadeImageHelper.createLoggingWindow();

        chooseBtn.setOnAction(event -> facadeImageHelper.chooseImageByFileChooser(getStage(), mainImagePane));

        showLogsBtn.setOnAction(event -> facadeImageHelper.openLoggingWindow());

        resizeBtn.setOnAction(event -> facadeImageHelper.doResizeButtonAction(mainImagePane));

        cutBtn.setOnAction(event -> facadeImageHelper.doCutButtonAction(mainImagePane));

        anchorPane.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode() == KeyCode.Z){
                facadeImageHelper.undoAction();
            }
        });

        anchorPane.setOnMouseClicked(event -> facadeImageHelper.reloadImageOnPane(mainImagePane));
    }

    public FacadeImageHelper getFacadeImageHelper() {
        return facadeImageHelper;
    }

    private Stage getStage(){
        return (Stage) anchorPane.getScene().getWindow();
    }


}
