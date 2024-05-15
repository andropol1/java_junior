package ru.gb.lesson5.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 8181;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void runServer(){
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                ClientManager client = new ClientManager(socket);
                System.out.println("Присоединился новый клиент: " + client.getName());
                Thread thread = new Thread(client);
                thread.start();
            }
        } catch (IOException e) {
            closeSocket();
        }
    }

    public void closeSocket(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        Server server = new Server(serverSocket);
        server.runServer();

    }
}
