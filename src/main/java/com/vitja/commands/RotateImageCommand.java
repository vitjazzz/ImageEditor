package com.vitja.commands;

import javafx.scene.image.ImageView;

/**
 * Created by Viktor on 06.10.2016.
 */
public class RotateImageCommand implements Command {
    private ImageView imageView;

    public RotateImageCommand(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public ImageView execute() {
        return new ImageView();
    }

    @Override
    public ImageView getInitialImageView() {
        return null;
    }
}
