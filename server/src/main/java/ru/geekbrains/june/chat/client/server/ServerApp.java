package ru.geekbrains.june.chat.client.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8089);//Запускаем сервер и он слушает порт 8089;
            System.out.println("Сервер запущен. Ожидаем подключение клиентов..");
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Клиент подключился");//Чтобы ждать подключающихся - serverSocket.accept();
            while (true){
                String inputMassage = in.readUTF();//Вычитавать текстовое сообщение на любом языке;
                System.out.println((inputMassage));
                out.writeUTF("ECHO: " + inputMassage);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
