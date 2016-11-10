package com.vitja.facade;

import com.vitja.composite.CompositeCommand;
import com.vitja.controllers.ImageController;
import com.vitja.states.CutState;
import com.vitja.states.ResizeState;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
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

    public FacadeImageHelper() {
        this.imageController = new ImageController();
        this.commands = new Stack<>();
    }

    public FacadeImageHelper(ScrollPane scrollPane) {
        this.imageController = new ImageController();
        this.commands = new Stack<>();
        this.scrollPane = scrollPane;
    }

    public void chooseImageByFileChooser(Stage stage, ScrollPane scrollPane) {
        try {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);

            imageController.setImageFile(fileChooser.showOpenDialog(stage));

            addImageToPane(scrollPane);
        } catch (Exception ex) {
            showErrorMessege(ex.getMessage());
        }
    }

    public void doResizeButtonAction(ScrollPane scrollPane) {
        if (imageController.getCurrentState() != null) {
            imageController.getCurrentState().removeStateEvents();
        }

        CompositeCommand resizeButtonCommand = new CompositeCommand("DoResize");

        imageController.setCurrentState(new ResizeState(scrollPane, imageView, resizeButtonCommand));

        commands.add(resizeButtonCommand);
    }

    public void doCutButtonAction(ScrollPane scrollPane) {
        if (imageController.getCurrentState() != null) {
            imageController.getCurrentState().removeStateEvents();
        }

        CompositeCommand cutButtonCommand = new CompositeCommand("DoCut");

        imageController.setCurrentState(new CutState(scrollPane, imageView, cutButtonCommand));

        commands.add(cutButtonCommand);
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
