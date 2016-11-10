package com.vitja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Viktor on 07.11.2016.
 */
public class TempExecutable {

    private static void threadMessage(String message){
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    private static class MessageLoop implements Runnable{
        @Override
        public void run() {
            String info[] = {
                    "Hello", "world", "full", "of", "fools"
            };
            try {
                for (int i = 0;
                        i < info.length;
                        i++){
                    Thread.sleep(4000);
                    threadMessage(info[i]);
                }
            } catch (Exception e){
                threadMessage("Thread interrupted!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long patience = 1000 * 60;

        String input = "60";

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Enter something : ");
            input = br.readLine();


        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            patience = Long.parseLong(input) * 1000;
        } catch (NumberFormatException e){
            System.exit(1);
        }

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread t = new Thread(new MessageLoop());
        t.start();

        threadMessage("Waiting for MessageLoop thread to finish");

        while (t.isAlive()){
            threadMessage("Still waiting...");
            t.join(1000);
            if(((System.currentTimeMillis() - startTime) > patience)
                    && t.isAlive()){
                threadMessage("Tired of waiting!");
                t.interrupt();
                t.join();
            }
        }
        threadMessage("Finally!");
    }
}
