package comment.repository;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaQuery;

import jakarta.inject.Inject;

import comment.dtos.CommentDto;
import comment.model.CommentEntity;
import comment.mapper.CommentMapper;
import comment.dtos.CommentFilterParameters;
import comment.error.exception.BadRequestException;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public abstract class AbstractCommentRepository implements CrudRepository<CommentEntity, Integer> {

    @Inject
    private CommentMapper commentMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    public AbstractCommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Find all comments supported with filter parameters.
     *
     * @return List<CommentDto> Response with the list of comments.
     */
    @Transactional
    public List<CommentDto> findAll(CommentFilterParameters commentFilterParameters) {
        var builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentEntity> query = builder.createQuery(CommentEntity.class);
        Root<CommentEntity> root = query.from(CommentEntity.class);

        var predicates = new ArrayList<Predicate>();

        // Filter by id.
        if (commentFilterParameters != null && commentFilterParameters.getId() != null) {
            predicates.add(builder.equal(root.get("id"), commentFilterParameters.getId()));
        }

        // Filter by createdBy.
        if (commentFilterParameters != null && commentFilterParameters.getCreatedBy() != null) {
            predicates.add(builder.equal(root.get("createdBy"), commentFilterParameters.getCreatedBy()));
        }

        // Filter by updatedBy.
        if (commentFilterParameters != null && commentFilterParameters.getUpdatedBy() != null) {
            predicates.add(builder.equal(root.get("updatedBy"), commentFilterParameters.getUpdatedBy()));
        }

        // Filter by moduleName.
        if (commentFilterParameters != null && commentFilterParameters.getModuleName() != null) {
            predicates.add(builder.equal(root.get("moduleName"), commentFilterParameters.getModuleName()));
        }

        // Filter by entityName.
        if (commentFilterParameters != null && commentFilterParameters.getEntityName() != null) {
            predicates.add(builder.equal(root.get("entityName"), commentFilterParameters.getEntityName()));
        }

        // Filter by entityId.
        if (commentFilterParameters != null && commentFilterParameters.getEntityId() != null) {
            predicates.add(builder.equal(root.get("entityId"), commentFilterParameters.getEntityId()));
        }

        // Filter by createdAt.
        if (commentFilterParameters != null && commentFilterParameters.getCreatedAt() != null) {
            var createdDateRange = commentFilterParameters.getCreatedAt().split("to");
            if (createdDateRange[0] != null && createdDateRange[1] != null) {
                try {
                    Date createdStartDate = new java.util.Date(Long.parseLong(createdDateRange[0]));
                    Date createdEndDate = new java.util.Date(Long.parseLong(createdDateRange[1]));
                    predicates.add(builder.between(root.get("createdAt"), createdStartDate, createdEndDate));
                } catch (Exception ex) {
                    throw new BadRequestException("Invalid date range.");
                }
            }
        }

        // Filter by updatedAt.
        if (commentFilterParameters != null && commentFilterParameters.getUpdatedAt() != null) {
            var updatedDateRange = commentFilterParameters.getCreatedAt().split("to");
            if (updatedDateRange[0] != null && updatedDateRange[1] != null) {
                try {
                    Date updatedStartDate = new java.util.Date(Long.parseLong(updatedDateRange[0]));
                    Date updatedEndDate = new java.util.Date(Long.parseLong(updatedDateRange[1]));
                    predicates.add(builder.between(root.get("updatedAt"), updatedStartDate, updatedEndDate));
                } catch (Exception ex) {
                    throw new BadRequestException("Invalid date range.");
                }
            }
        }

        // Sorting.
        Path path;

        if (commentFilterParameters != null && commentFilterParameters.getSortOrder() != null && commentFilterParameters.getSortBy() != null) {
            path = root.get(commentFilterParameters.getSortBy());
            if (commentFilterParameters.getSortOrder().equalsIgnoreCase("desc")) {
                query.orderBy(builder.desc(path));
            } else {
                query.orderBy(builder.asc(path));
            }
        } else {
            path = root.get("id");
            query.orderBy(builder.desc(path));
        }

        query.where(builder.and(predicates.toArray(new Predicate[]{})));

        // Pagination.
        var commentEntityTypedQuery = entityManager.createQuery(query);
        var resultList = commentFilterParameters != null
                && commentFilterParameters.getPageSize() != null
                && commentFilterParameters.getPageNumber() != null
                ? commentEntityTypedQuery
                .setFirstResult((commentFilterParameters.getPageNumber() - 1) * commentFilterParameters.getPageSize())
                .setMaxResults(commentFilterParameters.getPageSize()).getResultList()
                : commentEntityTypedQuery.getResultList();


        return resultList.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
