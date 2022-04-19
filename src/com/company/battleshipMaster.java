package com.company;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class battleshipMaster {

    // 2D ARRAY
    int[][] locations;
    int[] shipsArr = {2, 3, 3, 4, 5};
    int sunkenShips, tries, length;

    JFrame frame = new JFrame();
    JPanel containerPanel;


    JPanel buttonPanel;
    JButton[][] buttonArr;

    public void setUpGame() {
        // The purpose of this class is to create the start screen, get the size of the board the user wants, and then call createGame() to make the game itself
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel startPanel = new JPanel();

//		startPanel = addPair("Enter your name", startPanel);
        JTextField textField = new JTextField(20);
        JLabel label = new JLabel("What size grid would you like? 8+ ");
        label.setPreferredSize(new Dimension(200, 200));
        startPanel.add(label);
        startPanel.add(textField);

        JButton button1 = new JButton("Continue");
        button1.setPreferredSize(new Dimension(200, 200));
        startPanel.add(button1);
        final JPanel startPanel2 = startPanel;

        startPanel.setPreferredSize(new Dimension(600, 600));
        frame.getContentPane().add(startPanel2);
        frame.pack();
        frame.setVisible(true);

        // Add an actionListener for when the button is pressed
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // ERROR PROTECTION FOR BOTH INVALID GRADES AND NAMES
                    if(textField.getText() == null || textField.getText() == "" || textField.getText() == " " || textField.getText().equals("")) {
                        textField.setText("Please input a value");
                    } else if(Integer.parseInt(textField.getText()) < 8) {
                        textField.setText("Choose a valid number!");
                    } else {
                        clearPanel(startPanel2);
                        createGame(Integer.parseInt(textField.getText()));
                        createFrame();
                    }

                } catch (NumberFormatException f) {
                    textField.setText("Please input a valid value");
                }
            }
        });



    }

    public void createGame(int boardSize) {
        // The 2D array to keep track of the locations of the battleships, as well as an int to keep track of how many battleships are left
        locations = new int[boardSize][boardSize];

        // Initialize all the variables
        sunkenShips = 0;
        tries = 0;
        length = boardSize;

        // Place the ships on the board
        int currentShip = 0;
        String direction;
        int[] coordsStart = new int[2];
        int[] coordsEnd = new int[2];
        int lengthOfShip;

        while(currentShip <= 4) {

            lengthOfShip = shipsArr[currentShip];
            // Generate a random position
            direction = "updown";
            if(Math.random() > .5) direction = "leftright";
            coordsStart = generatePos(lengthOfShip, direction, length);

            // The position of one end of the ship has been generated, figure out what the position of the other end is.
            if(direction == "updown") {
                coordsEnd[0] = coordsStart[0];
                coordsEnd[1] = coordsStart[1] + lengthOfShip;
            } else {
                coordsEnd[1] = coordsStart[1];
                coordsEnd[0] = coordsStart[0] + lengthOfShip;
            }

            // Check if the position is valid. If so, move on to the next ship. if not, the loop keeps running till something does work.
            if(checkPos(coordsStart, coordsEnd)) {
                currentShip++;
                System.out.println("Ship placed of length " + lengthOfShip + " at " + Arrays.toString(coordsStart) + " to " +  Arrays.toString(coordsEnd));
                for(int i = 0; i < lengthOfShip; i++) {
                    if(direction == "updown") {
                        locations[coordsStart[0]][coordsStart[1] + i] = lengthOfShip;
                    } else {
                        locations[coordsStart[0] + i][coordsStart[1]] = lengthOfShip;
                    }
                }
            }

        }

        for(int x = 0; x < length; x++) {
            System.out.println(Arrays.toString(locations[x]));
        }

    }

    public int[][] getGrid() {
        return locations;
    }

    // This method generates a random (x, y) position for a boat to be in, making sure that it is not off the edge.
    public int[] generatePos(int length2, String dir, int boardLength) {
        int[] returnArr = new int[2];
        if(dir == "updown") {
            returnArr[0] = (int) Math.floor(Math.random()*boardLength);
            returnArr[1] = (int) Math.floor(Math.random()*(boardLength - (length2 - 1)));
        } else {
            returnArr[1] = (int) Math.floor(Math.random()*boardLength);
            returnArr[0] = (int) Math.floor(Math.random()*(boardLength - (length2 - 1)));
        }
        System.out.println("Trying " + Arrays.toString(returnArr));
        return returnArr;
    }





    // This method iterates over all the places where the computer wants to place a battleship to make sure there are no ships there
    public boolean checkPos(int[] start, int[] end) {

        if(start[0] == end[0]) {
            for(int a = start[1]; a < end[1]; a++) {
                if(locations[start[0]][a] != 0) {
                    return false;
                }
            }
        } else {
            for(int a = start[0]; a < end[0]; a++) {
                if(locations[a][start[1]] != 0) {
                    return false;
                }
            }
        }
        return true;
    }








    public void createFrame() {
        // CREATE THE JFRAME



        //CREATE THE PANELS
        buttonPanel = new JPanel();
        containerPanel = new JPanel();
        buttonArr = new JButton[length][length];
        // Make the panel a grid
        buttonPanel.setLayout(new GridLayout(length, length));

        // Nested for loop to make the grid of buttons
        for(int i = 0; i < length; i ++) {
            for(int k  = 0; k < length; k ++) {

                JButton currentButton = new JButton(" ");
                currentButton.setPreferredSize(new Dimension(480/length, 480/length));
                currentButton.setName(i + " " + k);

                // Add an actionListener for when the button is pressed
                currentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        if(hitShip(button.getName())) {

                        } else {
                            button.setText("Miss!");
                            button.setBackground(Color.RED);
                        }
                    }
                });
                // Add the button to the panel made
                buttonPanel.add(currentButton);
                buttonArr[i][k] = currentButton;
            }
        }
        buttonPanel.setPreferredSize(new Dimension(600, 600));
        containerPanel.add(buttonPanel);

        addPanel(containerPanel);
        frame.pack();
    }

    public boolean hitShip(String point) {
        tries++;
        StringTokenizer s = new StringTokenizer(point);
        int x = Integer.parseInt(s.nextToken());
        int y = Integer.parseInt(s.nextToken());
        if(locations[x][y] == 0) {
            System.out.println("Hit water");
            return false;
        } else if(locations[x][y] == 6) {
            System.out.println("You've already tried this square");
        }
        System.out.println("Hit ship");
        int typeOfShip = locations[x][y];
        locations[x][y] = 6;
        // Check if the ship itself is sunk.
        Boolean shipSunk = true;
        for(int o = 0; o < length; o++) {
            for(int u = 0; u < length; u++) {
                if(locations[o][u] == typeOfShip) shipSunk = false;
            }
        }

        buttonArr[x][y].setText("Hit!");
        buttonArr[x][y].setBackground(Color.GREEN);

        if(shipSunk) {
            System.out.println("The ship(s) of length " + typeOfShip + " has been sunk!");
            sunkenShips++;
            if(sunkenShips == 4) {
                endGame();
            }
        }


        return true;
    }

    public void endGame() {



        master.tictacGame.reportScore(tries);

        if(master.tictacGame.playAgain()) {
            clearPanel(containerPanel);
            JPanel endPanel = new JPanel();
            JLabel label = new JLabel("You are done in " + tries + " tries! Congratulations!");
            label.setPreferredSize(new Dimension(600, 200));
            JButton button1 = new JButton("Player 2");
            button1.setPreferredSize(new Dimension(200, 200));

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearPanel(endPanel);
                    setUpGame();
                }
            });
            endPanel.add(label);
            endPanel.add(button1);
            addPanel(endPanel);
        } else {
            master.tictacGame.tictacGui.showFrame();
            frame.setVisible(false);
        }
        System.out.println("Nice job!");


        // Add an actionListener for when the button is pressed



    }

    public void clearPanel(JPanel panel) {
        frame.remove(panel);
    }

    public void addPanel(JPanel panel) {
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }


    // The premise of the game is that I create a 2d array with the "ships", 0 is a sea, 1 is ship 1, 2 is ship 2, etc etc
    // The computer places the ships automatically and makes sure they don't overlap or go off the edge
    // After every "hit", the computer checks if there are any of that ship left. If not, that ship is gone and the user is informed

    // A list of the ships that the computer puts on the board:
    // A single ship five squares long (the aircraft carrier)
    // A single ship four squares long (the battleship)
    // Two ships three squares long (the cruiser and the submarine)
    // A single ship two squares long (the destroyer)

    // I've tried to abstract AS MUCH as I can and am happy to have a very minimal main method.




}



