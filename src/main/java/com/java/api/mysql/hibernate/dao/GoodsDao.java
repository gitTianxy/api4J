package com.java.api.mysql.hibernate.dao;

import com.java.api.mysql.hibernate.entity.Goods;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GoodsDao extends HibernateDaoSupport {

    public void save(Goods user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            this.getHibernateTemplate().save(user);
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Transactional
    public List<Goods> list() {
        /*Session session = null;
        Transaction transaction = null;
        try {
            session = this.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            return (List<Goods>) this.getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Goods.class));
        } finally {
            transaction.commit();
            session.close();
        }*/
        return (List<Goods>) this.getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Goods.class));
    }
}
