package Repository;

import Domain.Entitate;

import java.io.IOException;
import java.sql.SQLException;

public interface IDbRepository<T extends Entitate> extends IRepository<T>
{
    void connectToDb();
    void closeConnection();
    void createTable();
    void insertRandomData() throws DuplicateEntityException, IOException, SQLException;
}
