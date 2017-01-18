package com.vitja.forms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Viktor on 25.09.2016.
 */
public class MainForm extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private MainWindowController mainWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        Parent root = loader.load();
        mainWindowController = loader.getController();

        primaryStage.setTitle("Image Editor");
        primaryStage.setScene(new Scene(root, 1224, 940));
        primaryStage.show();

        primaryStage.getIcons().add(new Image("file:src/main/resources/images/ImageEditorIcon.png"));
        //primaryStage.getIcons().add(new Image("file:/D:/Study/ImageEditorProject/src/main/resources/images/ImageEditorIcon.png"));

    }

    @Override
    public void stop() throws Exception {
        if(mainWindowController != null){
            mainWindowController.getFacadeImageHelper().closeConnection();
        }
    }
}
