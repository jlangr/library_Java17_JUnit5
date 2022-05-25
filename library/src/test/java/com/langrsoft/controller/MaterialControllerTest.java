package com.langrsoft.controller;

import com.langrsoft.external.MaterialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testutil.ControllerTestUtil.resultContent;

@ExtendWith(MockitoExtension.class)
class MaterialControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    MaterialController materialController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(materialController).build();
    }

    @Test
    void getMaterialType() throws Exception {
        var result = mockMvc.perform(get("/material/BOOK"))
                .andReturn();

        var materialType = resultContent(result, MaterialType.class);
        assertSame(MaterialType.BOOK, materialType);
        assertThat(materialType.getDailyFine(), equalTo(MaterialType.BOOK.getDailyFine()));
    }

    @Test
    void getMaterialTypeReturns404OnInvalidMaterialType() throws Exception {
        var result = mockMvc.perform(get("/material/BADTYPE"))
                .andExpect(status().isNotFound());
    }
}