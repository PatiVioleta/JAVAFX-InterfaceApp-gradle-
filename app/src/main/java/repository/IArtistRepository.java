package repository;

public interface IArtistRepository<ID,T extends HasId<ID>> {
    T findOne(ID id);
    Iterable<T> findAll();
}
