package comment.dtos;

import io.micronaut.core.annotation.Introspected;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@Introspected
@AllArgsConstructor
@NoArgsConstructor
public class CommentFilterParameters {
    private Integer id;

    private String createdAt;

    private Integer createdBy;

    private String updatedAt;

    private Integer updatedBy;

    private String comment;

    private String moduleName;

    private String entityName;

    private Integer entityId;

    private Integer pageSize;

    private String sortBy;

    private String sortOrder;

    private Integer pageNumber;
}
