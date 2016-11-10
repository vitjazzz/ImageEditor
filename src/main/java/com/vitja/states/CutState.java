package com.vitja.states;

import com.vitja.commands.CutImageCommand;
import com.vitja.commands.ResizeImageCommand;
import com.vitja.composite.CompositeCommand;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

import java.util.Stack;

/**
 * Created by Viktor on 27.09.2016.
 */
public class CutState implements State {
    private ScrollPane scrollPane;

    private ImageView imageView;

    private EventHandler<? super MouseEvent> mouseClickedHandler;

    private CompositeCommand compositeCommand;

    public CutState(ScrollPane scrollPane, ImageView imageView, CompositeCommand compositeCommand){
        mouseClickedHandler = event -> {};
        this.scrollPane = scrollPane;
        this.imageView = imageView;
        this.compositeCommand = compositeCommand;
        initializeEvents();
    }

    @Override
    public void initializeEvents() {
        if(scrollPane != null && imageView != null){
            mouseClickedHandler = event -> {
                // coeficient = imageView.width / image.width
                // cutWidth(0, event.getX() / coeficient

                Stack<CompositeCommand> commands = compositeCommand.getEditCommands();

                commands.add(new CompositeCommand(new CutImageCommand(imageView, event)));

                /*if(commands.isEmpty()){
                    MouseEvent tempMouseEvent = new MouseEvent(null, imageView.getImage().getWidth(),
                            imageView.getImage().getHeight(),
                            0, 0, null, 1, true, true, true, true, true, true, true, true, true, true, null);
                    commands.add(new CompositeCommand(new CutImageCommand(imageView, tempMouseEvent)));
                }*/

                CutImageCommand cutImageCommand = new CutImageCommand(imageView, event);
                imageView = cutImageCommand.execute();

            };

            scrollPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedHandler);
        }
    }

    @Override
    public void removeStateEvents(){
        scrollPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedHandler);
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
