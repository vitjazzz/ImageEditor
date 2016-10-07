package com.vitja.forms;

import com.vitja.ImageController;
import com.vitja.states.CutState;
import com.vitja.states.ResizeState;
import com.vitja.states.RotateState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
    final static Logger logger = Logger.getLogger(Controller.class);

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

    private Stage primaryStage;

    private ImageController imageController;

    private ImageView imageView;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        assert chooseBtn != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";

        assert anchorPane != null : "anchor pane was not injected";

        imageController = new ImageController();

        chooseBtn.setOnAction(event -> chooseImageByFileChooser());

        resizeBtn.setOnAction(event -> {
            if(imageController.getCurrentState() != null){
                imageController.getCurrentState().removeStateEvents();
            }
            imageController.setCurrentState(new ResizeState(mainImagePane, imageView));
        });

        cutBtn.setOnAction(event -> {
            if(imageController.getCurrentState() != null){
                imageController.getCurrentState().removeStateEvents();
            }
            imageController.setCurrentState(new CutState(mainImagePane, imageView));
        });

        rotateBtn.setOnAction(event -> {
            if(imageController.getCurrentState() != null){
                imageController.getCurrentState().removeStateEvents();
            }
            imageController.setCurrentState(new RotateState(mainImagePane, imageView));
        });

        anchorPane.setOnKeyPressed(event -> {
            if(event.isControlDown() && event.getCode() == KeyCode.Z){
                logger.info("Ctrl + Z pressed.");
                imageController.reverseAction();
            }
        });

        anchorPane.setOnMouseClicked(event -> {
            if(imageController.getCurrentState()!= null && imageController.getCurrentState().getImageView() != null){
                imageView = imageController.getCurrentState().getImageView();

                Pane pane = new Pane();
                pane.getChildren().add(imageView);
                mainImagePane.setContent(pane);
            }
        });
    }

    private void showErrorMessege(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void chooseImageByFileChooser(){
        try {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);

            Stage stage = getStage();

            imageController.setImageFile(fileChooser.showOpenDialog(stage));

            addImageToPane();
        } catch (Exception ex){
            showErrorMessege(ex.getMessage());
        }
    }

    private void addImageToPane(){
        imageView = new ImageView();
        imageView.setImage(imageController.getImage());

        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        mainImagePane.setContent(pane);
    }

    private Stage getStage(){
        return (Stage) anchorPane.getScene().getWindow();
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Open Resource File");

        if(imageController.getImageFile() != null){
            String imagePath = imageController.getImageFile().getPath();
            String initialDirectory = imagePath.substring(0, imagePath.lastIndexOf(imageController.getImageFile().getName()));
            logger.info(initialDirectory);
            fileChooser.setInitialDirectory(new File(initialDirectory));
        }
    }
}
