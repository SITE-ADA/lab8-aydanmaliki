package az.edu.ada.wm2.courseservice.exception;

public class PrerequisiteNotCompletedException extends RuntimeException {

    public PrerequisiteNotCompletedException(Long studentId, Long prerequisiteCourseId) {
        super("Student " + studentId + " has not completed prerequisite course " + prerequisiteCourseId);
    }
}