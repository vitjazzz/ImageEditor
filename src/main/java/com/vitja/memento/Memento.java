package com.vitja.memento;

import com.vitja.commands.Command;

/**
 * Created by Viktor on 20.10.2016.
 */
public class Memento {
    private Command command;

    public Memento(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void executeCommand(){
        command.execute();
    }
}
