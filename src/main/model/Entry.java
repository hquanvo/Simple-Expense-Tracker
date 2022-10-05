package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Entry is the atomic element of BudgetList
public class Entry {
    private double amount; //amount of money spent
    private Category category; //categories
    private Date date; //
    private String description;

    //EFFECTS: Construct an Entry with 0 amount spent, default type and date, empty description
    public Entry() {
        amount = 0;
        category = Category.DEFAULT;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        description = "";
    }

    //getters
    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
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

    public void setDate(String date) {
        this.date.parse(date);
        System.out.println("The date has been set to " + date);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
