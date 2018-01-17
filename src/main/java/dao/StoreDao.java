package dao;

import model.Store;
import model.User;

/**
 * Created by 61990 on 2017/12/19.
 */
public interface StoreDao extends BaseDao{


    public void saveStore(Store store);
    public void deleteStore(Store store);
    public Store getStoreByID(String id);
}
