package com.vitja.commands;

import javafx.scene.image.ImageView;

/**
 * Created by Viktor on 06.10.2016.
 */
public class CutImageCommand implements Command {
    private ImageView imageView;

    public CutImageCommand(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void execute() {

    }
}
