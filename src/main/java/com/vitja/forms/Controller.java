package com.vitja.forms;

import com.vitja.client_server.Client;
import com.vitja.client_server.NetworkConnection;
import com.vitja.client_server.Server;
import com.vitja.controllers.ImageController;
import com.vitja.facade.FacadeImageHelper;
import com.vitja.states.CutState;
import com.vitja.states.ResizeState;
import com.vitja.states.RotateState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 * Created by Viktor on 25.09.2016.
 */
public class Controller implements Initializable{
    @FXML
    private Button chooseBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane mainImagePane;

    @FXML
    private Button resizeBtn;

    @FXML
    private Button rotateBtn;

    @FXML
    private Button cutBtn;

    @FXML
    private Button effectsBtn;

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    private FacadeImageHelper facadeImageHelper;

    private boolean isServer = false;

    private NetworkConnection connection = isServer ? createServer() : createClient();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        textField.setOnAction(event -> {
            String message = isServer ? "Server: " : "Client: ";
            message += textField.getText();
            textField.clear();

            try {
                connection.send(message);
                textArea.appendText(message + "\n");
            } catch (Exception e) {
                textArea.appendText("Failed to send\n");
            }
        });

        facadeImageHelper = new FacadeImageHelper(mainImagePane);

        chooseBtn.setOnAction(event -> facadeImageHelper.chooseImageByFileChooser(getStage(), mainImagePane));

        resizeBtn.setOnAction(event -> facadeImageHelper.doResizeButtonAction(mainImagePane));

        cutBtn.setOnAction(event -> facadeImageHelper.doCutButtonAction(mainImagePane));

        rotateBtn.setOnAction(event -> facadeImageHelper.doCutButtonAction(mainImagePane));

        anchorPane.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode() == KeyCode.Z){
                facadeImageHelper.undoAction();
            }
        });

        anchorPane.setOnMouseClicked(event -> facadeImageHelper.reloadImageOnPane(mainImagePane));
    }

    public void closeConnection() throws Exception {
        connection.closeConnection();
    }

    private Server createServer(){
        return new Server(55555, data -> {
            Platform.runLater(() -> {
                textArea.appendText(data.toString() + "\n");
            });
        });
    }

    private Client createClient(){
        return new Client(55555, "127.0.0.1", data -> {
            Platform.runLater(() -> {
                textArea.appendText(data.toString() + "\n");
            });
        });
    }

    private Stage getStage(){
        return (Stage) anchorPane.getScene().getWindow();
    }


}
