package repository;

import model.Artist;
import model.Spectacol;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class SpectacolRepositoryTest {

    Properties serverProps;
    Connection conn;
    SpectacolRepository r;

    public SpectacolRepositoryTest(){
        serverProps = new Properties();
        try{
            serverProps.load(new FileReader("bd.config"));
            JdbcUtils dbUtils = new JdbcUtils(serverProps);
            conn=dbUtils.getConnection();
            r = new SpectacolRepository(serverProps);

        }catch (IOException e){
            System.out.println("Cannot find bd.config "+e);
        }
    }

    @Test
    public void findOne() {
        try(Statement stmt=conn.createStatement()) {
            stmt.executeUpdate("insert into Spectacole (data, locatie, nrLocDisp,nrLocVand,artist) values ('2018-01-01','test',1,1,1)");

            PreparedStatement preStmt=conn.prepareStatement("SELECT IDS from Spectacole where locatie='test'");
            ResultSet result=preStmt.executeQuery();
            result.next();
            int id = result.getInt("IDS");

            assert (r.findOne(id).getId()==id);
            assert (r.findOne(id).getLocation().equals("test"));

            stmt.executeUpdate("Delete from Spectacole where IDS="+id);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindOne test error "+e);
            assert false;
        }
    }

    @Test
    public void findAll() {
        try(Statement stmt=conn.createStatement()) {
            PreparedStatement preStmt=conn.prepareStatement("SELECT count(*) from Spectacole");
            ResultSet result=preStmt.executeQuery();
            result.next();
            int nr = result.getInt(1);

            List<Spectacol> la =(List<Spectacol>) r.findAll();

            assert (la.size()==nr);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindAll test error "+e);
            assert false;
        }
    }
}