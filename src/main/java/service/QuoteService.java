package service;

import model.Quote;

import java.util.List;

public interface QuoteService {
    List<Quote> getAllQuotes();

    Quote getQuote(String ticker);

    void create(Quote quote);

    void update(Quote quote);

    void delete(String symbol);

    boolean exists(String symbol);

    int indexOf(String ticker);
}
