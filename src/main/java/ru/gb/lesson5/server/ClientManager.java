package ru.gb.lesson5.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Инкапсуляция для клиента на сервере
 */
public class ClientManager implements Runnable {
    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;
    public static ArrayList<ClientManager> clients = new ArrayList<>();
    public ClientManager(Socket socket) {
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            name = bufferedReader.readLine();
            clients.add(this);
            broadcastMessage("К чату подключился: " + name);
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (!socket.isClosed()) {
            try {
                messageFromClient = bufferedReader.readLine();

                if (Objects.equals(messageFromClient, "exit")) {
                    System.out.println(name + " покинул чат");
                    broadcastMessage("Client " + name + " покинул чат");
                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                } else if (messageFromClient.startsWith(name + ": @")) {
                    String[] split = messageFromClient.split("\\s+");
                    String loginTo = split[1].substring(1);
                    String pureMessage = messageFromClient.replace(name + ": @" + loginTo + " ", "");
                    privateMessage(pureMessage, loginTo);
                } else {
                    broadcastMessage(messageFromClient);
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    private void privateMessage(String messageToSend, String loginTo) {
        for (ClientManager client : clients) {
            try {
                if (client.name.equals(loginTo)) {
                    client.bufferedWriter.write(name + ": " + messageToSend);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    private void broadcastMessage(String message){
        for (ClientManager client : clients) {
            try {
                if (!client.name.equals(name)){
                    client.bufferedWriter.write(message);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClient();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeClient() {
        clients.remove(this);
        System.out.println(name + " вышел из чата");
        broadcastMessage(name + " вышел из чата");
    }

    public String getName() {
        return name;
    }
}
