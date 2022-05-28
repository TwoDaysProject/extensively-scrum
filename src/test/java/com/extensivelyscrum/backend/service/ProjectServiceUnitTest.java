package com.extensivelyscrum.backend.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectServiceUnitTest {

    private ProjectService projectService;

    @BeforeAll
    public void setUp() {
        projectService = new ProjectService(null, null);
    }


    @Test
    public void testGetNextTag() throws Exception { // Unit test of the function getNextTag

        // given:
        String projectName1 = "test something-hhh";
        String projectName2 = "test";
        int tagCounter = 0;

        // when
        String tag1 = projectService.getNextTag(projectName1, tagCounter);
        String tag2 = projectService.getNextTag(projectName2, tagCounter + 1);

        // then
        assertEquals("TSH-1", tag1);
        assertEquals("TEST-2", tag2);
    }
}