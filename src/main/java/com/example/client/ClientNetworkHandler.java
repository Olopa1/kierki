package com.example.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.example.server.AuthenticationData;

public class ClientNetworkHandler {
    private String ipAddress;
    private int port;
    private Socket clientSocket = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    AuthenticationData userData = new AuthenticationData();

    /**
     * Creates new connection to server.
     *
     * @param ipAddress Ip address.
     * @param port      Port.
     * @throws IOException
     */
    public ClientNetworkHandler(String ipAddress, int port) throws IOException {
        try {
            this.ipAddress = ipAddress;
            this.port = port;
            this.clientSocket = new Socket(ipAddress, port);
            this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.in = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Ends connection with server.
     *
     * @throws IOException
     */
    public void endConnection() throws IOException {
        try {
            this.out.close();
            this.in.close();
            this.clientSocket.close();
            this.clientSocket = null;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Logins user to given account.
     *
     * @param login    Login of a user.
     * @param password Password of a user.
     * @return Whether logging in was successful or not.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Boolean loginUser(String login, String password) throws IOException, ClassNotFoundException {
        if (this.clientSocket == null) {
            return false;
        }
        try {
            this.userData.login = login;
            this.userData.password = password;
            out.writeObject(this.userData);
            out.flush();
            String response = (String) in.readObject();
            if (response.toLowerCase().compareTo("zalogowano") == 0) {
                return true;
            }
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return false;
    }

    /**
     * Asks server about rooms information.
     *
     * @return Map of room names and number of players in them.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public HashMap<String, String> getRoomData() throws IOException, ClassNotFoundException {
        if (this.clientSocket == null) {
            return null;
        }
        HashMap<String, String> roomData = null;
        try {
            out.writeObject("pokaz");
            out.flush();
            Object temp;
            temp = in.readObject();
            if (temp instanceof HashMap<?, ?>) {
                roomData = (HashMap<String, String>) temp;
            }
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return roomData;
    }

    /**
     * Request server to join a given room.
     *
     * @param roomName Name of desired room.
     * @return Whether joining was successful or not.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Boolean joinRoom(String roomName) throws IOException, ClassNotFoundException {
        if (this.clientSocket == null) {
            return false;
        }
        try {
            out.writeObject("dolacz");
            out.flush();
            Object responseRaw = in.readObject();
            if (!(responseRaw instanceof String)) {
                throw new ClassNotFoundException();
            }
            String response = (String) responseRaw;
            System.out.println("Odpowiedz serwera: " + response);
            if (response.compareTo("dolaczanie") != 0) {
                return false;
            }
            out.writeObject(roomName);
            out.flush();
            response = (String) in.readObject();
            System.out.println("Odpowiedz serwera 2: " + response);
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        }
        return true;
    }

    /**
     * Sends given message to the rest of the players in a room.
     *
     * @param message String witch message.
     * @throws IOException
     */
    public void sendChatMessage(String message) throws IOException {
        out.writeObject("chat");
        out.writeObject(message);
        out.flush();
    }

    /**
     * Sends message to server.
     *
     * @param message String with command.
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    /**
     * Sends to server info about desired move in the game.
     *
     * @param index Index of card to play.
     * @throws IOException
     */
    public void sendCard(Integer index) throws IOException {
        out.writeObject(index);
        out.flush();
    }

    /**
     * Receives message from the server.
     *
     * @return Object got from the server.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object receiveChatMessage() throws IOException, ClassNotFoundException {
        Object message = in.readObject();
        return message;
    }

}
