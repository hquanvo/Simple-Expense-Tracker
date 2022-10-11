package model;

import java.time.*;
import java.time.format.DateTimeFormatter;

// An Entry contains information about the amount of money spent in an expenditure (in dollars), the category the
// expenditure belongs to, the date of the expenditure, and a description about the expenditure.
public class Entry {
    private double amount; //amount of money spent
    private Category category; //categories
    private LocalDate date; //
    private String description;

    //REQUIRES: amt > 0, date in yyyy/mm/dd format
    //EFFECTS: Construct an Entry with amount spent, a date, a category and a description
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
            case "food":
                this.category = Category.FOOD;
                break;
            case "rent":
                this.category = Category.RENT;
                break;
            case "supplies":
                this.category = Category.SUPPLIES;
                break;
            case "bills":
                this.category = Category.BILLS;
                break;
            case "others":
                this.category = Category.OTHERS;
                break;
            default:
                break;
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