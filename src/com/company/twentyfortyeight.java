package com.company;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;

public class twentyfortyeight {
    int board[][] = new int[4][4];

    public twentyfortyeight() {
        int numPlaces = 0;
        int xtest, ytest;
        while(numPlaces < 2) {
            xtest = (int) (Math.random()*4);
            ytest = (int) (Math.random()*4);
            if(checkIndex(xtest, ytest)) {
                board[xtest][ytest] = 2;
                numPlaces++;
            }
        }
    }

    public void printBoard() {
        for(int o = 0; o < 4; o++) {
            for(int r = 0; r < 4; r++) {
                System.out.print(board[o][r] + " ");
            }
            System.out.println();
        }
    }

    private boolean checkIndex(int x, int y) {
        if(board[x][y] != 0) return false;
        return true;
    }

    private void resetboard() {
        for(int o = 0; o < 4; o++) {
            for(int r = 0; r < 4; r++) {
                board[o][r] = 0;
            }
        }
        int numPlaces = 0;
        int xtest, ytest;
        while(numPlaces < 2) {
            xtest = (int) (Math.random()*4);
            ytest = (int) (Math.random()*4);
            if(checkIndex(xtest, ytest)) {
                board[xtest][ytest] = 2;
                numPlaces++;
            }
        }
    }

    private int[] calculateMerge(int[] input2) {

        int[] input = {input2[0], input2[1], input2[2], input2[3]};

        if(input[3] == input[2]) {
            input[3] = input[3]*2;
            input[2] = input[1];
            input[1] = input[0];
            input[0] = 0;
        }

        if(input[2] == 0 && (input[3] == input[1])) {
            input[3] = input[3]*2;
            input[2] = input[0];
            input[1] = 0;
            input[0] = 0;
        }

        if(input[2] == 0 && input[1] == 0 && (input[3] == input[0])) {
            input[3] = input[3]*2;
            input[2] = 0;
            input[1] = 0;
            input[0] = 0;
        }

        if(input[2] == input[1]) {
            input[2] = input[2]*2;
            input[1] = input[0];
            input[0] = 0;
        }

        if(input[1] == 0 && (input[2] == input[0])) {
            input[2] = input[2]*2;
            input[1] = 0;
            input[0] = 0;
        }

        if(input[1] == input[0]) {
            input[1] = input[1]*2;
            input[0]  = 0;
        }

        while(input[3] == 0) {
            if(input[3] == 0 && input[2] == 0 && input[1] == 0 && input[0] == 0) break;
            input[3] = input[2];
            input[2] = input[1];
            input[1] = input[0];
            input[0] = 0;
        }

        while(input[2] == 0) {
            if(input[2] == 0 && input[1] == 0 && input[0] == 0) break;
            input[2] = input[1];
            input[1] = input[0];
            input[0] = 0;
        }

        while(input[1] == 0) {
            if(input[1] == 0 && input[0] == 0) break;
            input[1] = input[0];
            input[0] = 0;
        }

        return input;
    }


    public void moveRight() {
        int a = 0;
        for(int i = 0; i < 4; i++) {
            if(Arrays.equals(board[i], calculateMerge(board[i]))) a++;
            board[i] = calculateMerge(board[i]);
        }
        if(a != 4) {
            addNumber();
        }
    }

    public void moveLeft() {
        int a = 0;
        for (int i=0; i<4; i++) {
            int[] reverse = {board[i][3], board[i][2], board[i][1], board[i][0]};
            int[] temp = calculateMerge(reverse);
            if(Arrays.equals(reverse, temp)) a++;
            for (int j=0; j<4; j++) {
                board[i][j] = temp[3-j];
            }
        }
        if(a != 4) {
            addNumber();
        }
    }

    public void moveTop() {
        int a = 0;
        for(int i = 0; i < 4; i++) {
            int[] k = {board[3][i], board[2][i], board[1][i], board[0][i]};
            int[] temp = calculateMerge(k);
            if(Arrays.equals(k, temp)) {
                a++;
            }
            for(int j = 3; j >= 0; j--) {
                board[j][i] = temp[3 - j];
            }
        }
        if(a != 4) {
            addNumber();
        }
    }


    public void moveBottom() {
        int a = 0;
        for(int i = 0; i < 4; i++) {
            int[] k = {board[0][i], board[1][i], board[2][i], board[3][i]};
            int[] temp = calculateMerge(k);
            if(Arrays.equals(k, temp)) {
                a++;
            }
            for(int j = 0; j < 4; j++) {
                board[j][i] = temp[j];
            }
        }
        if(a != 4) {
            addNumber();
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int addNumber() {
        boolean done = false;
        int counter = 0;
        while(!done) {
            counter++;
            if(counter > 100) {
                return sumGame();
            } else {
                int xtemp = (int) (Math.random()*4);
                int ytemp = (int) (Math.random()*4);
                if(board[xtemp][ytemp] == 0) {
                    if((Math.random()*1) > .9) {
                        board[xtemp][ytemp] = 4;
                    } else {
                        board[xtemp][ytemp] = 2;
                    }
                    done = true;
                }

            }
        }
        return -1;
    }

    public boolean checkIfDone() {
        for(int i = 0; i < 4; i ++) {
            for(int j = 0; j < 4; j++) {
                if(board[i][j] == 0) return false;
            }
        }
        return true;
    }

    public int sumGame() {
        int tot = 0;
        for(int o = 0; o < 4; o++) {
            for(int r = 0; r < 4; r++) {
                tot += board[o][r];
            }
        }
        return tot;
    }

    public void createField() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(600, 300));
    }



    public void renderFrame(twentyfortyeight game, gridGUI GUI) {
        for(int i = 0; i < 4; i++) {
            for(int k = 0; k < 4; k++) {
                try {
                    GUI.getGrid()[i][k].setName(i + " " + k);
                    GUI.getGrid()[i][k].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton button = (JButton) e.getSource();

                            if(checkIfDone()) {;
                                master.tictacGame.reportScore(1000000000 - sumGame());
                                if(master.tictacGame.playAgain()) {
                                    resetboard();
                                } else {
                                    GUI.removeFrame();
                                    master.tictacGame.tictacGui.showFrame();
                                }
                            } else {
                                int a = Integer.parseInt(button.getName().substring(0, 1));
                                int b = Integer.parseInt(button.getName().substring(2, 3));

                                if(a == 0 && (b == 1 || b == 2)) {
                                    game.moveTop();
                                    GUI.updateFrame(game.getBoard());
                                    game.renderFrame(game, GUI);
                                } else if (a == 3 && (b == 1 || b == 2)) {
                                    game.moveBottom();
                                    GUI.updateFrame(game.getBoard());
                                    game.renderFrame(game, GUI);
                                } else if (b == 0 && (a == 1 || a ==2)) {
                                    game.moveLeft();
                                    GUI.updateFrame(game.getBoard());
                                    game.renderFrame(game, GUI);
                                } else if (b == 3 && (a == 1 || a == 2)) {
                                    game.moveRight();
                                    GUI.updateFrame(game.getBoard());
                                    game.renderFrame(game, GUI);
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }




    }



}



