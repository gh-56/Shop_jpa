package com.busanit.jpashop.repository;

import com.busanit.jpashop.entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryEM {

    @PersistenceContext
    private EntityManager em;

    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    public Item find(Long id) {
        return em.find(Item.class, id);
    }
}
