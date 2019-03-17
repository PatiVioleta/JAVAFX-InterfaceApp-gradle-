package repository;

import model.Artist;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import repository.JdbcUtils;


public class ArtistRepositoryTest {

    Properties serverProps;
    Connection conn;
    ArtistRepository r;

    public ArtistRepositoryTest(){
        serverProps = new Properties();
        try{
            serverProps.load(new FileReader("bd.config"));
            JdbcUtils dbUtils = new JdbcUtils(serverProps);
            conn=dbUtils.getConnection();
            r = new ArtistRepository(serverProps);

        }catch (IOException e){
            System.out.println("Cannot find bd.config "+e);
        }
    }

    @Test
    public void findOne() {
        try(Statement stmt=conn.createStatement()) {
            stmt.executeUpdate("insert into Artisti (artistNume) values ('test')");

            PreparedStatement preStmt=conn.prepareStatement("SELECT artistId from Artisti where artistNume='test'");
            ResultSet result=preStmt.executeQuery();
            result.next();
            int id = result.getInt("artistId");

            assert (r.findOne(id).getArtistId()==id);
            assert (r.findOne(id).getName().equals("test"));

            stmt.executeUpdate("Delete from Artisti where artistId="+id);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindOne test error "+e);
            assert false;
        }
    }

    @Test
    public void findAll() {
        try(Statement stmt=conn.createStatement()) {
            PreparedStatement preStmt=conn.prepareStatement("SELECT count(*) from Artisti");
            ResultSet result=preStmt.executeQuery();
            result.next();
            int nr = result.getInt(1);

            List<Artist> la =(List<Artist>) r.findAll();

            assert (la.size()==nr);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindAll test error "+e);
            assert false;
        }
    }
}