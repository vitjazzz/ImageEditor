package com.vitja.states;

import com.vitja.State;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by Viktor on 27.09.2016.
 */
public class CutState implements State {
    private ScrollPane scrollPane;

    private ImageView imageView;

    private EventHandler<? super MouseEvent> mouseClickedHandler;

    public CutState(ScrollPane scrollPane, ImageView imageView){
        this.scrollPane = scrollPane;
        this.imageView = imageView;
        initializeEvents();
    }

    @Override
    public void initializeEvents() {
        if(scrollPane != null && imageView != null){
            mouseClickedHandler = event -> {
                if(event.getX() <= imageView.getImage().getWidth() && event.getY() <= imageView.getImage().getHeight()){
                    PixelReader pixelReader = imageView.getImage().getPixelReader();
                    WritableImage writableImage = new WritableImage(pixelReader, 0, 0, Double.valueOf(event.getX()).intValue() , Double.valueOf(event.getY()).intValue());
                    imageView = new ImageView(writableImage);
                }
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
