package az.edu.ada.wm2.courseservice.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    @Schema(description = "Course id", example = "1")
    private Long id;

    @Schema(description = "Course title", example = "Data Structures")
    private String title;

    @Schema(description = "Course code", example = "CS201")
    private String code;

    @Schema(description = "Credit count", example = "4")
    private Integer credits;
}
