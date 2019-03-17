package event;

public class ObjectEvent<E> implements Event {
    private E oldData;
    private E data;
    private ChangeEventType type;

    public E getOldData() {
        return oldData;
    }

    public void setOldData(E oldData) {
        this.oldData = oldData;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public void setType(ChangeEventType type) {
        this.type = type;
    }

    public ObjectEvent(E oldData, E data, ChangeEventType type) {
        this.oldData = oldData;
        this.data = data;
        this.type = type;
    }
}
