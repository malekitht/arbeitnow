import com.arbeitnow.arbeitnow.controller.JobPostController;
import com.arbeitnow.arbeitnow.entity.JobPost;
import com.arbeitnow.arbeitnow.service.JobPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

public class JobControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JobPostService jobService;

    @InjectMocks
    private JobPostController jobController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobController).build();
    }

    @Test
    void testCreateJobPost() throws Exception {
        JobPost job = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);

        when(jobService.createJobPost(any(JobPost.class))).thenReturn(job);

        mockMvc.perform(post("/api/job")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"slug\": \"slug1\", \"company_name\": \"Company1\", \"title\": \"Title1\", \"description\": \"Description1\", \"remote\": false, \"location\": \"Location1\", \"created_at\": 1627845600 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    void testGetJobPostById() throws Exception {
        JobPost job = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);

        when(jobService.getJobPostById(1)).thenReturn(Optional.of(job));

        mockMvc.perform(get("/api/job/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title1"));
    }

    @Test
    void testUpdateJobPost() throws Exception {
        JobPost updatedJob = new JobPost("slug1", "Company1", "UpdatedTitle", "UpdatedDescription", false, "Location1", 1627845600L);

        when(jobService.updateJobPost(eq(1), any(JobPost.class))).thenReturn(updatedJob);

        mockMvc.perform(put("/api/job/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"slug\": \"slug1\", \"company_name\": \"Company1\", \"title\": \"UpdatedTitle\", \"description\": \"UpdatedDescription\", \"remote\": false, \"location\": \"Location1\", \"created_at\": 1627845600 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("UpdatedTitle"));
    }

    @Test
    void testDeleteJobPostById() throws Exception {
        doNothing().when(jobService).deleteJobPostById(1);

        mockMvc.perform(delete("/api/job/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllJobs() throws Exception {
        JobPost job1 = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);
        JobPost job2 = new JobPost("slug2", "Company2", "Title2", "Description2", false, "Location2", 1627845600L);
        List<JobPost> jobs = Arrays.asList(job1, job2);

        when(jobService.getAllJobPosts()).thenReturn(jobs);

        mockMvc.perform(get("/api/job"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].slug").value("slug1"))
                .andExpect(jsonPath("$[1].slug").value("slug2"));
    }

    @Test
    void testGetTop10CitiesByJobCount() throws Exception {
        JobPost job1 = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);
        JobPost job2 = new JobPost("slug2", "Company2", "Title2", "Description2", false, "Location1", 1627845600L);
        JobPost job3 = new JobPost("slug3", "Company3", "Title3", "Description3", false, "Location2", 1627845600L);

        List<Map.Entry<String, Long>> topCities = jobService.getTop10CitiesByJobCount();

        when(jobService.getTop10CitiesByJobCount()).thenReturn(topCities);

        mockMvc.perform(get("/api/job/top10cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetCityStatistics() throws Exception {
        JobPost job1 = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);
        JobPost job2 = new JobPost("slug2", "Company2", "Title2", "Description2", false, "Location1", 1627845600L);
        JobPost job3 = new JobPost("slug3", "Company3", "Title3", "Description3", false, "Location2", 1627845600L);

        when(jobService.getCityStatistics()).thenReturn(Map.of(
                "Location1", 2L,
                "Location2", 1L
        ));

        mockMvc.perform(get("/api/job/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.Location1").value(2))
                .andExpect(jsonPath("$.Location2").value(1));
    }
}