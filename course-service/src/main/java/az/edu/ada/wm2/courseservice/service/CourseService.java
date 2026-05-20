package az.edu.ada.wm2.courseservice.service;

import az.edu.ada.wm2.courseservice.client.StudentFeignClient;
import az.edu.ada.wm2.courseservice.exception.*;
import az.edu.ada.wm2.courseservice.model.dto.*;
import az.edu.ada.wm2.courseservice.model.entity.Course;
import az.edu.ada.wm2.courseservice.model.entity.Enrollment;
import az.edu.ada.wm2.courseservice.repository.CourseRepository;
import az.edu.ada.wm2.courseservice.repository.EnrollmentRepository;
import feign.FeignException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentFeignClient studentFeignClient;
    private final RestTemplate restTemplate;

    @Value("${student.service.base-url}")
    private String studentServiceBaseUrl;

    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course course = Course.builder()
                .title(requestDto.getTitle())
                .code(requestDto.getCode())
                .credits(requestDto.getCredits())
                .prerequisiteCourseId(requestDto.getPrerequisiteCourseId())
                .build();

        return toCourseResponseDto(courseRepository.save(course));
    }

    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toCourseResponseDto)
                .toList();
    }

    public CourseResponseDto getCourseById(Long id) {
        return toCourseResponseDto(findCourseOrThrow(id));
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto) {
        Course course = findCourseOrThrow(id);

        course.setTitle(requestDto.getTitle());
        course.setCode(requestDto.getCode());
        course.setCredits(requestDto.getCredits());
        course.setPrerequisiteCourseId(requestDto.getPrerequisiteCourseId());

        return toCourseResponseDto(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        courseRepository.delete(findCourseOrThrow(id));
    }

    public EnrollmentResponseDto enrollStudent(Long courseId, Long studentId) {
        Course course = findCourseOrThrow(courseId);

        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new EnrollmentAlreadyExistsException(courseId, studentId);
        }

        validateStudentWithFeign(studentId);
        validatePrerequisite(course, studentId);

        Enrollment enrollment = Enrollment.builder()
                .courseId(courseId)
                .studentId(studentId)
                .enrollmentDate(LocalDate.now())
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);

        return new EnrollmentResponseDto(
                saved.getId(),
                saved.getCourseId(),
                saved.getStudentId(),
                saved.getEnrollmentDate(),
                "Student enrolled successfully."
        );
    }

    public CourseStudentsResponseDto getCourseStudents(Long courseId) {
        Course course = findCourseOrThrow(courseId);

        List<StudentDto> students = enrollmentRepository.findByCourseId(courseId).stream()
                .map(Enrollment::getStudentId)
                .map(this::fetchStudentWithRestTemplate)
                .toList();

        return new CourseStudentsResponseDto(course.getId(), course.getTitle(), students);
    }

    public List<CourseResponseDto> getCoursesByStudentName(String studentName) {
        String search = studentName.trim().toLowerCase();

        StudentDto matchedStudent = fetchAllStudentsWithRestTemplate().stream()
                .filter(student -> {
                    String fullName = (student.getFirstName() + " " + student.getLastName()).toLowerCase();
                    return student.getFirstName().equalsIgnoreCase(search)
                            || student.getLastName().equalsIgnoreCase(search)
                            || fullName.equals(search);
                })
                .findFirst()
                .orElseThrow(() -> new StudentServiceCommunicationException("Student not found with name: " + studentName));

        return enrollmentRepository.findByStudentId(matchedStudent.getId()).stream()
                .map(Enrollment::getCourseId)
                .map(this::findCourseOrThrow)
                .map(this::toCourseResponseDto)
                .toList();
    }

    private void validatePrerequisite(Course course, Long studentId) {
        Long prerequisiteCourseId = course.getPrerequisiteCourseId();

        if (prerequisiteCourseId == null) {
            return;
        }

        findCourseOrThrow(prerequisiteCourseId);

        boolean completed = enrollmentRepository.existsByCourseIdAndStudentId(prerequisiteCourseId, studentId);

        if (!completed) {
            throw new PrerequisiteNotCompletedException(studentId, prerequisiteCourseId);
        }
    }

    private void validateStudentWithFeign(Long studentId) {
        try {
            studentFeignClient.getStudentById(studentId);
        } catch (FeignException.NotFound ex) {
            throw new RemoteStudentNotFoundException(studentId);
        } catch (FeignException ex) {
            throw new StudentServiceCommunicationException("Could not validate student-service response.");
        }
    }

    private StudentDto fetchStudentWithRestTemplate(Long studentId) {
        String url = studentServiceBaseUrl + "/api/v1/students/" + studentId;

        try {
            return restTemplate.getForObject(url, StudentDto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RemoteStudentNotFoundException(studentId);
        } catch (RestClientException ex) {
            throw new StudentServiceCommunicationException("Could not fetch student details from student-service.");
        }
    }

    private List<StudentDto> fetchAllStudentsWithRestTemplate() {
        String url = studentServiceBaseUrl + "/api/v1/students";

        try {
            StudentDto[] students = restTemplate.getForObject(url, StudentDto[].class);
            return students == null ? List.of() : List.of(students);
        } catch (RestClientException ex) {
            throw new StudentServiceCommunicationException("Could not fetch students from student-service.");
        }
    }

    private Course findCourseOrThrow(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    private CourseResponseDto toCourseResponseDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getTitle(),
                course.getCode(),
                course.getCredits(),
                course.getPrerequisiteCourseId()
        );
    }
}