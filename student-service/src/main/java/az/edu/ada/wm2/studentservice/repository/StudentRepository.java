package az.edu.ada.wm2.studentservice.repository;

import az.edu.ada.wm2.studentservice.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
