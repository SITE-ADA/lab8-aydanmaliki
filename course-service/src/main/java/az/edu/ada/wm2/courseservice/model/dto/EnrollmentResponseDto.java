package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDto {

    @Schema(description = "Enrollment id", example = "10")
    private Long enrollmentId;

    @Schema(description = "Course id", example = "1")
    private Long courseId;

    @Schema(description = "Student id", example = "15")
    private Long studentId;

    @Schema(description = "Date when the student was enrolled", example = "2026-05-20")
    private LocalDate enrollmentDate;

    @Schema(description = "Operation result message", example = "Student enrolled successfully.")
    private String message;
}