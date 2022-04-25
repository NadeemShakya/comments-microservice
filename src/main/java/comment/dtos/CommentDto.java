package comment.dtos;

import io.micronaut.core.annotation.Introspected;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Introspected
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer id;

    @NotBlank(message = "Comment is required.")
    private String comment;

    @NotBlank(message = "Module name is required.")
    private String moduleName;

    @NotBlank(message = "Entity name is required.")
    private String entityName;

    @NotNull(message = "Entity Id is required.")
    private Integer entityId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Integer createdBy;

    private Integer updatedBy;
}
