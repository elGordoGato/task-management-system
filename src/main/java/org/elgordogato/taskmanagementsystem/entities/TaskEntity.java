package org.elgordogato.taskmanagementsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskPriorityEnum;
import org.elgordogato.taskmanagementsystem.utils.enums.TaskStatusEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "tasks")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@NamedEntityGraph(
        name = "with-comments",
        attributeNodes = {
                @NamedAttributeNode("comments"),
        }
)
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status = TaskStatusEnum.PENDING;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriorityEnum priority = TaskPriorityEnum.LOW;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private UserEntity creator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private UserEntity executor;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
