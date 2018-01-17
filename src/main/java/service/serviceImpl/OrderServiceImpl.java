package service.serviceImpl;

import dao.OrderDao;
import dao.daoImpl.OrderDaoImpl;
import model.Order;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    public List<Order> getOrdersOfThePage(String userID, int start, int num) {
        return orderDao.getOrdersOfUser(userID,start,num);
    }

    public int getNumOfOrders(String userID) {
        return orderDao.getNumOfOrders(userID);
    }
}
