package ru.geekbrains.june.chat.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextArea chatArea;

    @FXML
    TextField massageField;


    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localhost",8089);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(() -> {
                while (true){
                    try {
                        String inputMassage = in.readUTF();
                        chatArea.appendText(inputMassage + "\n");//Используем многопоток, чтобы в отдельном потоке
                                                                //крутился бесконечный цикл;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.start();
        } catch (IOException e) {
            System.out.println("Нeвозможно подключится");
            System.exit(0);
        }
    }
    public void sendMassage(){
        try {
            out.writeUTF(massageField.getText());
            massageField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

