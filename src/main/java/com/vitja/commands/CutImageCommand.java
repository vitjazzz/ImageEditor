package com.vitja.commands;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

/**
 * Created by Viktor on 06.10.2016.
 */
public class CutImageCommand implements Command {
    private ImageView imageView;

    private MouseEvent mouseEvent;

    public CutImageCommand(ImageView imageView, MouseEvent mouseEvent) {
        this.imageView = imageView;
        this.mouseEvent = mouseEvent;
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

            return new ImageView(writableImage);
        }

        return imageView;
    }

    @Override
    public ImageView getInitialImageView() {
        return imageView;
    }
}
