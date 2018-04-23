package controller;

import model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import service.QuoteService;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final Logger LOG = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    QuoteService quoteService;

    // =========================================== Get All Quotes ==========================================

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Quote>> getAll() {
        LOG.info("getting all users with offset: {}, and count: {}");
        List<Quote> users = quoteService.getAllQuotes();

        if (users == null || users.isEmpty()){
            LOG.info("No quotes available");
            return new ResponseEntity<List<Quote>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Quote>>(users, HttpStatus.OK);
    }

    // =========================================== Get Quote By Ticker =========================================

    @RequestMapping(value = "{ticker}", method = RequestMethod.GET)
    public ResponseEntity<Quote> get(@PathVariable("ticker") String ticker){
        LOG.info("Getting quote from: {}", ticker);
        Quote quote = quoteService.getQuote(ticker);

        if (quote == null){
            LOG.info("Quote for ticker {} not found", ticker);
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Quote>(quote, HttpStatus.OK);
    }

    // =========================================== Create Quote ========================================

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Quote quote, UriComponentsBuilder ucBuilder){
        LOG.info("creating new quote: {}", quote);

        if (quoteService.exists(quote.getSymbol())){
            LOG.info("A quote for " + quote.getSymbol() + " already exists.");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        quoteService.create(quote);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/quote/{ticker}").buildAndExpand(quote.getSymbol()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // =========================================== Update Existing Quote ===================================

    @RequestMapping(value = "{ticker}", method = RequestMethod.PUT)
    public ResponseEntity<Quote> update(@PathVariable String ticker, @RequestBody Quote quote){
        LOG.info("Udating quote: {}", quote);
        Quote currentQuote = quoteService.getQuote(ticker);

        if (currentQuote == null){
            LOG.info("Quote for {} not found", ticker);
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND);
        }

        currentQuote.setSymbol(quote.getSymbol());
        currentQuote.setDelayedPrice(quote.getDelayedPrice());
        currentQuote.setDelayedSize(quote.getDelayedSize());
        currentQuote.setHigh(quote.getHigh());
        currentQuote.setLow(quote.getLow());

        quoteService.update(quote);
        return new ResponseEntity<Quote>(currentQuote, HttpStatus.OK);
    }

    // =========================================== Delete User ============================================

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("ticker") String ticker){
        LOG.info("deleting quote for {}", ticker);
        Quote quote = quoteService.getQuote(ticker);

        if (quote == null){
            LOG.info("Unable to delete. Quote for {} not found", ticker);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        quoteService.delete(ticker);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
