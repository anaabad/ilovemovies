package com.github.anaabad.ilovemovies.services.transformers;

import com.github.anaabad.ilovemovies.controllers.dtos.CommentDto;
import com.github.anaabad.ilovemovies.persistence.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentTrf {
    public CommentDto commentEntityToCommentDto(CommentEntity commentEntity){
        CommentDto commentDto = new CommentDto();
        commentDto.setText(commentEntity.getText());
        return commentDto;
    }
    public CommentEntity commentDtoToCommentEntity(CommentDto commentDto){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setText(commentDto.getText());
        return commentEntity;
    }
}
