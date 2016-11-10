package com.vitja.states;

import com.vitja.composite.CompositeCommand;
import com.vitja.controllers.ImageController;
import com.vitja.commands.ResizeImageCommand;
import com.vitja.memento.CommandOriginator;
import com.vitja.memento.Memento;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Stack;

/**
 * Created by Viktor on 27.09.2016.
 */
public class ResizeState implements State {
    private ScrollPane scrollPane;

    private ImageView imageView;

    private EventHandler<? super MouseEvent> mouseMovedHandler;

    private CompositeCommand compositeCommand;

    public ResizeState(ScrollPane scrollPane, ImageView imageView, CompositeCommand compositeCommand){
        mouseMovedHandler = event -> {};
        this.scrollPane = scrollPane;
        this.imageView = imageView;
        this.compositeCommand = compositeCommand;
        initializeEvents();
    }

    @Override
    public void initializeEvents() {
        if(scrollPane != null && imageView != null){
            mouseMovedHandler = event -> {
                Stack<CompositeCommand> commands = compositeCommand.getEditCommands();
                /*if(commands.isEmpty()){
                    MouseEvent tempMouseEvent = new MouseEvent(null, imageView.getImage().getWidth(), imageView.getImage().getHeight(), 0, 0, null, 1, true, true, true, true, true, true, true, true, true, true, null);
                    commands.add(new CompositeCommand(new ResizeImageCommand(imageView, tempMouseEvent)));
                }*/

                commands.add(new CompositeCommand(new ResizeImageCommand(imageView, event)));

                ResizeImageCommand resizeImageCommand = new ResizeImageCommand(imageView, event);
                imageView = resizeImageCommand.execute();


            };

            scrollPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseMovedHandler);
        }
    }

    @Override
    public void removeStateEvents(){
        if(mouseMovedHandler != null){
            scrollPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseMovedHandler);
        }
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
