//package com.example.client;
//
//public class temp {
//}
//import java.io.*;
//        import java.net.*;
//
//public class Client {
//    private static final String SERVER_ADDRESS = "localhost";
//    private static final int PORT = 12345;
//
//    public static void main(String[] args) {
//        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
//            System.out.println("Połączono z serwerem");
//
//            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//
//            while (true) {
//                // Odbiór obiektu
//                SharedObject receivedObject = (SharedObject) in.readObject();
//                System.out.println("Klient otrzymał: " + receivedObject);
//
//                // Modyfikacja obiektu
//                receivedObject.modify();
//                System.out.println("Klient zmodyfikował: " + receivedObject);
//
//                // Odesłanie obiektu do serwera
//                out.writeObject(receivedObject);
//                out.flush();
//            }
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
