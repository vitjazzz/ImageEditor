package com.vitja.memento;

import com.vitja.commands.Command;

/**
 * Created by Viktor on 20.10.2016.
 */
public class CommandOriginator {
    private Command currentCommand;

    public CommandOriginator() {
    }

    public CommandOriginator(Command currentCommand){
        this.currentCommand = currentCommand;
    }

    public void restoreFromMemento(Memento memento){
        currentCommand = memento.getCommand();
    }

    public Memento saveToMemento(){
        return new Memento(currentCommand);
    }

    public void executeMemento(Memento memento){
        if(memento != null){
            memento.executeCommand();
        }
    }
}
