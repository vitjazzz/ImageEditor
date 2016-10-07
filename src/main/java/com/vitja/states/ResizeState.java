package com.vitja.states;

import com.vitja.ImageController;
import com.vitja.State;
import com.vitja.commands.ResizeImageCommand;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by Viktor on 27.09.2016.
 */
public class ResizeState implements State {
    private ScrollPane scrollPane;

    private ImageView imageView;

    private EventHandler<? super MouseEvent> mouseMovedHandler;

    public ResizeState(ScrollPane scrollPane, ImageView imageView){
        this.scrollPane = scrollPane;
        this.imageView = imageView;
        initializeEvents();
    }

    @Override
    public void initializeEvents() {
        if(scrollPane != null && imageView != null){
            mouseMovedHandler = event -> {
                ResizeImageCommand resizeImageCommand = new ResizeImageCommand(imageView, event);
                resizeImageCommand.execute();
                ImageController.commandStack.push(resizeImageCommand);
            };

            scrollPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseMovedHandler);
        }
    }

    @Override
    public void removeStateEvents(){
        scrollPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseMovedHandler);
    }

    public static void resizeImage(MouseEvent event, ImageView imageView){
        imageView.setFitHeight(event.getY());
        imageView.setFitWidth(event.getX());
    }

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
