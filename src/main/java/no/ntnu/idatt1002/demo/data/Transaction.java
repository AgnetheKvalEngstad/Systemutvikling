package no.ntnu.idatt1002.demo.data;

import java.util.Date;

import java.util.Calendar;
//Date format to format dates

//TODO make user tests for all in data

//NOTE Since this class is immutable, deep copies should not be necessary. If this is wrong
//all files copying transactions need to be changed

/**
 * Abstract class that expense/income inherits from.
 */
public abstract class Transaction {
    private String name;
    private String notes;
    //TODO make into string
    private String date;
    private double amount;

    //Constructor in here?
    public Transaction(String name, String notes, String date, double amount){
        this.name = name;
        this.notes = notes;
        //TODO deep copy, or solved by being private?
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }

    public double getAmount(){
        return amount;
    }
}
