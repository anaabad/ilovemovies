package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.controllers.dtos.CommentDto;
import com.github.anaabad.ilovemovies.persistence.entity.CommentEntity;
import com.github.anaabad.ilovemovies.persistence.entity.MovieEntity;
import com.github.anaabad.ilovemovies.persistence.repository.CommentRepository;
import com.github.anaabad.ilovemovies.persistence.repository.MovieRepository;
import com.github.anaabad.ilovemovies.services.transformers.CommentTrf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;

    private final CommentTrf commentTrf;

    public CommentDto findById(Long id) {
        Optional<CommentEntity> commentOpt = commentRepository.findById(id);
        return commentOpt.map(commentTrf::commentEntityToCommentDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, format("Comment with id %s is not found", id)));

    }

    public CommentDto save(CommentDto commentDto) {
        CommentEntity commentEntity = commentTrf.commentDtoToCommentEntity(commentDto);
        return commentTrf.commentEntityToCommentDto(commentRepository.save(commentEntity));
    }

    public CommentDto update(Long id, CommentDto commentDto) {
        CommentDto comment = findById(id);
        comment.setText(commentDto.getText());
        return save(comment);
    }

    public List<CommentDto> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentEntity -> commentTrf.commentEntityToCommentDto(commentEntity))
                .collect(Collectors.toList());

    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public List<CommentDto> getAllByMovieId(Long id) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);
        List<CommentEntity> comments= movieEntity.map(commentRepository::findAllByMovie).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, format("Movie with id %s is not found", id)));
        return comments.stream().map(comment -> commentTrf.commentEntityToCommentDto(comment)).collect(Collectors.toList());
    }
}
