package model;

public class Quote {
    private String symbol;
    private double delayedPrice;
    private double high;
    private double low;
    private int delayedSize;


    public Quote(String symbol, double delayedPrice, double high, double low, int delayedSize) {
        this.symbol = symbol;
        this.delayedPrice = delayedPrice;
        this.high = high;
        this.low = low;
        this.delayedSize = delayedSize;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getDelayedPrice() {
        return delayedPrice;
    }

    public void setDelayedPrice(double delayedPrice) {
        this.delayedPrice = delayedPrice;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public int getDelayedSize() {
        return delayedSize;
    }

    public void setDelayedSize(int delayedSize) {
        this.delayedSize = delayedSize;
    }
}
