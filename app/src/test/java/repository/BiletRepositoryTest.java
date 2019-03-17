package repository;

import model.Artist;
import model.Bilet;
import model.Spectacol;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class BiletRepositoryTest {

    Properties serverProps;
    Connection conn;
    BiletRepository r;
    SpectacolRepository rS;
    ArtistRepository rA;

    public BiletRepositoryTest(){
        serverProps = new Properties();
        try{
            serverProps.load(new FileReader("bd.config"));
            JdbcUtils dbUtils = new JdbcUtils(serverProps);
            conn=dbUtils.getConnection();
            r = new BiletRepository(serverProps);
            rS = new SpectacolRepository(serverProps);
            rA = new ArtistRepository(serverProps);

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
            int idS = result.getInt("IDS");

            PreparedStatement preStmt2=conn.prepareStatement("insert into Bilete (numeCumparator, spectacol, nrLocDorite) values ('test',?,0)");
            preStmt2.setInt(1,idS);
            preStmt2.executeUpdate();

            preStmt=conn.prepareStatement("SELECT biletId from Bilete where numeCumparator='test'");
            result=preStmt.executeQuery();
            result.next();
            int idB = result.getInt("biletId");


            assert (r.findOne(idB).getBiletId()==idB);
            assert (r.findOne(idB).getNumeCumparator().equals("test"));

            stmt.executeUpdate("Delete from Bilete where biletId="+idB);
            stmt.executeUpdate("Delete from Spectacole where IDS="+idS);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindOne test error "+e);
            assert false;
        }
    }

    @Test
    public void findAll() {
        try(Statement stmt=conn.createStatement()) {
            PreparedStatement preStmt=conn.prepareStatement("SELECT count(*) from Bilete");
            ResultSet result=preStmt.executeQuery();
            result.next();
            int nr = result.getInt(1);

            List<Bilet> la =(List<Bilet>) r.findAll();

            assert (la.size()==nr);

            conn.close();
        } catch (SQLException e) {
            System.out.println("FindAll test error "+e);
            assert false;
        }
    }

    @Test
    public void save() {
        try(Statement stmt=conn.createStatement()) {
//            stmt.executeUpdate("Delete from Spectacole where IDS="+

            Artist artist = rA.findOne(1);
            Spectacol spectacol = new Spectacol(1,"test", null,null,20,10, artist);
//
//            stmt.executeUpdate("insert into Spectacole (data, locatie, nrLocDisp,nrLocVand) values ('2018-01-01','test',1,1)");
//            PreparedStatement preStmt=conn.prepareStatement("SELECT IDS from Spectacole where locatie='test'");
//            ResultSet result=preStmt.executeQuery();
//            result.next();
//            int idS = result.getInt("IDS");
//            Spectacol spectacol = rS.findOne(idS);

            r.save(new Bilet(0,"test",spectacol,5));

            PreparedStatement preStmt2=conn.prepareStatement("SELECT biletId from Bilete where numeCumparator='test'");
            ResultSet result2=preStmt2.executeQuery();
            result2.next();
            int idB = result2.getInt("biletId");

            assert (r.findOne(idB).getBiletId()==idB);
            assert (r.findOne(idB).getNumeCumparator().equals("test"));

            stmt.executeUpdate("Delete from Bilete where biletId="+idB);

            conn.close();
        } catch (SQLException e) {
            System.out.println("Save test error "+e);
            assert false;
        }
    }
}