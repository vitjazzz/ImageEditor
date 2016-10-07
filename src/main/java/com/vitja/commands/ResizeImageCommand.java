package com.vitja.commands;

import com.vitja.ImageController;
import com.vitja.states.ResizeState;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

/**
 * Created by Viktor on 06.10.2016.
 */
public class ResizeImageCommand implements Command {
    private static Logger logger = Logger.getLogger(ResizeImageCommand.class);

    private ImageView imageView;

    private MouseEvent mouseEvent;

    public ResizeImageCommand(ImageView imageView, MouseEvent mouseEvent) {
        this.imageView = imageView;
        this.mouseEvent = mouseEvent;
    }

    @Override
    public void execute() {
        logger.info("Resized image.");
        if(ImageController.commandStack.empty() || ImageController.commandStack.peek().getClass() != this.getClass()){
            MouseEvent tempMouseEvent = new MouseEvent(null, imageView.getImage().getWidth(), imageView.getImage().getHeight(), 0, 0, null, 1, true, true, true, true, true, true, true, true, true, true, null);
            ImageController.commandStack.push(new ResizeImageCommand(imageView, tempMouseEvent));
            logger.info(imageView.getFitWidth() + "   " + imageView.getFitHeight());
        }
        ResizeState.resizeImage(mouseEvent, imageView);
        //imageView.setImage(imageView.snapshot(null, null));
    }
}
