package view;

import event.ChangeEventType;
import event.ObjectEvent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Spectacol;
import service.Service;
import utils.Observable;
import utils.Observer;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AngajatSceneCtrl implements Observer<ObjectEvent<Spectacol>> {
    Service service;
    private Stage stage;

    private ObservableList<Spectacol> observableList;
    private ObservableList<String> observableZile;
    private ObservableList<Spectacol> observableListCautare;

    @FXML private TableView<Spectacol> spectacoleTableView;
    @FXML private TableColumn<Spectacol, String> numeArtistCol;
    @FXML private TableColumn<Spectacol, String> dataCol;
    @FXML private TableColumn<Spectacol, String> locatieCol;
    @FXML private TableColumn<Spectacol, String> locDispCol;
    @FXML private TableColumn<Spectacol, String> locVandCol;

    @FXML private TableView<Spectacol> cautareTableView;
    @FXML private TableColumn<Spectacol, String> numeArtistCol2;
    @FXML private TableColumn<Spectacol, String> locatieCol2;
    @FXML private TableColumn<Spectacol, String> oraCol2;
    @FXML private TableColumn<Spectacol, String> locDispCol2;

    @FXML private ComboBox<String> comboZile;

    @FXML private TextField numeCumparatorField;
    @FXML private TextField nrLocDorField;

    public AngajatSceneCtrl(){}

    public void setController(Service service, Stage stage){
        this.service = service;
        this.stage = stage;

        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllSpectacol().spliterator(), false).collect(Collectors.toList()));

        loadData();
        seteazaComboZile();

        service.addObserver(this);

        //service.refreshSpectacole();
    }

    @FXML
    private void loadData(){
        observableList = FXCollections.observableList(StreamSupport.stream(service.findAllSpectacol().spliterator(), false).collect(Collectors.toList()));
        spectacoleTableView.setItems(observableList);

    }

    public void seteazaComboZile(){
        observableZile = FXCollections.observableList(StreamSupport.stream(service.findAllZile().spliterator(), false).collect(Collectors.toList()));
        comboZile.setItems(observableZile);
    }

    public void seteazaCautareTableView(){
        if(cautareTableView.getItems()!=null)
            cautareTableView.getItems().clear();
//        cautareTableView.refresh();

        String data = comboZile.getValue();
        observableListCautare = FXCollections.observableList(StreamSupport.stream(service.findAllSpectacolData(data).spliterator(), false).collect(Collectors.toList()));
        cautareTableView.setItems(observableListCautare);
    }

    @FXML
    private void initialize(){
        numeArtistCol.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("numeArtist"));
        dataCol.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("date"));
        locatieCol.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("location"));
        locDispCol.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("nrLocDisp"));
        locVandCol.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("nrLocVand"));
        spectacoleTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        numeArtistCol2.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("numeArtist"));
        locatieCol2.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("location"));
        oraCol2.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("ora"));
        locDispCol2.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("nrLocDisp"));
        cautareTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        spectacoleTableView.setItems(observableList);
        cautareTableView.setItems(observableListCautare);

        colorareTableView1();
        colorareTableview2();

    }

    private void vindeBilet(){

        try {
            Spectacol spectacol = cautareTableView.getSelectionModel().getSelectedItem();
            String numeCumparator = numeCumparatorField.getText();
            Integer nrLocDor = Integer.parseInt(nrLocDorField.getText());

            service.vindeBilet(spectacol, numeCumparator, nrLocDor);
            confirmationWindow("Confirmare", "Biletul a fost vandut cu succes!");

//            spectacoleTableView.refresh();
//            cautareTableView.refresh();
//
//            service.refreshSpectacole();
//            service.refreshSpectacole();

        }catch (ValidationException err){
            errorWindow("Eroare bilet",err.getMessage());
        }catch (NullPointerException err){
            errorWindow("Eroare bilet","Selectati un spectacol mai intai!");
        }catch (NumberFormatException err){
            errorWindow("Eroare bilet","Numarul de locuri dorite este invalid!");
        }

    }

    private void confirmationWindow(String text, String m){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(text);
        alert.setContentText(m);
        alert.show();
    }

    private void errorWindow(String text, String m){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(text);
        alert.setContentText(m);
        alert.show();
    }

    @FXML
    private void cautareHandler(){
        seteazaCautareTableView();
    }

    @FXML
    private void vindeBiletHandler(){
        vindeBilet();
    }

    @Override
    public void update(ObjectEvent<Spectacol> s) {
        if(s.getType() == ChangeEventType.ADD){
            observableList.add(s.getData());
            observableListCautare.add(s.getData());
        }
        if(s.getType() == ChangeEventType.UPDATE){
            int idx = observableList.indexOf(s.getOldData());
            observableList.set(idx, s.getData());

            idx = observableListCautare.indexOf(s.getOldData());
            observableListCautare.set(idx, s.getData());
        }
        if(s.getType() == ChangeEventType.DELETE){
            observableList.remove(s.getData());
            observableListCautare.remove(s.getData());
        }
    }

    public void colorareTableView1() {
        spectacoleTableView.setRowFactory(row -> new TableRow<Spectacol>() {
            @Override
            public void updateItem(Spectacol item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getNrLocDisp() == 0) {

//                        System.out.println("AICIII____________________________________________________");
//                        System.out.println(item);
//                        System.out.println(getChildren().size());

                        for (int i = 0; i < getChildren().size(); i++) {
//                            System.out.println("schimb");
                            ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #ff7b62");
                        }
                    }else {
                        if (getTableView().getSelectionModel().getSelectedItems().contains(item)) {
                            for (int i = 0; i < getChildren().size(); i++) {
                                ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #97c99e");
                            }
                        } else {
                            for (int i = 0; i < getChildren().size(); i++) {
                                ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
                                ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #ffffff");
                                ;
                            }
                        }

                    }
                }
            }
        });
    }

    public void colorareTableview2(){
        cautareTableView.setRowFactory(row-> new TableRow<Spectacol>(){
            @Override
            public void updateItem(Spectacol item, boolean empty){
                super.updateItem(item,empty);

                if (item == null || empty){
                    setStyle("");
                }else{
                    if(item.getNrLocDisp()==0){

//                        System.out.println("AICIII222222222____________________________________________________");
//                        System.out.println(item);
//                        System.out.println(getChildren().size());

                        for(int i=0; i<getChildren().size();i++){
//                            System.out.println("schimb22222222222");
                            ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #ff7b62");
                        }
                    }else {
                        if (getTableView().getSelectionModel().getSelectedItems().contains(item)) {
                            for (int i = 0; i < getChildren().size(); i++) {
                                ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #97c99e");
                            }
                        } else {
                            for (int i = 0; i < getChildren().size(); i++) {
                                ((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
                                ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #ffffff");
                                ;
                            }
                        }

                    }
                }
            }
        });
    }

    @FXML
    public void close(){
        stage.close();
    }
}
