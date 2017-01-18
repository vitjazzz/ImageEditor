package com.vitja.commands;

import com.vitja.facade.FacadeImageHelper;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.util.Date;

/**
 * Created by Viktor on 06.10.2016.
 */
public class CutImageCommand implements Command {
    private ImageView imageView;

    private MouseEvent mouseEvent;

    private FacadeImageHelper facadeImageHelper;

    public CutImageCommand(ImageView imageView, MouseEvent mouseEvent, FacadeImageHelper facadeImageHelper) {
        this.imageView = imageView;
        this.mouseEvent = mouseEvent;
        this.facadeImageHelper = facadeImageHelper;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public ImageView execute() {
        if(mouseEvent.getX() <= imageView.getImage().getWidth() && mouseEvent.getY() <= imageView.getImage().getHeight()){
            PixelReader pixelReader = imageView.getImage().getPixelReader();
            WritableImage writableImage = new WritableImage(pixelReader, 0, 0,
                    Double.valueOf(mouseEvent.getX()).intValue(),
                    Double.valueOf(mouseEvent.getY()).intValue());


            facadeImageHelper.logAction(new Date() + "  Image was cut to size: X - " + mouseEvent.getX() + ", Y - " + mouseEvent.getY());
            return new ImageView(writableImage);
        }

        return imageView;
    }

    @Override
    public ImageView getInitialImageView() {
        facadeImageHelper.logAction(new Date() + "  Image was cut to size: X - " + imageView.getFitWidth() + ", Y - " + imageView.getFitHeight());
        return imageView;
    }
}
