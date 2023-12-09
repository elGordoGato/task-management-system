package org.elgordogato.taskmanagementsystem.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.elgordogato.taskmanagementsystem.entities.TaskEntity;
import org.elgordogato.taskmanagementsystem.utils.TaskParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<TaskEntity> findAllByParams(TaskParameters parameters, Pageable page) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("with-comments");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> query = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> task = query.from(TaskEntity.class);

        Predicate commonPredicate = getCommonPredicate(cb, task, parameters);
        System.out.println(commonPredicate);

        query.select(task)
                .where(commonPredicate)
                .orderBy(QueryUtils.toOrders(page.getSort(), task, cb));

        List<TaskEntity> result = entityManager.createQuery(query)
                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .setFirstResult((int) page.getOffset())
                .setMaxResults(page.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<TaskEntity> tasksForCount = countQuery.from(TaskEntity.class);
        countQuery.select(cb.count(tasksForCount.get("id")))
                .where(getCommonPredicate(cb, tasksForCount, parameters));


        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(result, page, count);
    }

    private Predicate getCommonPredicate(CriteriaBuilder cb, Root<TaskEntity> task, TaskParameters parameters) {
        List<Predicate> andPredicates = new ArrayList<>();

        Optional.ofNullable(parameters.getTitle())
                .ifPresent(title ->
                        andPredicates.add(
                                cb.like(cb.lower(task.get("title")),
                                        String.format("%%%s%%", title.toLowerCase()))));

        Optional.ofNullable(parameters.getDescription())
                .ifPresent(description ->
                        andPredicates.add(
                                cb.like(cb.lower(task.get("description")),
                                        String.format("%%%s%%", description.toLowerCase()))));

        Optional.ofNullable(parameters.getStatus())
                .ifPresent(status ->
                        andPredicates.add(
                                cb.equal(task.get("status"), status)));

        Optional.ofNullable(parameters.getPriority())
                .ifPresent(priority ->
                        andPredicates.add(
                                cb.equal(task.get("priority"), priority)));

        Optional.ofNullable(parameters.getHasComments())
                .ifPresent(hasComments ->
                        andPredicates.add(
                                cb.equal(cb.isEmpty(task.get("comments")), !hasComments)));

        List<Predicate> orPredicates = getUserPredicates(cb, task, parameters);
        if (!orPredicates.isEmpty()) {
            andPredicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
        }

        return cb.and(andPredicates.toArray(new Predicate[0]));


    }

    private List<Predicate> getUserPredicates(CriteriaBuilder cb, Root<TaskEntity> task, TaskParameters parameters) {
        List<Predicate> orPredicates = new ArrayList<>();
        Optional.ofNullable(parameters.getCreatorId())
                .ifPresent(creatorId ->
                        orPredicates.add(
                                cb.equal(task.get("creator").get("id"), creatorId)));

        Optional.ofNullable(parameters.getExecutorId())
                .ifPresent(executorId ->
                        orPredicates.add(
                                cb.equal(task.get("executor").get("id"), executorId)));

        return orPredicates;
    }
}
