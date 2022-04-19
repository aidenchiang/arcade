package com.company;

public class master {

    static TicTacToe tictacGame;

    public static void main(String[] args) {
        tictacGame = new TicTacToe();

        gridGUI gui = new gridGUI(tictacGame.getBoard());
        tictacGame.tictacGui = gui;
        gui.updateFrame(tictacGame.getBoard());

    }
}
