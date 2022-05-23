package com.langrsoft.controller;

import com.langrsoft.domain.Patron;
import com.langrsoft.service.library.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testutil.ControllerTestUtil.postAsJson;
import static testutil.ControllerTestUtil.resultContent;

@ExtendWith(MockitoExtension.class)
class PatronControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    PatronController patronController;
    @Mock
    PatronService patronService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    void addDelegatesToService() throws Exception {
        var request = new PatronRequest();
        request.setName("a name");
        when(patronService.add("a name")).thenReturn("anID");

        var result = mockMvc.perform(postAsJson("/patrons", request))
                .andExpect(status().isOk())
                .andReturn();

        var id = result.getResponse().getContentAsString();
        assertThat(id, equalTo("anID"));
    }


    @Test
    void retrieveAll() throws Exception {
        var patron1 = new Patron("1", "A");
        var patron2 = new Patron("2", "B");
        when (patronService.allPatrons())
                .thenReturn(List.of(patron1, patron2));

        var results = mockMvc.perform(get("/patrons"))
                .andReturn();

        var retrieved = resultContent(results, PatronRequest[].class);
        var names = Arrays.stream(retrieved)
                        .map(PatronRequest::getName).collect(toList());
        assertThat(names, equalTo(List.of("A", "B")));
    }

    @Test
    public void retrieve() throws Exception {
        when(patronService.find("42")).thenReturn(new Patron("xyz"));

        var results = mockMvc.perform(get("/patrons/42"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(resultContent(results, PatronRequest.class).getName(), equalTo("xyz"));
    }
}