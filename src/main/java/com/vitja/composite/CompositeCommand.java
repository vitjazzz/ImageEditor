package com.vitja.composite;

import com.vitja.commands.Command;
import java.util.Stack;

/**
 * Created by Viktor on 03.11.2016.
 */
public class CompositeCommand {
    private String name;

    private Stack<CompositeCommand> editCommands;

    private Command command;

    public CompositeCommand() {
        this.editCommands = new Stack<>();
    }

    public CompositeCommand(String name) {
        this.name = name;
        this.editCommands = new Stack<>();
    }

    public CompositeCommand(Command command) {
        this.command = command;
        this.editCommands = new Stack<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Stack<CompositeCommand> getEditCommands() {
        return editCommands;
    }
}
