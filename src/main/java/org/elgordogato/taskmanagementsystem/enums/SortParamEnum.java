package org.elgordogato.taskmanagementsystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortParamEnum {
    CREATED("createdAt"),
    UPDATED("updatedAt");

    private final String fieldName;
}
