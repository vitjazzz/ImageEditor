package com.vitja.client_server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Created by Viktor on 10.11.2016.
 */
public abstract class NetworkConnection {
    private ConnectionThread connectionThread = new ConnectionThread();
    private Consumer<Serializable> onReceiveCallback;

    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    public void startConnection() throws Exception{
        connectionThread.start();
    }

    public void closeConnection() throws Exception{
        connectionThread.socket.close();
    }

    public void send(Serializable data) throws Exception {
        connectionThread.outputStream.writeObject(data);
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract Integer getPort();

    private class ConnectionThread extends Thread{
        private Socket socket;
        private ObjectOutputStream outputStream;

        @Override
        public void run() {
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                 Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())
            ){
                this.socket = socket;
                this.outputStream = outputStream;
                socket.setTcpNoDelay(true);

                while (true){
                    Serializable data = (Serializable) inputStream.readObject();
                    onReceiveCallback.accept(data);
                }
            }catch (Exception e){
                onReceiveCallback.accept("Connection closed.");
            }
        }
    }
}
