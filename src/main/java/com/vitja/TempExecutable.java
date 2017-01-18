package com.vitja;

import com.vitja.composite.CompositeCommand;
import com.vitja.states.CutState;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Viktor on 07.11.2016.
 */
public class TempExecutable {

    public static void main(String[] args) {
        TempExecutable tempExecutable = new TempExecutable();

        CompositeCommand command = tempExecutable.getCompositeCommand();
        command.setName("not Hello!");
        System.out.println(tempExecutable.getCompositeCommand().getName());
    }

    CompositeCommand compositeCommand = new CompositeCommand("Hello");

    public CompositeCommand getCompositeCommand() {
        return compositeCommand;
    }
}
