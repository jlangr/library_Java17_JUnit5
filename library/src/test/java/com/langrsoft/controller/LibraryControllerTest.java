package com.langrsoft.controller;

import com.langrsoft.external.Material;
import com.langrsoft.service.library.LibraryData;
import com.langrsoft.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Date;

import static java.util.Calendar.MARCH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testutil.ControllerTestUtil.postAsJson;
import static testutil.ControllerTestUtil.resultContent;

@ExtendWith(MockitoExtension.class)
class LibraryControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    LibraryController libraryController;

    @Mock
    LibraryData libraryData;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(libraryController).build();
    }

    @Test
    void clearsAllData() throws Exception {
        mockMvc.perform(post("/clear"))
                .andExpect(status().isOk());

        verify(libraryData).deleteBranchesHoldingsPatrons();
    }

    @Nested
    class LocalClassificationService {
        @Test
        void addRetrieveSucceeds() throws Exception {
            mockMvc.perform(post("/use_local_classification"));

            var materialRequest = new MaterialRequest();
            materialRequest.setAuthor("Kafka");
            materialRequest.setFormat("BOOK");
            materialRequest.setClassification("QB234");
            materialRequest.setSourceId("QB234");
            mockMvc.perform(postAsJson("/materials", materialRequest));

            var result = mockMvc.perform(get("/retrieveMaterial/QB234"))
                    .andExpect(status().isOk())
                    .andReturn();

            var retrieved = resultContent(result, Material.class);
            assertThat(retrieved.getAuthor(), equalTo("Kafka"));
        }
    }

    @Nested
    class CurrentDate {
        @Test
        void setCurrentDateFixesCurrentDateValue() throws Exception {
            var date = DateUtil.create(2025, MARCH, 15);

            mockMvc.perform(postAsJson("/current_date", date));

            assertThat(retrieveCurrentDate(), equalTo(date));
        }

        @Test
        void resetCurrentDateClearsFixedDate() throws Exception {
            mockMvc.perform(postAsJson("/current_date", DateUtil.create(2025, MARCH, 15)));

            mockMvc.perform(post("/reset_current_date"));

            assertThat(retrieveCurrentDate(), equalTo(DateUtil.toDate(LocalDate.now())));
        }

        private Date retrieveCurrentDate() throws Exception {
            var result = mockMvc.perform(get("/current_date")).andReturn();
            return resultContent(result, Date.class);
        }
    }

}