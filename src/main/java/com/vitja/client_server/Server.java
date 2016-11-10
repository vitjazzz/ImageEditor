package com.vitja.client_server;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Created by Viktor on 10.11.2016.
 */
public class Server extends NetworkConnection {
    private Integer port;

    public Server(Integer port, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected Integer getPort() {
        return port;
    }
}
