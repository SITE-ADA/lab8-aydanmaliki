package az.edu.ada.wm2.courseservice.exception;

public class EnrollmentAlreadyExistsException extends RuntimeException {

    public EnrollmentAlreadyExistsException(Long courseId, Long studentId) {
        super("Student " + studentId + " is already enrolled in course " + courseId);
    }
}
