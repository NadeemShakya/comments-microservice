package comment.repository;

import comment.model.CommentEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
