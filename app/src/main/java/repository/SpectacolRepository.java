package repository;

import model.Spectacol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SpectacolRepository implements ISpectacolRepository<Integer, Spectacol>{
    private JdbcUtils dbUtils;
    private ArtistRepository aR;

    private static final Logger logger= LogManager.getLogger();

    public SpectacolRepository(Properties props){
        logger.info("Initializing SpectacolRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
        aR = new ArtistRepository(props);
    }

    public Spectacol findOne(Integer integer) {
        logger.traceEntry("finding spectacol with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Spectacole where IDS=?")){
            preStmt.setInt(1,integer);

            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {

                    int id = result.getInt("IDS");
                    String data = result.getString("data");
                    String ora = result.getString("ora");
                    String locatie = result.getString("locatie");
                    int nrLocDisp = result.getInt("nrLocDisp");
                    int nrLocVand = result.getInt("nrLocVand");
                    int artistId = result.getInt("artist");


                    Spectacol spect = null;

                        spect = new Spectacol(id, locatie, data,ora, nrLocDisp, nrLocVand, aR.findOne(artistId));
                        logger.traceExit(spect);

                    return spect;
                }
            }
            con.close();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No spectacol found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Spectacol> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Spectacol> spects=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Spectacole")) {

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("IDS");
                    String data = result.getString("data");
                    String ora = result.getString("ora");
                    String locatie = result.getString("locatie");
                    int nrLocDisp = result.getInt("nrLocDisp");
                    int nrLocVand = result.getInt("nrLocVand");
                    int artistId = result.getInt("artist");

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                    LocalDateTime test = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = test.format(myFormatObj);

                    Spectacol spect = null;


                        spect = new Spectacol(id, locatie, data, ora, nrLocDisp, nrLocVand, aR.findOne(artistId));
                        logger.traceExit(spect);
                        spects.add(spect);

                }
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(spects);
        return spects;
    }

    @Override
    public Iterable<Spectacol> findAllData(String data) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Spectacol> spects=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Spectacole where data=?")) {
            preStmt.setString(1,data);

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("IDS");
                    //data = result.getString("data");
                    String ora = result.getString("ora");
                    String locatie = result.getString("locatie");
                    int nrLocDisp = result.getInt("nrLocDisp");
                    int nrLocVand = result.getInt("nrLocVand");
                    int artistId = result.getInt("artist");

                    Spectacol spect = null;
                    spect = new Spectacol(id, locatie, data, ora, nrLocDisp, nrLocVand, aR.findOne(artistId));
                    logger.traceExit(spect);
                    spects.add(spect);

                }
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(spects);
        return spects;
    }

    @Override
    public Spectacol update(Spectacol entity) {
        logger.traceEntry("update spectacol {} ",entity);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("update Spectacole set nrLocDisp=?, nrLocVand=? where IDS=?")){

            preStmt.setInt(1,entity.getNrLocDisp());
            preStmt.setInt(2,entity.getNrLocVand());
            preStmt.setInt(3,entity.getId());

            int result = preStmt.executeUpdate();
            con.close();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }

        logger.traceExit("Updated ", entity);
        return entity;
    }
}
