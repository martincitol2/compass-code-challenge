package com.compass.code.challenge.controller;

import com.compass.code.challenge.service.CompassCodeChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompassCodeChallengeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompassCodeChallengeService compassCodeChallengeService;

    @InjectMocks
    private CompassCodeChallengeController compassCodeChallengeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(compassCodeChallengeController).build();
    }

    @Test
    void testProcess() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, "some,data,to,upload\nanother,row".getBytes());

        List<List<String>> expectedResponse = Arrays.asList(
            Arrays.asList("some", "data", "to", "upload"),
            Arrays.asList("another", "row")
        );

        when(compassCodeChallengeService.process(file)).thenReturn(expectedResponse);

        mockMvc.perform(multipart("/compass-code-challenge/process-duplicates").file(file))
            .andExpect(status().isOk());
    }
}