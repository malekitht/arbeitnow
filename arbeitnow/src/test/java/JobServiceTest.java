
import com.arbeitnow.arbeitnow.entity.JobPost;
import com.arbeitnow.arbeitnow.repository.JobPostRepository;
import com.arbeitnow.arbeitnow.service.JobPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    @Mock
    private JobPostRepository jobRepository;

    @InjectMocks
    private JobPostService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateJobPost() {
        JobPost job = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);

        when(jobRepository.save(job)).thenReturn(job);

        JobPost result = jobService.createJobPost(job);
        assertNotNull(result);
        assertEquals(job, result);

        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testGetJobPostById() {
        JobPost job = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);

        when(jobRepository.findById(1)).thenReturn(Optional.of(job));

        Optional<JobPost> result = jobService.getJobPostById(1);
        assertTrue(result.isPresent());
        assertEquals(job, result.get());

        verify(jobRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteJobPostById() {
        Integer id = 1;

        doNothing().when(jobRepository).deleteById(id);

        jobService.deleteJobPostById(id);

        verify(jobRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllJobs() {
        JobPost job1 = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);
        JobPost job2 = new JobPost("slug2", "Company2", "Title2", "Description2", false, "Location2", 1627845600L);
        List<JobPost> jobs = Arrays.asList(job1, job2);

        when(jobRepository.findAll()).thenReturn(jobs);

        List<JobPost> result = jobService.getAllJobPosts();
        assertEquals(jobs, result);
    }

    @Test
    void testGetTop10CitiesByJobCount() {
        JobPost job1 = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);
        JobPost job2 = new JobPost("slug2", "Company2", "Title2", "Description2", false, "Location1", 1627845600L);
        JobPost job3 = new JobPost("slug3", "Company3", "Title3", "Description3", false, "Location2", 1627845600L);
        JobPost job4 = new JobPost("slug4", "Company4", "Title4", "Description4", false, "Location3", 1627845600L);
        JobPost job5 = new JobPost("slug5", "Company5", "Title5", "Description5", false, "Location3", 1627845600L);

        List<JobPost> jobs = Arrays.asList(job1, job2, job3, job4, job5);

        when(jobRepository.findAll()).thenReturn(jobs);

        Map<String, Long> expected = jobs.stream()
                .collect(Collectors.groupingBy(JobPost::getLocation, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Map.Entry<String, Long>> result = jobService.getTop10CitiesByJobCount();
        assertEquals(expected, result.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Test
    void testGetCityStatistics() {
        JobPost job1 = new JobPost("slug1", "Company1", "Title1", "Description1", false, "Location1", 1627845600L);
        JobPost job2 = new JobPost("slug2", "Company2", "Title2", "Description2", false, "Location1", 1627845600L);
        JobPost job3 = new JobPost("slug3", "Company3", "Title3", "Description3", false, "Location2", 1627845600L);

        List<JobPost> jobs = Arrays.asList(job1, job2, job3);

        when(jobRepository.findAll()).thenReturn(jobs);

        Map<String, Long> expected = jobs.stream()
                .collect(Collectors.groupingBy(JobPost::getLocation, Collectors.counting()));

        Map<String, Long> result = jobService.getCityStatistics();
        assertEquals(expected, result);
    }
}