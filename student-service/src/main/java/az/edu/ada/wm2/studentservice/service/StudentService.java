package az.edu.ada.wm2.studentservice.service;

import az.edu.ada.wm2.studentservice.exception.StudentNotFoundException;
import az.edu.ada.wm2.studentservice.model.dto.StudentRequestDto;
import az.edu.ada.wm2.studentservice.model.dto.StudentResponseDto;
import az.edu.ada.wm2.studentservice.model.entity.Student;
import az.edu.ada.wm2.studentservice.repository.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        Student student = Student.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .age(requestDto.getAge())
                .build();

        Student savedStudent = studentRepository.save(student);
        return toResponseDto(savedStudent);
    }

    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public StudentResponseDto getStudentById(Long id) {
        Student student = findStudentOrThrow(id);
        return toResponseDto(student);
    }

    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        Student existingStudent = findStudentOrThrow(id);

        existingStudent.setFirstName(requestDto.getFirstName());
        existingStudent.setLastName(requestDto.getLastName());
        existingStudent.setEmail(requestDto.getEmail());
        existingStudent.setAge(requestDto.getAge());

        Student updatedStudent = studentRepository.save(existingStudent);
        return toResponseDto(updatedStudent);
    }

    public void deleteStudent(Long id) {
        Student student = findStudentOrThrow(id);
        studentRepository.delete(student);
    }

    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    private StudentResponseDto toResponseDto(Student student) {
        return new StudentResponseDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge()
        );
    }
}
