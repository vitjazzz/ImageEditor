package com.vitja.commands;

import com.vitja.controllers.ImageController;
import com.vitja.memento.CommandOriginator;
import com.vitja.states.ResizeState;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

/**
 * Created by Viktor on 06.10.2016.
 */
public class ResizeImageCommand implements Command {
    private static Logger logger = Logger.getLogger(ResizeImageCommand.class);

    private ImageView imageView;

    private MouseEvent mouseEvent;

    public ResizeImageCommand(ImageView imageView, MouseEvent mouseEvent) {
        this.imageView = imageView;
        this.mouseEvent = mouseEvent;
    }

    @Override
    public ImageView execute() {
        logger.info("Resized image.");

        ImageView imageView1 = new ImageView(imageView.getImage());
        //ResizeState.resizeImage(mouseEvent, imageView);

        imageView1.setFitHeight(mouseEvent.getY());
        imageView1.setFitWidth(mouseEvent.getX());

        return imageView1;
        //imageView.setImage(imageView.snapshot(null, null));
    }

    @Override
    public ImageView getInitialImageView() {
        return imageView;
    }
}
