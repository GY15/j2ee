package dao.daoImpl;

import dao.OrderDao;
import model.Order;
import model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import util.Database;
import util.HibernateUtil;

import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {

    /**
     * 获取指定ID的所有订单数目
     *
     * @author 61990
     * @updateTime 2017/12/19
     * @param userID 用户id
     * @return 指定ID的所有订单数目
     */
    public int getNumOfOrders(String userID) {
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx=session.beginTransaction();
            String sql = "select o.orderid,o.userid,store.name,o.num,o.time,o.price,IF(o.num>store.inventory,0,1) as outStock\n" +
                    "from `order` o, store \n" +
                    "where o.productid = store.productid\n" +
                    "and userid ="+userID+"\n" +
                    "GROUP BY o.orderid";
            TypedQuery<Order> query = session.createNativeQuery(sql,Order.class);
            List<Order> result = query.getResultList();
            tx.commit();
            System.out.println(result.size());
            return result.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取指定ID的某一页订单
     *
     * @author 61990
     * @updateTime 2017/12/19
     * @param userID 用户id
     * @param start 开始条数
     * @param num  需要条数
     * @return 指定ID的某一页订单
     */
    public List<Order> getOrdersOfUser(String   userID, int start, int num) {
        List<Order> orders = new ArrayList<Order>();
        try {
            Session session = HibernateUtil.getSession();
            Transaction tx=session.beginTransaction();
            String sql = "select o.orderid,o.userid,store.name,o.num,o.time,o.price,IF(o.num>store.inventory,0,1) as outStock\n" +
                    "from `order` o, store \n" +
                    "where o.productid = store.productid\n" +
                    "and userid ="+userID+"\n" +
                    "GROUP BY o.orderid " +
                    "limit "+start+","+num;
            TypedQuery<Order> query = session.createNativeQuery(sql,Order.class);
            List<Order> result = query.getResultList();
            tx.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
