package repository;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.Properties;

public class UserRepository implements IUserRepository<Integer,User> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props){
        logger.info("Initializing UserRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public User findOne(String nume, String pass) {
        logger.traceEntry("finding user with name {} pass {}", nume, pass);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Users where name=? and password=?")){
            preStmt.setString(1, nume);
            preStmt.setString(2, pass);

            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {

                    int id = result.getInt("userId");
                    String name = result.getString("name");
                    String password = result.getString("password");

                    User user = new User(id, name, password);
                    logger.traceExit(user);
                    return user;
                }
            }
            con.close();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No user found with name {} pass ...", nume);

        return null;
    }
}
