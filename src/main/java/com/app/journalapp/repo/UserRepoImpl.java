package com.app.journalapp.repo;

import com.app.journalapp.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Users> getUsersWithSentimentAnalysis() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery  = criteriaBuilder.createQuery(Users.class);
        Root<Users> user = criteriaQuery.from(Users.class);
        criteriaQuery.select(user).where(criteriaBuilder.isTrue(user.get("sentimentAnalysis")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
