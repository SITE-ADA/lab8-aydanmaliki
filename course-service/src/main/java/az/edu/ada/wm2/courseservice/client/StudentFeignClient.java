package az.edu.ada.wm2.courseservice.client;

import az.edu.ada.wm2.courseservice.config.FeignClientConfig;
import az.edu.ada.wm2.courseservice.model.dto.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "studentServiceClient",
        url = "${student.service.base-url}",
        configuration = FeignClientConfig.class
)
public interface StudentFeignClient {

    @GetMapping("/api/v1/students/{id}")
    StudentDto getStudentById(@PathVariable("id") Long id);
}
