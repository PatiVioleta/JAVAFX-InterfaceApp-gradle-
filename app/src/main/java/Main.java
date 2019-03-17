import view.LoginSceneCtrl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Artist;
import model.Bilet;
import model.Spectacol;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.*;
import service.Service;

import java.io.FileReader;
import java.util.Properties;

public class Main extends Application {
    static Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Properties serverProps=new Properties();
        serverProps.load(new FileReader("bd.config"));

        IUserRepository<Integer, User> repoU = new UserRepository(serverProps);
        IArtistRepository<Integer, Artist> repoA = new ArtistRepository(serverProps);
        ISpectacolRepository<Integer, Spectacol> repoS = new SpectacolRepository(serverProps);
        IBiletRepository<Integer, Bilet> repoB = new BiletRepository(serverProps);

        Service service = new Service(repoU,repoA,repoS,repoB);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/loginScene.fxml"));

        Parent root = (Parent) loader.load();
        LoginSceneCtrl ctrl=loader.getController();
        ctrl.setController(service);

        primaryStage.setTitle("Festival Login");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
