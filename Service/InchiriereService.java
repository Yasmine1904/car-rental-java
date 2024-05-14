package Service;

import Domain.Inchiriere;
import Domain.Masina;
import Repository.DuplicateEntityException;
import Repository.IRepository;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.sql.SQLException;
import java.util.Collection;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class InchiriereService
{
    IRepository<Inchiriere> repository;

    public InchiriereService(IRepository<Inchiriere> repository)
    {
        this.repository=repository;
    }

    public int size()
    {
        return repository.size();
    }

    public void add(int id, Masina masina, LocalDate dataInceput, LocalDate dataSfarsit) throws DuplicateEntityException, IOException, SQLException {
        repository.add(new Inchiriere(id, masina, dataInceput, dataSfarsit));
    }

//    public void modify(int id, domeniu.Masina masinaNou, LocalDate dataInceputNou, LocalDate dataSfarsitNou) throws DuplicateEntityException
//    {
//        domeniu.Inchiriere inchiriere = readInchiriere(id);
//        if(readInchiriere(id) != null)
//        {
//            inchiriere.setMasina(masinaNou);
//            inchiriere.setDataInceput(dataInceputNou);
//            inchiriere.setDataSfarsit(dataSfarsitNou);
//        }
//        else
//            throw new IllegalArgumentException("inchirierea nu exista");
//    }

    public void modify(int id, Inchiriere inchiriere) throws IOException, DuplicateEntityException {

        if(readInchiriere(id) != null)
            repository.modify(id, inchiriere);
        else
            //throw new DuplicateEntityException("inchirierea nu exista");
            throw new IllegalArgumentException("inchirierea nu exista");
    }



    public void remove(int id) throws IOException, DuplicateEntityException {
        if(readInchiriere(id) != null)
            repository.remove(id);
        else
            //throw new DuplicateEntityException("inchirierea nu exista");
            throw new IllegalArgumentException("inchirierea nu exista");
    }

    public Inchiriere readInchiriere(int id)
    {
        return repository.find(id);
    }

    public Collection<Inchiriere> getAll()
    {
        return repository.getAll();
    }

    public void afisareMasiniCuNrInchirieri()
    {
        Collection<Inchiriere> inchirieri = getAll();
        Map<Masina, Long> masiniCuNrInchirieri = inchirieri.stream()
                .collect(Collectors.groupingBy(Inchiriere::getMasina, Collectors.counting()));

        masiniCuNrInchirieri.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getMarca() + ", " + entry.getKey().getModel(),
                        Map.Entry::getValue, Long::sum)) // combinarea valorilor duplicate
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    String masina = entry.getKey();
                    Long numarInchirieri = entry.getValue();
                    System.out.println(masina + " - Numar total de inchirieri: " + numarInchirieri);
                });
    }

}
