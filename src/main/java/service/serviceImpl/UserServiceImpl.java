package service.serviceImpl;

import dao.UserDao;
import dao.daoImpl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    public boolean login(String userID, String password) {
        User user = userDao.getUser(userID);
        if (user == null||!user.getPassword().equals(password)) {
            return false;
        } else {
           return true;
        }
    }
}
