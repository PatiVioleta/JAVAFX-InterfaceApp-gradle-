package repository;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.*;

public class UserRepositoryTest {

    Properties serverProps;
    Connection conn;
    UserRepository r;

    public UserRepositoryTest(){
        serverProps = new Properties();
        try{
            serverProps.load(new FileReader("bd.config"));
            JdbcUtils dbUtils = new JdbcUtils(serverProps);
            conn=dbUtils.getConnection();
            r = new UserRepository(serverProps);

        }catch (IOException e){
            System.out.println("Cannot find bd.config "+e);
        }
    }

    @Test
    public void findOne() {
        try(Statement stmt=conn.createStatement()) {
            stmt.executeUpdate("insert into Users (name, password) values ('test','test')");

            PreparedStatement preStmt=conn.prepareStatement("SELECT userId from Users where name='test'");
            ResultSet result=preStmt.executeQuery();
            result.next();
            int id = result.getInt("userId");

            assert (r.findOne("test","test").getId()==id);
            assert (r.findOne("test","test").getName().equals("test"));

            stmt.executeUpdate("Delete from Users where userId="+id);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindOne test error "+e);
            assert false;
        }
    }
}