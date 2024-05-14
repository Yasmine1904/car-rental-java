package Domain;
public class MasinaFactory implements IEntityFactory<Masina>
{
    public Masina createEntity(String line)
    {
        int id = Integer.parseInt(line.split(",")[0]);
        String marca = line.split(",")[1];
        String model = line.split(",")[2];

        return new Masina(id, marca, model);
    }
}





