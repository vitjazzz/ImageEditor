package com.vitja.memento;

import org.springframework.stereotype.Service;

import java.util.Stack;

/**
 * Created by Viktor on 20.10.2016.
 */
public class CareTaker {
    private Stack<Memento> mementoStack;

    public CareTaker() {
        mementoStack = new Stack<>();
    }

    public void add(Memento memento){
        mementoStack.push(memento);
    }

    public Memento getAndRemoveLast(){
        return mementoStack.pop();
    }

    public Memento getLast(){
        return mementoStack.peek();
    }

    public boolean isEmpty(){
        return mementoStack.isEmpty();
    }
}
