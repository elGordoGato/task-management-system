package org.elgordogato.taskmanagementsystem.dtos.mapper;

import org.elgordogato.taskmanagementsystem.dtos.CommentDto;
import org.elgordogato.taskmanagementsystem.entities.CommentEntity;

import java.util.List;

public class CommentMapper {
    public static CommentDto dtoFromEntity(CommentEntity createdComment) {
        return CommentDto.builder()
                .id(createdComment.getId())
                .text(createdComment.getText())
                .authorId(createdComment.getAuthor().getId())
                .createdAt(createdComment.getCreatedAt())
                .updatedAt(createdComment.getUpdatedAt())
                .build();
    }

    public static List<CommentDto> listDtoFromEntity(List<CommentEntity> commentEntities) {
        return commentEntities.stream()
                .map(CommentMapper::dtoFromEntity)
                .toList();
    }
}
