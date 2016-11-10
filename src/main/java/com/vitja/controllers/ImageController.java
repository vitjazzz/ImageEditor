package com.vitja.controllers;

import com.vitja.states.State;
import com.vitja.commands.Command;
import com.vitja.memento.CareTaker;
import com.vitja.memento.CommandOriginator;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Viktor on 26.09.2016.
 */
public class ImageController {
    private static final ArrayList<String> availableFormats = new ArrayList<>(Arrays.asList("jpg", "gif", "png", "bmp", "wbmp"));

    public static ArrayList<String> getAvailableFormats() {
        return (ArrayList) availableFormats.clone();
    }

    public ImageController() {
    }

    private State currentState;

    private CommandOriginator commandOriginator = new CommandOriginator();

    private File imageFile;

    private Image image;

    public boolean checkIfImage(File file) {
        if (file.isDirectory()) return false;

        String fileFormat = file.getName().substring(file.getName().lastIndexOf('.') + 1);
        if(!availableFormats.contains(fileFormat)) return false;

        return true;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImage(File file) throws MalformedURLException {
        this.image = new Image(file.toURI().toURL().toString());
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) throws Exception {
        if(imageFile != null){
            if(checkIfImage(imageFile)){
                this.imageFile = imageFile;
                setImage(imageFile);
            } else {
                throw new Exception("Chosen file isn't an image or is an image of unsupported type!");
            }
        }
    }

    /*
    public final static CareTaker CARE_TAKER = new CareTaker();

    public void reverseAction(){
        if(!CARE_TAKER.isEmpty()){
            commandOriginator.executeMemento(CARE_TAKER.getAndRemoveLast());
        }
        if(!CARE_TAKER.isEmpty()){
            commandOriginator.executeMemento(CARE_TAKER.getLast());
        }
    }*/
}
