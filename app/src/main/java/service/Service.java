package service;

import event.ChangeEventType;
import utils.Observer;
import event.ObjectEvent;
import utils.Observable;
import model.Artist;
import model.Bilet;
import model.Spectacol;
import model.User;
import repository.IArtistRepository;
import repository.IBiletRepository;
import repository.ISpectacolRepository;
import repository.IUserRepository;
import validator.BiletValidator;
import validator.ValidationException;
import validator.Validator;

import java.util.ArrayList;
import java.util.HashSet;

public class Service implements Observable<ObjectEvent<Spectacol>> {
    IUserRepository<Integer, User> repoU;
    IArtistRepository<Integer, Artist> repoA;
    ISpectacolRepository<Integer, Spectacol> repoS;
    IBiletRepository<Integer, Bilet> repoB;

    Validator<Bilet> biletValidator = new BiletValidator();

    private static ArrayList<Observer<ObjectEvent<Spectacol>>> observersS;

    public Service(IUserRepository u, IArtistRepository a, ISpectacolRepository s, IBiletRepository b){
        repoU = u;
        repoA = a;
        repoS = s;
        repoB = b;

        observersS = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer<ObjectEvent<Spectacol>> e) {
        observersS.add(e);
    }

    @Override
    public void removeObserver(Observer<ObjectEvent<Spectacol>> e) {
        observersS.remove(e);
    }

    @Override
    public void notifyObservers(ObjectEvent<Spectacol> e) {
        observersS.forEach(obs -> obs.update(e));
    }

    public User findOneUser(String nume, String pass){
        return repoU.findOne(nume,pass);
    }

    public Iterable<Spectacol> findAllSpectacol(){
        return repoS.findAll();
    }

    public Iterable<String> findAllZile(){
        Iterable<String> l = new HashSet<>();
        repoS.findAll().forEach(x->((HashSet<String>) l).add(x.getDate()));
        return l;
    }

    public Iterable<Spectacol> findAllSpectacolData(String data){
        return repoS.findAllData(data);
    }

    public void vindeBilet(Spectacol spectacol, String numeCump, Integer nrLocDor){
        Bilet bilet = new Bilet(null,numeCump,spectacol,nrLocDor);
        biletValidator.validate(bilet);
        repoB.save(bilet);
        spectacol.setNrLocDisp(spectacol.getNrLocDisp()-bilet.getNrLocDorite());
        spectacol.setNrLocVand(spectacol.getNrLocVand()+bilet.getNrLocDorite());

        repoS.update(spectacol);

        notifyObservers(new ObjectEvent(repoS.findOne(spectacol.getId()), spectacol, ChangeEventType.UPDATE));
    }

    public void refreshSpectacole(){
        repoS.findAll().forEach(x->notifyObservers(new ObjectEvent(x,x,ChangeEventType.UPDATE)));
    }
}
