package utils;

import event.Event;

public interface Observable<S extends Event> {
    void addObserver(Observer<S> e);
    void removeObserver(Observer<S> e);
    void notifyObservers(S e);

}
