package Repository;

import Domain.Inchiriere;
import Domain.Masina;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class InchirieriDbRepository extends MemoryRepository<Inchiriere>
{
    private String JDBC_URL = "jdbc:sqlite:inchirieri.db";

    private Connection connection;

    public InchirieriDbRepository()
    {
        connectToDb();
        createTable();
    }

    public void connectToDb()
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try
        {
            if(connection == null || connection.isClosed())
            {
                connection = ds.getConnection();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection()
    {
        try
        {
            if(connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void createTable()
    {
        try
        {
            String sql = "CREATE TABLE IF NOT EXISTS Inchirieri (id, idMasina, numeMarca nvarchar(50), numeModel nvarchar(50), dataInceput nvarchar(50), dataSfarsit nvarchar(50));";



            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void add(Inchiriere entitate) throws  IOException {
        if(entitate == null)
        {
            throw new IllegalArgumentException("entitatea nu poate fi null");
        }

        if(find(entitate.getId()) != null)
        {
            throw new IllegalArgumentException("entitatea deja exista");
        }

        try(PreparedStatement stmt = connection.prepareStatement("INSERT INTO Inchirieri (id, idMasina, numeMarca, numeModel, dataInceput, dataSfarsit) VALUES (?, ?, ?, ?, ?, ?);"))
        {

            stmt.setInt(1, entitate.getId());
            stmt.setInt(2, entitate.getMasina().getId());
            stmt.setString(3, entitate.getMasina().getMarca());
            stmt.setString(4, entitate.getMasina().getModel());
            stmt.setString(5, entitate.getDataInceput().toString());
            stmt.setString(6, entitate.getDataSfarsit().toString());
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    public void modify(int id, Inchiriere entitate) throws IOException
    {
        if(entitate == null)
        {
            throw new IllegalArgumentException("entitatea nu poate fi null");
        }

        if(find(entitate.getId()) == null)
        {
            throw new IllegalArgumentException("entitatea nu exista");
        }

        try
        {
            String sql = "UPDATE Inchirieri SET idMasina = ?, numeMarca = ?, numeModel = ?, dataInceput = ?, dataSfarsit = ? WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, entitate.getMasina().getId());
            stmt.setString(2, entitate.getMasina().getMarca());
            stmt.setString(3, entitate.getMasina().getModel());
            stmt.setString(4, entitate.getDataInceput().toString());
            stmt.setString(5, entitate.getDataSfarsit().toString());
            stmt.setInt(6, id);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id) throws IOException
    {
        try
        {
            String sql = "DELETE FROM Inchirieri WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Inchiriere> getAll()
    {
        ArrayList<Inchiriere> inchirieri = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM Inchirieri;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            var result = stmt.getResultSet();
            while(result.next())
            {
                int id = result.getInt("id");
                int idMasina = result.getInt("idMasina");
                String numeMarca = result.getString("numeMarca");
                String numeModel = result.getString("numeModel");
                LocalDate dataInceput = LocalDate.parse(result.getString("dataInceput"));
                LocalDate dataSfarsit = LocalDate.parse(result.getString("dataSfarsit"));
                inchirieri.add(new Inchiriere(id, new Masina(idMasina, numeMarca, numeModel), dataInceput, dataSfarsit));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return inchirieri;
    }


}
