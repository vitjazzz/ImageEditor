package com.vitja.states;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

/**
 * Created by Viktor on 27.09.2016.
 */
public class RotateState implements State {
    private ScrollPane scrollPane;

    private ImageView imageView;

    public RotateState(ScrollPane scrollPane, ImageView imageView){
        this.scrollPane = scrollPane;
        this.imageView = imageView;
        initializeEvents();
    }

    @Override
    public void initializeEvents() {
        if(scrollPane != null && imageView != null){

        }
    }

    @Override
    public void removeStateEvents(){}

    @Override
    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
