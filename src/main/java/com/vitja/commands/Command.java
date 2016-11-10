package com.vitja.commands;

import javafx.scene.image.ImageView;

/**
 * Created by Viktor on 06.10.2016.
 */
public interface Command {
    ImageView execute();

    ImageView getInitialImageView();
}
