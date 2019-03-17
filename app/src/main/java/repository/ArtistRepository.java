package repository;

import model.Artist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArtistRepository implements IArtistRepository<Integer, Artist> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ArtistRepository(Properties props){
        logger.info("Initializing ArtistRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public Artist findOne(Integer integer) {
        logger.traceEntry("finding artist with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Artisti where artistId=?")){
            preStmt.setInt(1,integer);

            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {

                    int id = result.getInt("artistId");
                    String nume = result.getString("artistNume");

                    Artist artist = new Artist(id, nume);

                    logger.traceExit(artist);
                    return artist;
                }
            }

            con.close();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No artist found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Artist> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();

        List<Artist> artisti=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Artisti")) {

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    int id = result.getInt("artistId");
                    String nume = result.getString("artistNume");

                    Artist task = new Artist(id, nume);
                    artisti.add(task);
                }
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(artisti);
        return artisti;
    }
}
