package comment.controller;

import java.util.List;

import jakarta.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import comment.dtos.CommentDto;
import comment.service.CommentService;
import comment.dtos.CommentFilterParameters;
import comment.error.exception.NotFoundException;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.Delete;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;

@Controller("/comments")
public class CommentController {
    @Inject
    private CommentService commentService;

    /**
     * Find all comments supported with filter parameters.
     *
     * @return HttpResponse<List<CommentDto>> Response with the list of comments.
     */
    @Get("/{?commentFilterParameters*}")
    public HttpResponse<List<CommentDto>> findAll(@Nullable CommentFilterParameters commentFilterParameters) {
        return HttpResponse.ok(commentService.findAll(commentFilterParameters));
    }

    /**
     * Find the comment by its id.
     *
     * @param id The comment's id.
     * @return HttpResponse<CommentDto> The comment.
     * @throws NotFoundException if the comment with given id doesn't exist.
     */
    @Get("/{id}")
    public HttpResponse<CommentDto> findById(@PathVariable Integer id) {
        return HttpResponse.ok(commentService.findById(id));
    }


    /**
     * Save a comment.
     *
     * @param commentDto DTO for the comment.
     * @return HttpResponse<CommentDto> The saved comment.
     */
    @Validated
    @Post
    public HttpResponse<CommentDto> save(@Body @Valid CommentDto commentDto) {
        return HttpResponse.created(commentService.save(commentDto));
    }


    /**
     * Update a comment.
     *
     * @param id The comment id.
     * @param commentDto DTO for the comment with update information.
     * @return HttpResponse<CommentDto> The updated comment.
     * @throws NotFoundException if the comment with given id doesn't exist.
     */
    @Patch("/{id}")
    public HttpResponse<CommentDto> update(@PathVariable("id") Integer id, @NotNull @Body CommentDto commentDto) {
        return HttpResponse.ok(commentService.update(id, commentDto));
    }

    /**
     * Delete a comment.
     *
     * @param id The comment id.
     * @return HttpResponse<String> The success message.
     * @throws NotFoundException if the comment with given id doesn't exist.
     */
    @Delete("/{id}")
    public HttpResponse<String> delete(@PathVariable Integer id) {
        return HttpResponse.ok(commentService.delete(id));
    }
}
