package Services;

import DAO.User_DAO;
import Model.User_Info;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(User_Info user) {
        try {
            if (User_DAO.isExists(user.getEmail())){
                return 0;
            } else {
                return User_DAO.saveUser(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
