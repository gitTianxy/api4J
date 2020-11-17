package com.java.api.orm.hibernate;

import com.java.api.orm.hibernate.dao.GoodsDao;
import com.java.api.orm.hibernate.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/applicationContext.xml"
})
public class GoodsDaoTest {

    @Autowired
    GoodsDao goodsDao;

    @Test
    public void save() {
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i=0; i<10; i++) {
            Goods g = new Goods();
            g.setId(i);
            g.setName("goods_" + i);
            g.setPrice(Double.valueOf(df.format(Math.random() * 100)));
            goodsDao.save(g);
        }
    }

    @Test
    public void list() {
        List<Goods> list = goodsDao.list();
        for (Goods g : list) {
            System.out.println(g);
        }
    }
}
