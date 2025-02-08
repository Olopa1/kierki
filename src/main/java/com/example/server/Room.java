package com.example.server;

import com.example.common.OutcomeFunction;
import com.example.common.OutcomeFunctionSet;
import com.example.common.Player;
import com.example.common.Table;
import com.example.exceptions.NotEnoughCardsInDeck;

import java.util.*;

public class Room{

    public final static OutcomeFunction[] functions = {OutcomeFunctionSet.firstDeal(),OutcomeFunctionSet.secondDeal(),OutcomeFunctionSet.thirdDeal(),OutcomeFunctionSet.fourthDeal(),OutcomeFunctionSet.fifthDeal(),OutcomeFunctionSet.sixthDeal(),OutcomeFunctionSet.seventhDeal()};
    private int currentHand;
    public final static int ROOM_SIZE = 4;
    private final String name;
    private List<HandleClient> clients = new ArrayList<>();
    private Table table;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlayersNumber(){
    return this.clients.size();
  }

    public synchronized boolean addClient(HandleClient client) throws NotEnoughCardsInDeck {
        if(clients.size()==ROOM_SIZE) return false;
        clients.add(client);

        if(clients.size()==ROOM_SIZE){


            startNewGame();
        }

        return true;
    }

    public boolean startNewGame() throws NotEnoughCardsInDeck {
        this.table = new Table(this.clients.get(0).getPlayer(), this.clients.get(1).getPlayer(),
                this.clients.get(2).getPlayer(), this.clients.get(3).getPlayer());

        int currentPlayer = this.table.nextHand(functions[0]);
        this.currentHand = 0;
        broadcast("Gra rozpoczęta", null);
        broadcast(this.table, null);
        this.clients.get(currentPlayer).sendMessage("Teraz ty");
        return true;
    }

    public boolean nextMove(HandleClient client, Integer cardIndex) throws NotEnoughCardsInDeck {
        int result = this.table.nextMove(client.getPlayer(), cardIndex);
        if(result == -1){
            this.currentHand++;
            result = this.table.nextHand(functions[this.currentHand]);
        }
        broadcast(this.table, null);
        this.clients.get(result).sendMessage("Teraz ty");
        return true;
    }

    public synchronized void broadcast(Object message, HandleClient sender) {
        for (HandleClient client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public Table getTable(){
        return this.table;
    }
}
