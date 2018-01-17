package dao.daoImpl;

import dao.StoreDao;
import dao.UserDao;
import model.Store;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Database;
import util.HibernateUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreDaoImpl extends BaseDaoImpl implements StoreDao {

    public void saveStore(Store store) {
        super.save(store);
    }

    public void deleteStore(Store store) {

    }

    public Store getStoreByID(String id) {
        return null;
    }


}
