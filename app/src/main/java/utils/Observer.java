package utils;

import event.Event;

public interface Observer<E extends Event> {
    void update(E e);
}