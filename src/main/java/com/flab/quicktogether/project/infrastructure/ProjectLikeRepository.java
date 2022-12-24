package com.flab.quicktogether.project.infrastructure;

import com.flab.quicktogether.project.domain.Likes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProjectLikeRepository {
    private final EntityManager em;

    public void save(Likes likes) {
        em.persist(likes);
    }

    public Likes findOne(Long id) {
        return em.find(Likes.class, id);
    }

    public void delete(Likes likes) {
        em.remove(likes);
    }

    public Long findTotalLikesByProjectId(Long projectId) {
        try {
            Object totalLikes = em.createQuery("select count(l) from Likes l join l.project p where p.id = :projectId", Likes.class)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
            return (Long) totalLikes;
        } catch (NoResultException ignore) {
            return 0L;
        }

    }

    public List<Likes> findByProjectId(Long projectId) {
        return em.createQuery("select l from Likes l join l.project p where p.id = :projectId", Likes.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }



        public Optional<Likes> findByProjectIdAndMemberId(Long projectId, Long memberId) {
        Likes likes = null;
        try {
            likes = em.createQuery("select l from Likes l " + "where l.member.id = :memberId and l.project.id = :projectId", Likes.class)
                    .setParameter("memberId", memberId)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
            return Optional.ofNullable(likes);
        } catch (NoResultException ignore) {
            return Optional.ofNullable(likes);
        }
    }
}
