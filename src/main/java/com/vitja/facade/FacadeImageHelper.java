package com.vitja.facade;

import com.vitja.client_server.Client;
import com.vitja.client_server.NetworkConnection;
import com.vitja.client_server.Server;
import com.vitja.composite.CompositeCommand;
import com.vitja.controllers.ImageController;
import com.vitja.forms.LoggingWindowController;
import com.vitja.states.CutState;
import com.vitja.states.ResizeState;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Viktor on 27.10.2016.
 */
public class FacadeImageHelper {
    final static Logger logger = Logger.getLogger(FacadeImageHelper.class);

    private ImageController imageController;

    private ImageView imageView;

    private Stack<CompositeCommand> commands;

    private ScrollPane scrollPane;

    private Stage loggingWindow;
    private LoggingWindowController loggingController;

    private boolean isServer = false;
    private NetworkConnection connection = isServer ? createServer() : createClient();

    public FacadeImageHelper(ScrollPane scrollPane) {
        this.imageController = new ImageController();
        this.commands = new Stack<>();
        this.scrollPane = scrollPane;

        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws Exception {
        connection.closeConnection();
    }

    private Server createServer(){
        return new Server(55555, data -> {
            Platform.runLater(() -> {
                loggingController.appendLog(data.toString());
            });
        });
    }

    private Client createClient(){
        return new Client(55555, "127.0.0.1", data -> {
            Platform.runLater(() -> {
                loggingController.appendLog(data.toString());
            });
        });
    }

    public void logAction(String message){
        try {
            if(isServer) {
                connection.send("Server: " + message);
            } else{
                connection.send("Client: " + message);
            }

            if(isServer) {
                loggingController.appendLog("Server: " + message);
            } else{
                loggingController.appendLog("Client: " + message);
            }
        } catch (Exception e) {
            loggingController.appendLog("Something went wrong!");
        }
    }

    public void chooseImageByFileChooser(Stage stage, ScrollPane scrollPane) {
        try {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);

            imageController.setImageFile(fileChooser.showOpenDialog(stage));

            addImageToPane(scrollPane);

            logAction(new Date() + "  Choosed image " + imageController.getImageFile().getName());
        } catch (Exception ex) {
            showErrorMessege(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void doResizeButtonAction(ScrollPane scrollPane) {
        if (imageController.getCurrentState() != null) {
            imageController.getCurrentState().removeStateEvents();
        }

        CompositeCommand resizeButtonCommand = new CompositeCommand("DoResize");

        imageController.setCurrentState(new ResizeState(scrollPane, imageView, resizeButtonCommand, this));

        commands.push(resizeButtonCommand);
    }

    public void doCutButtonAction(ScrollPane scrollPane) {
        if (imageController.getCurrentState() != null) {
            imageController.getCurrentState().removeStateEvents();
        }

        CompositeCommand cutButtonCommand = new CompositeCommand("DoCut");

        imageController.setCurrentState(new CutState(scrollPane, imageView, cutButtonCommand, this));

        commands.push(cutButtonCommand);
    }

    public void openLoggingWindow(){
        loggingWindow.show();
    }

    public void createLoggingWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loggingWindow.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Logging window");
        stage.setScene(new Scene(root, 800, 940));

        this.loggingWindow = stage;
        this.loggingController = loader.getController();
    }

    public void undoAction() {
        if (commands.isEmpty()) {
            return;
        }

        Stack<CompositeCommand> editCommands = commands.peek().getEditCommands();

        if (editCommands.isEmpty()) {
            commands.pop();
            undoAction();
            return;
        }

        imageView = editCommands.pop().getCommand().getInitialImageView();
        reloadImageOnPane();

        logger.info("Ctrl + Z pressed.");
        //imageController.reverseAction();
    }

    private void reloadImageOnPane() {
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        scrollPane.setContent(pane);
    }

    public void reloadImageOnPane(ScrollPane scrollPane) {
        if (imageController.getCurrentState() != null && imageController.getCurrentState().getImageView() != null) {
            imageView = imageController.getCurrentState().getImageView();

            Pane pane = new Pane();
            pane.getChildren().add(imageView);
            scrollPane.setContent(pane);
        }
    }

    public ImageController getImageController() {
        return imageController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        reloadImageOnPane();
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Open Resource File");

        if (imageController.getImageFile() != null) {
            String imagePath = imageController.getImageFile().getPath();
            String initialDirectory = imagePath.substring(0, imagePath.lastIndexOf(imageController.getImageFile().getName()));
            logger.info(initialDirectory);
            fileChooser.setInitialDirectory(new File(initialDirectory));
        }
    }

    private void addImageToPane(ScrollPane scrollPane) {
        imageView = new ImageView();
        imageView.setImage(imageController.getImage());

        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        scrollPane.setContent(pane);
    }

    private void showErrorMessege(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
