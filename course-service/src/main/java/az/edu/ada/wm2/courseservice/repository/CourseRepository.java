package az.edu.ada.wm2.courseservice.repository;

import az.edu.ada.wm2.courseservice.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
