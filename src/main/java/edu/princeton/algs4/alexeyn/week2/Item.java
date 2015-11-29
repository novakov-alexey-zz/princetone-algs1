package edu.princeton.algs4.alexeyn.week2;

/**
 * @author Alexey Novakov
 */
public class Item {
    private int intValue;
    private String textValue;

    public Item() {
    }

    public Item(String textValue) {
        this.textValue = textValue;
    }

    public Item(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    @Override
    public String toString() {
        return String.valueOf(intValue);
    }
}
