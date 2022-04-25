package comment.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.DynamicUpdate;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.core.annotation.Introspected;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Builder
@Introspected
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "comments")
@Where(clause = "is_deleted=false")
@DynamicUpdate
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @DateCreated
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp createdAt;

    @DateUpdated
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp updatedAt;

    @Column(name = "comment")
    private String comment;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private Integer entityId;

    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
