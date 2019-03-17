package repository;

import model.Bilet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BiletRepository implements IBiletRepository<Integer, Bilet>{
    private JdbcUtils dbUtils;
    private SpectacolRepository sRepo;

    private static final Logger logger= LogManager.getLogger();

    public BiletRepository(Properties props){
        logger.info("Initializing BiletRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
        sRepo = new SpectacolRepository(props);
    }

    public Bilet findOne(Integer integer) {
        logger.traceEntry("finding bilet with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Bilete where biletId=?")){
            preStmt.setInt(1,integer);

            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {

                    int id = result.getInt("biletId");
                    String numeC = result.getString("numeCumparator");
                    int spectacolId = result.getInt("spectacol");
                    int nrLocDorite = result.getInt("nrLocDorite");

                    Bilet bilet = new Bilet(id, numeC, sRepo.findOne(spectacolId), nrLocDorite);
                    logger.traceExit(bilet);
                    return bilet;
                }
            }

            con.close();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No bilet found with id {}", integer);

        return null;
    }

    public Iterable<Bilet> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();

        List<Bilet> bil=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Bilete")) {

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("biletId");
                    String numeC = result.getString("numeCumparator");
                    int spectacolId = result.getInt("spectacol");
                    int nrLocDorite = result.getInt("nrLocDorite");

                    Bilet bilet = new Bilet(id, numeC, sRepo.findOne(spectacolId), nrLocDorite);
                    bil.add(bilet);
                }
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(bil);
        return bil;
    }

    @Override
    public void save(Bilet entity) {
        logger.traceEntry("save bilet {} ",entity);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("insert into Bilete (numeCumparator, spectacol, nrLocDorite) values (?,?,?)")){

            preStmt.setString(1,entity.getNumeCumparator());
            preStmt.setInt(2,entity.getSpectacol().getId());
            preStmt.setInt(3,entity.getNrLocDorite());

            int result = preStmt.executeUpdate();
            con.close();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }

        logger.traceExit("Inserted ", entity);
    }
}
