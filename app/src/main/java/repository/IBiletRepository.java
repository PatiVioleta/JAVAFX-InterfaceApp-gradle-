package repository;

public interface IBiletRepository<ID,T extends HasId<ID>> {
    void save(T entity);
}
