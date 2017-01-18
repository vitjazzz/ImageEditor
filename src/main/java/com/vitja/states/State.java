package com.vitja.states;

import com.vitja.forms.MainWindowController;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;

/**
 * Created by Viktor on 22.09.2016.
 */
public interface State{
    final static Logger logger = Logger.getLogger(MainWindowController.class);

    void initializeEvents();

    void setScrollPane(ScrollPane scrollPane);

    ScrollPane getScrollPane();

    ImageView getImageView();

    void setImageView(ImageView imageView);

    void removeStateEvents();
}
