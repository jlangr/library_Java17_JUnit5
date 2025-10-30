package com.langrsoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.langrsoft.domain.Branch;
import com.langrsoft.service.library.BranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testutil.ControllerTestUtil.contentAsString;
import static testutil.ControllerTestUtil.resultContent;

@ExtendWith(MockitoExtension.class)
class BranchControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    BranchService branchService;
    @InjectMocks
    BranchController branchController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
    }

    @Test
    void add() throws Exception {
        var branchRequest1 = new BranchRequest(new Branch("branch1name"));
        when(branchService.add("branch1name")).thenReturn("newId1");

        var result = mockMvc.perform(post("/branches")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(branchRequest1)))
           .andExpect(status().isOk())
           .andReturn();

        assertThat(contentAsString(result)).isEqualTo("newId1");
    }

    @Test
    void retrieveAll() throws Exception {
        var branch1 = new Branch("b1", "branch A");
        var branch2 = new Branch("b2", "branch B");
        when(branchService.allBranches())
           .thenReturn(asList(branch1, branch2));

        var result = mockMvc.perform(get("/branches"))
           .andExpect(status().isOk())
           .andReturn();

        assertThat(resultContent(result, BranchRequest[].class))
           .containsExactly(
              new BranchRequest(branch1),
              new BranchRequest(branch2));
    }
}
