package com.example.server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Main class of the server.
 */
public class RunServer {
    private static final int PORT = 12345;
    private static Map<String, String> users = new HashMap<>(); // Login -> Hasło
    private static List<Room> rooms = new ArrayList<>();
    private static List<HandleClient> clients = new ArrayList<>();

    /**
     * Main server's method.
     *
     * @param args Console arguments.
     */
    public static void main(String[] args) {
        loadUsers(); // Wczytanie kont z pliku (dla prostoty)
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serwer uruchomiony na porcie " + PORT);

            new Thread(() -> {
                try {
                    String command;
                    BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                    while ((command = console.readLine()) != null) {
                        if ((command.compareTo("rooms") == 0)) {
                            HashMap<String, String> rooms = getRoomsData();
                            for (String room : rooms.keySet()) {
                                System.out.println(room + ": " + rooms.get(room) + " miejsc zajętych");
                            }
                        } else if ((command.startsWith("room "))) {
                            HashMap<String, String> rooms = getRoomsData();
                            System.out.println(command.substring(5) + ": " + rooms.get(command.substring(5)) + " miejsc zajętych");
                            ArrayList<String> players = findOrCreateRoom(command.substring(5)).getPlayers();
                            for (String name : players) {
                                System.out.println("gracz: " + name);
                            }
                        } else if ((command.startsWith("end "))) {
                            findOrCreateRoom(command.substring(4)).forceEndGame();
                        } else if ((command.compareTo("shutdown")) == 0) {
                            rooms.clear();
                            for (HandleClient client : clients) {
                                client.disconnect();
                            }
                            clients.clear();

                            try {
                                serverSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            System.exit(0);
                        } else {
                            System.out.println("Nie znaleziono komendy");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie: " + clientSocket);

                HandleClient client = new HandleClient(clientSocket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException ignored) {

        }
    }

    /**
     * Loads users from file.
     */
    private static void loadUsers() {
        users.put("admin", "admin123"); // Na start przykładowy użytkownik
        users.put("p1", ""); // Na start przykładowy użytkownik
        users.put("p2", ""); // Na start przykładowy użytkownik
        users.put("p3", ""); // Na start przykładowy użytkownik
        users.put("p4", ""); // Na start przykładowy użytkownik
    }

    /**
     * Checks whether user exists or not.
     *
     * @param login    Login of a user.
     * @param password Password of a user.
     * @return Whether authentication data is correct or no.
     */
    public static synchronized boolean authenticate(String login, String password) {
        return users.containsKey(login) && users.get(login).equals(password);
    }

    /**
     * Finds or creates room.
     *
     * @param roomName Name of a room.
     * @return Room object of a given name.
     */
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

    /**
     * Gives info about active rooms in the game.
     *
     * @return Map with room names and number of players in each.
     */
    public static HashMap<String, String> getRoomsData() {
        HashMap<String, String> roomsData = new HashMap<String, String>();
        for (Room room : rooms) {
            roomsData.put(room.getName(), Integer.toString(room.getPlayersNumber()));
        }
        return roomsData;
    }
}
