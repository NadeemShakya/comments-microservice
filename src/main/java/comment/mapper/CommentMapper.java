package comment.mapper;

import comment.dtos.CommentDto;
import comment.model.CommentEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface CommentMapper {
    CommentDto toDto(CommentEntity commentEntity);

    Iterable<CommentDto> toDto(Iterable<CommentEntity> commentEntities);
}
