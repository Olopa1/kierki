package com.example.server;

import com.example.common.Player;
import com.example.common.Table;
import com.example.exceptions.NotEnoughCardsInDeck;

import java.io.*;
import java.net.*;

public class HandleClient implements Runnable {
    private Socket socket;
    private String username;
    private Room room;
    private Player player;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public HandleClient(Socket socket) {
        this.socket = socket;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.room = null;
    }

    @Override
    public void run() {
        try {
            //out.writeObject("Podaj login:");
            //out.flush();

            AuthenticationData data = (AuthenticationData) in.readObject();
            if (!RunServer.authenticate(data.login, data.password)) {
                out.writeObject("brak uzytkownika");
                out.flush();
                socket.close();
                return;
            }
            this.username = data.login;
            out.writeObject("zalogowano");
            out.flush();
            System.out.println("Zalogownao uzytkownika: " + this.username);
                      this.player = new Player(this.username);
            
            this.joinRoom();
            listenForMessages();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NotEnoughCardsInDeck e) {
            throw new RuntimeException(e);
        }
    }

    private void joinRoom(){
        try {
            while (true) {
                Object rawResponse = this.in.readObject();
                if (rawResponse instanceof String) {
                    String responseMessage = (String) rawResponse;
                    if (responseMessage.compareTo("dolacz") == 0) {
                        //PODAJE NAZWE POKOJU
                        out.writeObject("dolaczanie");
                        out.flush();
                        String roomName = (String) in.readObject();
                        this.room = RunServer.findOrCreateRoom(roomName);
                        room.addClient(this);

                        out.writeObject("dolaczono");
                        out.flush();
                        break;
                    } else if (responseMessage.compareTo("pokaz") == 0) {
                        System.out.println(RunServer.getRoomsData());
                        out.writeObject(RunServer.getRoomsData());
                        out.flush();
                    }
                } else {
                    continue;
                }

            }
        } catch (NotEnoughCardsInDeck e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void listenForMessages() throws IOException, ClassNotFoundException, NotEnoughCardsInDeck {
        try {
            Object object;
            while ((object = in.readObject()) != null) {
                if (object instanceof String message) {
                    if (message.compareTo("chat") == 0) {
                        String chatMessage = (String) in.readObject();
                        room.broadcast(username + ": " + chatMessage, this);
                    } else if (message.compareTo("disconnect") == 0) {
                        this.disconnect();
                        break;
                    }
                    else if (message.compareTo("leave") == 0) {
                        System.out.println("Uzytkownik opuszcza pokoj");
                        leaveRoom();
                        joinRoom();
                    }
                    else room.broadcast(username + ": " + message, this);
                }
                if (object instanceof Table table) {
                    room.broadcast(table, null);
                }
                if (object instanceof Integer cardIndex) {
                    room.nextMove(this, cardIndex);
                }
            }
        }
        catch (Exception e){
            disconnect();
        }
    }

    public void sendMessage(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();

        } catch (IOException e) {
            System.out.println("Nie udalo sie wyslac");
//            throw new RuntimeException(e);
        }
    }

    public Player getPlayer(){
        return this.player;
    }
    public String getUsername(){
        return this.username;
    }

    public void disconnect() {
        try {
            leaveRoom();

            sendMessage("disconnect");

            if (in != null) in.close();
            in = null;
            if (out != null) out.close();
            out = null;
            if (socket != null) socket.close();
            socket = null;
        }
        catch (Exception e){
            System.out.println("Problem z disconnect");
        }
    }

    public void leaveRoom(){
        sendMessage("leave");
        if(room != null) room.removeClient(this);
        room = null;
    }
}
