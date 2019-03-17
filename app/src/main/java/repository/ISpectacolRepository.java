package repository;

public interface ISpectacolRepository<ID,T extends HasId<ID>> {
    T findOne(ID id);
    Iterable<T> findAll();
    Iterable<T> findAllData(String data);
    T update(T entity);
}
