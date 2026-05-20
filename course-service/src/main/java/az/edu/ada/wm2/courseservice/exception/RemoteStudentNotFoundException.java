package az.edu.ada.wm2.courseservice.exception;

public class RemoteStudentNotFoundException extends RuntimeException {

    public RemoteStudentNotFoundException(Long id) {
        super("Student not found in student-service with id: " + id);
    }
}
