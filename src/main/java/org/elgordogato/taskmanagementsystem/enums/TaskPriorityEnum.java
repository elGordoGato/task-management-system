package org.elgordogato.taskmanagementsystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskPriorityEnum {
    LOW("низкий"),
    MODERATE("средний"),
    HIGH("высокий");

    private final String priority;
}
