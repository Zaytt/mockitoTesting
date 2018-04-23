package controller;

import filter.CORSFilter;
import model.Quote;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.QuoteService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuoteService quoteService;

    @InjectMocks
    private QuoteController quoteController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(quoteController)
                .addFilters(new CORSFilter())
                .build();
    }

    @Test
    public void test_get_quote_success() throws Exception{
        Quote quote = new Quote("NFLX", 201.5, 202.34, 199.23, 200);
        when(quoteService.getQuote("NFLX")).thenReturn(quote);

        mockMvc.perform(get("/quote/{ticker}", "NFLX"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.symbol", is("NFLX")))
                .andExpect(jsonPath("$.delayedPrice", is(201.5)))
                .andExpect(jsonPath("$.high", is(202.34)))
                .andExpect(jsonPath("$.low", is(199.23)))
                .andExpect(jsonPath("$.delayedSize", is(200)));

    }

//    @Test
//    public void create() {
//    }
//
//    @Test
//    public void update() {
//    }
//
//    @Test
//    public void delete() {
//    }
}