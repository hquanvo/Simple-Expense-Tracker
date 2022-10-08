package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

// Entry is the atomic element of BudgetList
public class Entry {
    private double amount; //amount of money spent
    private Category category; //categories
    private LocalDate date; //
    private String description;

    //REQUIRES: amt > 0
    //EFFECTS: Construct an Entry with amount spent, default type, a date, and an empty description
    public Entry(double amt, String date, Category category, String description) {
        this.amount = amt;
        this.category = category;
        this.date = LocalDate.parse(date);
        this.description = description;
    }


    //getters
    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category.toString().charAt(0) + category.toString().substring(1).toLowerCase();
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
    //MODIFIES: this
    //EFFECTS: Change a date by entering year, month, day
    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    //REQUIRES: Month must be in [1, 12]
    //MODIFIES: this
    //EFFECTS: Change a date by entering year and month
    public void setDate(int year, int month) {
        this.date = LocalDate.of(year, month, date.getDayOfMonth());
    }

    //MODIFIES: this
    //EFFECTS: Change a date by entering year only
    public void setDate(int year) {
        this.date = LocalDate.of(year, date.getMonth(), date.getDayOfMonth());
    }


    public void setDescription(String description) {
        this.description = description;
    }
}