package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

// Entry is the atomic element of BudgetList
public class Entry {
    private double amount; //amount of money spent
    private Category category; //categories
    private LocalDate date; //
    private String description;

    //EFFECTS: Construct an Entry with 0 amount spent, default type and date, empty description
    public Entry() {
        amount = 0;
        category = Category.DEFAULT;
        date = LocalDate.of(2000, 1, 1);
        description = "";
    }


    //getters
    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    public String getDescription() {
        return description;
    }

    //setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String ct) {
        switch (ct) {
            case "Food":
                this.category = Category.FOOD;
                break;
            case "Rent":
                this.category = Category.RENT;
                break;
            case "Supplies":
                this.category = Category.SUPPLIES;
                break;
            case "Bills":
                this.category = Category.BILLS;
                break;
            case "Others":
                this.category = Category.OTHERS;
                break;
            default:
                System.out.println("That category does not exist.");
        }
    }

    //REQUIRES: Date must be in yyyy/mm/dd format
    //EFFECTS: Change a date by parsing a string
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    //REQUIRES: Year month day must for a valid date
    //EFFECTS: Change a date by entering year, month, day
    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    //REQUIRES: Month must be in [1, 12]
    //EFFECTS: Change a date by entering year and month
    public void setDate(int year, int month) {
        this.date = LocalDate.of(year, month, date.getDayOfMonth());
    }

    //EFFECTS: Change a date by entering year only
    public void setDate(int year) {
        this.date = LocalDate.of(year, date.getMonth(), date.getDayOfMonth());
    }


    public void setDescription(String description) {
        this.description = description;
    }
}