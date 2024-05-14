package Domain;

import java.time.LocalDate;
import java.util.Date;

public class InchiriereFactory implements IEntityFactory<Inchiriere>
{
    public Inchiriere createEntity(String line)
    {
        int id = Integer.parseInt(line.split(",")[0]);
        int idMasina = Integer.parseInt(line.split(",")[1]);
        String marca = line.split(",")[2];
        String model = line.split(",")[3];
        LocalDate dataInceput = LocalDate.parse(line.split(",")[4]);
        LocalDate dataSfarsit = LocalDate.parse(line.split(",")[5]);

        Masina masina = new Masina(idMasina, marca, model);
        return new Inchiriere(id, masina, dataInceput, dataSfarsit);
    }
}
