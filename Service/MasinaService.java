package Service;

import Domain.Masina;
import Repository.DuplicateEntityException;
import Repository.IRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


public class MasinaService
{
    IRepository<Masina> repository;

    public MasinaService(IRepository<Masina> repository)
    {
        this.repository=repository;
    }

    public int size()
    {
        return repository.size();
    }

    public void add(int id, String marca, String model) throws IOException, SQLException
    {
        repository.add(new Masina(id, marca, model));
    }

    //    public void modify(int id, String marcaNoua, String modelNou)
    //    {
    //        domeniu.Masina masina = readMasina(id);
    //        if(readMasina(id) !=null)
    //        {
    //            masina.setMarca(marcaNoua);
    //            masina.setModel(modelNou);
    //        }
    //        else
    //            throw new IllegalArgumentException("masina nu exista");
    //}

    public void modify(int id, Masina masinaNoua) throws IOException {
        if(readMasina(id) != null)
            repository.modify(id, masinaNoua);
        else
            throw new IllegalArgumentException("masina nu exista");
    }
    public void remove(int id) throws IOException
    {
        if(readMasina(id) != null)
            repository.remove(id);
        else
            throw new IllegalArgumentException("masina nu exista");
    }

    public Collection<Masina> getAll()
    {
        return repository.getAll();
    }

    public Masina readMasina(int id)
    {
        return repository.find(id);
    }

}
