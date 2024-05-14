package Teste;

import Domain.Masina;
import org.junit.Test;
import Repository.DuplicateEntityException;
import Repository.MemoryRepository;
import Service.MasinaService;

import java.io.IOException;
import java.sql.SQLException;


public class TestServiceMasini
{
    public TestServiceMasini() {
    }

    @Test
    public void testAdd() throws DuplicateEntityException, IOException, SQLException {
        MemoryRepository<Masina> masinaMemoryRepository = new MemoryRepository<Masina>();
        MasinaService masinaService = new MasinaService(masinaMemoryRepository);
        masinaService.add(1, "audi", "a4");
        assert masinaService.size() == 1;
    }
    @Test
    public void testModify() throws DuplicateEntityException, IOException, SQLException {
        MemoryRepository<Masina> masinaMemoryRepository = new MemoryRepository<Masina>();
        MasinaService masinaService = new MasinaService(masinaMemoryRepository);
        masinaService.add(1, "audi", "a4");
        Masina masina = new Masina(1, "bmw", "m3");
        masinaService.modify(1,masina);
        Masina masinaVerif = masinaService.readMasina(1);
        assert masinaVerif == masina;
    }

    public void testRemove() throws DuplicateEntityException, IOException, SQLException {
        MemoryRepository<Masina> masinaMemoryRepository = new MemoryRepository<Masina>();
        MasinaService masinaService = new MasinaService(masinaMemoryRepository);
        masinaService.add(1, "audi", "a4");
        masinaService.remove(1);
        assert masinaService.size() == 0;
    }

    public void testReadMasina() throws DuplicateEntityException, IOException, SQLException {
        MemoryRepository<Masina> masinaMemoryRepository = new MemoryRepository<Masina>();
        MasinaService masinaService = new MasinaService(masinaMemoryRepository);
        masinaService.add(1,"bmw", "m3");
        Masina masina = new Masina(1, "bmw", "m3");
        Masina masinaVerif = masinaService.readMasina(1);
        assert masinaVerif == masina;
    }



}
