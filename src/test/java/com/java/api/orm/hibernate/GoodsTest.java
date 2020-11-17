package com.java.api.orm.hibernate;

import com.java.api.orm.hibernate.entity.Goods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.List;

public class GoodsTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configuration = new Configuration().configure();//创建配置对象
        sessionFactory = configuration.buildSessionFactory();//创建会话工厂
        session = sessionFactory.openSession();//开启会话
        transaction = session.beginTransaction();//开启事务

    }

    @After
    public void destroy() {
        transaction.commit();//事务提交
        session.close();//关闭会话
        sessionFactory.close();//关闭会话工厂
    }

    @Test
    public void save() {
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i=0; i<10; i++) {
            Goods g = new Goods();
            g.setId(i);
            g.setName("goods_" + i);
            g.setPrice(Double.valueOf(df.format(Math.random() * 100)));
            session.saveOrUpdate(g);
        }
    }

    @Test
    public void update() {
        Goods g = session.find(Goods.class, 1);
        g.setName(g.getName() + "_NEW");
        session.update(g);
    }

    @Test
    public void find() {
        Goods g = session.find(Goods.class, 1);
        System.out.println(g);
    }

    @Test
    public void list() {
        List<Goods> list = session.createQuery("From Goods").list();
        for (Goods g : list) {
            System.out.println(g);
        }
    }

    @Test
    public void delete() {
        Goods g = session.find(Goods.class, 1);
        session.delete(g);
    }
}
