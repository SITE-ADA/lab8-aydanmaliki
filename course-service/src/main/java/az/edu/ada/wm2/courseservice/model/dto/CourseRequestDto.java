package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @Schema(description = "Course title", example = "Data Structures")
    @NotBlank(message = "Title is required")
    private String title;

    @Schema(description = "Course code", example = "CS201")
    @NotBlank(message = "Code is required")
    private String code;

    @Schema(description = "Credit count", example = "4")
    @Positive(message = "Credits must be positive")
    private Integer credits;

    @Schema(description = "Optional prerequisite course id", example = "1")
    private Long prerequisiteCourseId;
}