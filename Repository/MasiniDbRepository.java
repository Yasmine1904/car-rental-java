package Repository;

import Domain.Masina;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MasiniDbRepository extends MemoryRepository<Masina> implements IDbRepository<Masina> {
    private static String JDBC_URL = "jdbc:sqlite:masini.db";

    private Connection connection;

    public MasiniDbRepository() throws SQLException, IOException {
        connectToDb();
        //createTable();
        insertRandomData();
    }

    private static final String[] marci = {"Audi", "BMW", "Mercedes", "Volkswagen", "Opel", "Ford", "Renault", "Dacia", "Skoda", "Toyota", "Honda", "Mazda", "Hyundai", "Kia", "Fiat", "Alfa Romeo", "Lancia", "Volvo", "Saab", "Porsche", "Lamborghini", "Ferrari", "Maserati", "Bugatti", "Citroen", "Peugeot", "Seat", "Mini", "Jaguar", "Land Rover", "Lexus", "Jeep", "Suzuki", "Mitsubishi", "Subaru", "Chevrolet", "Chrysler", "Dodge", "Hummer", "Pontiac", "Tesla", "Aston Martin", "Bentley", "Lotus", "Rolls Royce", "Smart", "Daihatsu", "Daewoo", "Lada"};
    private static final String[] modele = {"alb", "negru", "rosu", "albastru", "frumos", "urat", "mic", "mare", "rapid", "lent", "verde", "galben", "portocaliu", "roz", "mov", "maro", "bej", "gri", "argintiu", "auriu", "bronz", "crem", "turcoaz", "visiniu", "lila", "indigo", "carmaziu", "hatzjohnule", "carmaziu", "hatz", "golden", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson"};



    public void connectToDb() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed())
            {
                this.clear();
                connection.close();
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try {
            connectToDb();
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS masini (id, marca nvarchar(50), model nvarchar(50))");
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void add(Masina entitate) throws IOException, SQLException {
        if(entitate == null)
        {
            throw new IllegalArgumentException("entitatea nu poate fi null");
        }

        if(find(entitate.getId()) != null)
        {
            throw new IllegalArgumentException("entitatea deja exista");
        }

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO masini VALUES (?, ?, ?)")) {
            statement.setInt(1, entitate.getId());
            statement.setString(2, entitate.getMarca());
            statement.setString(3, entitate.getModel());
            statement.executeUpdate();
        } catch (SQLException e)
        {
            throw new SQLException(e);
        }

    }

    public void modify(int id, Masina entitate) throws IOException
    {
        if(entitate == null)
        {
            throw new IllegalArgumentException("entitatea nu poate fi null");
        }

        if(find(entitate.getId()) == null)
        {
            throw new IllegalArgumentException("entitatea nu exista");
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE masini SET marca = ?, model = ? WHERE id = ?")) {
            statement.setString(1, entitate.getMarca());
            statement.setString(2, entitate.getModel());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id) throws IOException
    {


        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM masini WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Masina> getAll()
    {
        ArrayList<Masina> masini = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM masini")) {
            statement.execute();
            var result = statement.getResultSet();
            while (result.next()) {
                masini.add(new Masina(result.getInt("id"), result.getString("marca"), result.getString("model")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return masini;
    }

    public void insertRandomData() throws IOException, SQLException {
        if(getAll().size() == 0)
        {
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                String marca = marci[random.nextInt(marci.length)];
                String model = modele[random.nextInt(modele.length)];
                int randomIdMasina = i;
                add(new Masina(randomIdMasina, marca, model));
            }
        }
    }

    private void clear() throws IOException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM masini")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}




