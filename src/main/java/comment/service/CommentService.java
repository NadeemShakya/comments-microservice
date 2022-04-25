package comment.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import jakarta.inject.Inject;

import comment.util.BaseUtil;
import comment.dtos.CommentDto;
import comment.model.CommentEntity;
import comment.mapper.CommentMapper;
import comment.constant.BaseConstant;
import comment.dtos.CommentFilterParameters;
import comment.repository.CommentRepository;
import comment.error.exception.NotFoundException;
import comment.repository.AbstractCommentRepository;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;

    @Inject
    private CommentMapper commentMapper;

    @Inject
    private AbstractCommentRepository abstractCommentRepository;

    /**
     * Delete a comment.
     *
     * @param id The comment id.
     * @return String The success message.
     * @throws NotFoundException if the comment with given id doesn't exist.
     */
    public String delete(Integer id) {
        CommentEntity commentEntity = findOne(id);

        commentEntity.setIsDeleted(true);
        commentRepository.update(commentEntity);

        return BaseUtil.getJsonObject(BaseConstant.MESSAGE, BaseConstant.SUCCESSFULLY_DELETED).toString();
    }

    /**
     * Find all comments supported with filter parameters.
     *
     * @return List<CommentDto> Response with the list of comments.
     */
    @Transactional
    public List<CommentDto> findAll(CommentFilterParameters commentFilterParameters) {
        return abstractCommentRepository.findAll(commentFilterParameters);
    }

    /**
     * Find the comment by its id.
     *
     * @param id The comment's id.
     * @return CommentDto The comment.
     * @throws NotFoundException if the comment with given id doesn't exist.
     */
    public CommentDto findById(Integer id) {
        CommentEntity commentEntity = findOne(id);

        return commentMapper.toDto(commentEntity);
    }

    /**
     * Generic method to find the comment by its id.
     *
     * @param id The comment's id.
     * @return CommentEntity The desired comment.
     * @throws NotFoundException If the comment with given id doesn't exist.
     */
    public CommentEntity findOne(Integer id) {
        Optional<CommentEntity> optionalCommentEntity  = commentRepository.findById(id);

        if(optionalCommentEntity.isEmpty()) {
            throw new NotFoundException("Comment not found.");
        }

        return optionalCommentEntity.get();
    }

    /**
     * Save a comment.
     *
     * @param commentDto DTO for the comment.
     * @return CommentDto The saved comment.
     */
    public CommentDto save(CommentDto commentDto) {
        var commentEntity = CommentEntity.builder()
                .comment(commentDto.getComment())
                .moduleName(commentDto.getModuleName())
                .entityName(commentDto.getEntityName())
                .entityId(commentDto.getEntityId())
                .build();

        commentRepository.save(commentEntity);

        return commentMapper.toDto(commentEntity);
    }

    /**
     * Update a comment.
     *
     * @param id The comment id.
     * @param commentDto DTO for the comment with update information.
     * @return CommentDto The updated comment.
     * @throws NotFoundException if the comment with given id doesn't exist.
     */
    public CommentDto update(Integer id, CommentDto commentDto) {
        CommentEntity commentEntity = findOne(id);

        commentEntity.setComment(commentDto.getComment());
        commentRepository.update(commentEntity);

        return commentMapper.toDto(commentEntity);
    }
}
