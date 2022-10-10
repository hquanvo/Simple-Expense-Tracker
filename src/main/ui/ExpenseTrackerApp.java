package ui;

import model.Tracker;


import java.util.Scanner;

//Expense Tracker Application
public class ExpenseTrackerApp {
    private Scanner input;
    private Tracker tracker;

    //EFFECTS: Initialize the tracker
    private void initialize() {
        input = new Scanner(System.in);
        tracker = new Tracker();

    }

    //This method is based on the runTeller() method in TellerApp project provided by UBC CPSC 210 instructor team.

    //EFFECTS:
    private void runProgram() {
        boolean run = true;
        String command;

        initialize();

        while (run) {

        }
    }


}
