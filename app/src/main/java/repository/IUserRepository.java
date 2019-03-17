package repository;

public interface IUserRepository<ID, T extends HasId<ID>> {
    T findOne(String nume,String pass);
}