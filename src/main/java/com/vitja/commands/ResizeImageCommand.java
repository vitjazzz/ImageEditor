package com.vitja.commands;

import com.vitja.controllers.ImageController;
import com.vitja.facade.FacadeImageHelper;
import com.vitja.memento.CommandOriginator;
import com.vitja.states.ResizeState;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by Viktor on 06.10.2016.
 */
public class ResizeImageCommand implements Command {
    private static Logger logger = Logger.getLogger(ResizeImageCommand.class);

    private ImageView imageView;

    private MouseEvent mouseEvent;

    private FacadeImageHelper facadeImageHelper;

    public ResizeImageCommand(ImageView imageView, MouseEvent mouseEvent, FacadeImageHelper facadeImageHelper) {
        this.imageView = imageView;
        this.mouseEvent = mouseEvent;
        this.facadeImageHelper = facadeImageHelper;
    }

    @Override
    public ImageView execute() {
        ImageView imageView1 = new ImageView(imageView.getImage());
        //ResizeState.resizeImage(mouseEvent, imageView);

        imageView1.setFitHeight(mouseEvent.getY());
        imageView1.setFitWidth(mouseEvent.getX());

        facadeImageHelper.logAction(new Date() + "  Changed size to: X - " + mouseEvent.getX() + ", Y - " + mouseEvent.getY());

        return imageView1;
        //imageView.setImage(imageView.snapshot(null, null));
    }

    @Override
    public ImageView getInitialImageView() {
        facadeImageHelper.logAction(new Date() + "  Changed size to: X - " + imageView.getFitWidth() + ", Y - " + imageView.getFitHeight());
        return imageView;
    }
}
