package com.codegym.repository;

import com.codegym.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
public class ProductRepository implements IProductRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query= em.createQuery("select c from Product c", Product.class );
        return query.getResultList();
    }

    @Override
    public Product findById(Integer id) {
        TypedQuery<Product> query= em.createQuery("select c from Product c where  c.id=:id",Product.class);
        query.setParameter("id",id);
        try{
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        if (product.getId() != null) {
            em.merge(product);
        } else {
            em.persist(product);
        }
    }

    @Override
    public void remove(Integer id) {
        Product product = findById(id);
        if (product != null) {
            em.remove(product);
        }
    }
}
