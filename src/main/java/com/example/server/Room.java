package com.example.server;

import com.example.common.OutcomeFunction;
import com.example.common.OutcomeFunctionSet;
import com.example.common.Table;
import com.example.exceptions.NotEnoughCardsInDeck;

import java.util.*;

/**
 * Class that handles whole gameplay for given four players.
 */
public class Room {

    public final static OutcomeFunction[] functions = {OutcomeFunctionSet.firstDeal(), OutcomeFunctionSet.secondDeal(), OutcomeFunctionSet.thirdDeal(), OutcomeFunctionSet.fourthDeal(), OutcomeFunctionSet.fifthDeal(), OutcomeFunctionSet.sixthDeal(), OutcomeFunctionSet.seventhDeal()};
    private int currentHand;
    public final static int ROOM_SIZE = 4;
    private final String name;
    private List<HandleClient> clients = new ArrayList<>();
    private Table table;
    private boolean endGame;

    /**
     * Initializes given room.
     *
     * @param name Name of new room.
     */
    public Room(String name) {
        this.name = name;
        this.endGame = false;
    }

    /**
     * Adds given client to this room.
     *
     * @param client HandleClient of the client.
     * @return Whether client was successfully added or not.
     * @throws NotEnoughCardsInDeck
     */
    public synchronized boolean addClient(HandleClient client) throws NotEnoughCardsInDeck {
        if (clients.size() == ROOM_SIZE) return false;
        clients.add(client);

        if (clients.size() == ROOM_SIZE) {


            startNewGame();
        }

        return true;
    }

    /**
     * Initializes Table and starts a game.
     *
     * @return Whether starting game was successful or not.
     * @throws NotEnoughCardsInDeck
     */
    public boolean startNewGame() throws NotEnoughCardsInDeck {
        this.table = new Table(this.clients.get(0).getPlayer(), this.clients.get(1).getPlayer(),
                this.clients.get(2).getPlayer(), this.clients.get(3).getPlayer());

        int currentPlayer = this.table.nextHand(functions[0]);
        this.currentHand = 0;
        broadcast("Start", null);
        broadcast(this.table, null);
        this.clients.get(currentPlayer).sendMessage("ty");
        return true;
    }

    /**
     * Performs move in the game.
     *
     * @param client    Client for who will be executed action.
     * @param cardIndex Card that will be played.
     * @return Whether move was successfully executed or not.
     * @throws NotEnoughCardsInDeck
     */
    public boolean nextMove(HandleClient client, Integer cardIndex) throws NotEnoughCardsInDeck {
        if (this.endGame) return false;

        int result = this.table.nextMove(client.getPlayer(), cardIndex);
        if (result == -1) {
            this.currentHand++;
            if (this.currentHand == 7) endGame = true;
            else
                result = this.table.nextHand(functions[this.currentHand]);
        }
        broadcast(this.table, null);

        if (endGame) broadcast("koniec", null);

        this.clients.get(result).sendMessage("ty");
        return true;
    }

    /**
     * Forwards given Object to all players in a room.
     *
     * @param message Object implementing serializable.
     * @param sender  Player form whom message is sent.
     */
    public synchronized void broadcast(Object message, HandleClient sender) {
        for (HandleClient client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    /**
     * Forces finishing the current game and gives random values to the players.
     *
     * @return Whether action was successful or not.
     */
    public boolean forceEndGame() {
        if (this.endGame) return false;

        this.endGame = true;
        ArrayList<String> players = this.getPlayers();
        Random random = new Random();
        for (String player : players)
            table.getPlayer(player).changeScore(-(random.nextInt(50) + 1) * 10);

        broadcast(table, null);
        broadcast("koniec", null);
        return true;
    }

    /**
     * Removes given client from a room.
     *
     * @param client HandleClient of a client to be removed.
     */
    public void removeClient(HandleClient client) {
        clients.remove(client);
    }

    public ArrayList<String> getPlayers() {
        ArrayList<String> players = new ArrayList<>();
        for (HandleClient player : this.clients) {
            players.add(player.getUsername());
        }
        return players;
    }

    public Table getTable() {
        return this.table;
    }

    public String getName() {
        return name;
    }

    public int getPlayersNumber() {
        return this.clients.size();
    }
}
