package com.example.client;

import java.io.*;
import java.net.*;

import com.example.common.Table;
import com.example.server.*;

public class RunClient {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int PORT = 12345;

  public static void main(String[] args) {
    try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
         BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

      AuthenticationData data = new AuthenticationData();

      String mess = (String) in.readObject();
      System.out.println(mess); // "Podaj login:"
      data.login = console.readLine();
      data.password = console.readLine();

      out.writeObject(data);
      out.flush();

      mess = (String) in.readObject();
      System.out.println(mess); // "Zalogowano..."
      mess = (String) in.readObject();
      System.out.println(mess); // "Podaj nazwę pokoju:"

      out.writeObject(console.readLine());
      out.flush();

      mess = (String) in.readObject();
      System.out.println(mess); // "Dołączono do pokoju..."

      final Table[] table = new Table[1];

      new Thread(() -> {
        try {
          Object serverMessage;
          while ((serverMessage = in.readObject()) != null) {
            if(serverMessage instanceof String s)
              System.out.println(s);
            if(serverMessage instanceof Table table1) {
              table[0] = table1;

              table[0].printPoints();
              System.out.println("Tyle kart: "+table1.getPlayer(data.login).getNumberOfCards());
              table[0].getPlayer(data.login).displayHand();
              System.out.println(table1.getDiscardedDeck().countCards());
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      }).start();

      String userInput;
      while ((userInput = console.readLine()) != null) {
        try{
          Integer i = Integer.valueOf(userInput);
          out.writeObject(i);
        }catch (Exception ignored){
          out.writeObject(userInput);
        }
        out.flush();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
