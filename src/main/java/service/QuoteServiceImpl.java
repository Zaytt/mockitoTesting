package service;

import model.Quote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuoteServiceImpl implements QuoteService {

    private static final AtomicInteger counter = new AtomicInteger();
    static List<Quote> quotes = new ArrayList<Quote>(
            Arrays.asList(
                    new Quote("NFLX", 201.5, 202.34, 199.23, 200),
                    new Quote("AAPL", 170.1, 172.5, 167.11, 123),
                    new Quote("TSLA", 245.48, 245.01, 246.03, 321),
                    new Quote("AMZN", 1559.5, 1578, 1534.72, 342),
                    new Quote("FB", 198.2, 200.78, 197.58, 971)));


    @Override
    public Quote getQuote(String ticker) {

        int index = indexOf(ticker);
        if(index >= 0)
            return quotes.get(index);
        else
            return null;
    }

    @Override
    public List<Quote> getAllQuotes() {
        return quotes;
    }

    @Override
    public void create(Quote quote) {
        quotes.add(quote);
    }

    @Override
    public void update(Quote quote) {
        int index = indexOf(quote.getSymbol());
        if(index >= 0){
            quotes.set(index, quote);
        }
    }

    @Override
    public void delete(String symbol) {
        int index = indexOf(symbol);
        if(index >= 0){
            quotes.remove(index);
        }

    }

    @Override
    public boolean exists(String symbol) {
        boolean found = false;
        for (Quote q: quotes) {
            if(q.getSymbol().compareTo(symbol) == 0){
                found = true;
                break;
            }
        }

        return found;
    }

    public int indexOf(String ticker){
        int index = 0;
        boolean found = false;
        for (Quote q: quotes) {
            if(q.getSymbol().compareTo(ticker) == 0){
                found = true;
                break;
            }
            index++;
        }
        if (found)
            return index;
        else
            return -1;
    }
}
