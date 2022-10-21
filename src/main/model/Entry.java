package model;

import exceptions.NegativeAmountException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


// An Entry contains information about the amount of money spent in an expenditure (in dollars), the category the
// expenditure belongs to, the date of the expenditure, and a description about the expenditure.
public class Entry {
    private double amount; //amount of money spent
    private Category category; //categories
    private LocalDate date; //
    private String description;

    // EFFECTS: Construct an Entry with amount spent, a date, a category and a description. If date is invalid, defaults
    //          to 2000-01-01. If amt <= 0, throw NegativeAmountException
    public Entry(double amt, String date, Category category, String description)
            throws NegativeAmountException, DateTimeParseException {
        if (amt <= 0) {
            throw new NegativeAmountException();
        }
        this.amount = amt;
        this.category = category;
        try {
            this.date = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            this.date = LocalDate.parse("2000-01-01");
        }
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
    public void setAmount(double amount) throws NegativeAmountException {
        if (amount <= 0) {
            throw new NegativeAmountException();
        }
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
            default:
                this.category = Category.OTHERS;
                break;
        }
    }

    // REQUIRES: Date must be in yyyy/mm/dd format
    // EFFECTS: Change a date by parsing a string, if date is invalid, defaults to 2000-01-01
    public void setDate(String date) {
        try {
            this.date = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            this.date = LocalDate.parse("2000-01-01");
        }
    }

    // REQUIRES: Year month day must for a valid date
    // MODIFIES: this
    // EFFECTS: Change a date by entering year, month, day
    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    // REQUIRES: Month must be in [1, 12]
    // MODIFIES: this
    // EFFECTS: Change a date by entering year and month
    public void setDate(int year, int month) {
        this.date = LocalDate.of(year, month, date.getDayOfMonth());
    }

    // MODIFIES: this
    // EFFECTS: Change a date by entering year only
    public void setDate(int year) {
        this.date = LocalDate.of(year, date.getMonth(), date.getDayOfMonth());
    }


    public void setDescription(String description) {
        this.description = description;
    }
}