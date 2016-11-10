package com.vitja.client_server;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Created by Viktor on 10.11.2016.
 */
public class Client extends NetworkConnection {
    private Integer port;
    private String ip;

    public Client(Integer port, String ip, Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return ip;
    }

    @Override
    protected Integer getPort() {
        return port;
    }
}
