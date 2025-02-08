package com.example.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class RunServer {
  private static final int PORT = 12345;
  private static Map<String, String> users = new HashMap<>(); // Login -> Hasło
  private static List<Room> rooms = new ArrayList<>();

  public static void main(String[] args) {
    loadUsers(); // Wczytanie kont z pliku (dla prostoty)
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Serwer uruchomiony na porcie " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Nowe połączenie: " + clientSocket);

        new Thread(new HandleClient(clientSocket)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void loadUsers() {
    users.put("admin", "admin123"); // Na start przykładowy użytkownik
    users.put("p1", ""); // Na start przykładowy użytkownik
    users.put("p2", ""); // Na start przykładowy użytkownik
    users.put("p3", ""); // Na start przykładowy użytkownik
    users.put("p4", ""); // Na start przykładowy użytkownik
  }

  public static synchronized boolean authenticate(String login, String password) {
    return users.containsKey(login) && users.get(login).equals(password);
  }

  public static synchronized Room findOrCreateRoom(String roomName) {
    for (Room room : rooms) {
      if (room.getName().equals(roomName)) {
        return room;
      }
    }
    Room newRoom = new Room(roomName);
    rooms.add(newRoom);
    return newRoom;
  }

  public static HashMap<String,String> getRoomsData(){
    HashMap<String, String> roomsData = new HashMap<String,String>();
    for(Room room : rooms){
      roomsData.put(room.getName(),Integer.toString(room.getPlayersNumber()));
    }
    return roomsData;
  }
}
