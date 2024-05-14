package Teste;

import Domain.Inchiriere;
import Domain.Masina;

import java.time.LocalDate;

public class TestInchiriere {
    public TestInchiriere(){}
    public void getGetteri()
    {
        LocalDate dataInceput = LocalDate.of(2023,10,1);
        LocalDate dataSfarsit = LocalDate.of(2023,10,10);
        Masina masina = new Masina(1,"audi","a4");
        Inchiriere inchiriere = new Inchiriere(1, masina, dataInceput, dataSfarsit);
        assert inchiriere.getId() == 1;
        assert inchiriere.getMasina() == masina;
        assert inchiriere.getDataInceput() == dataInceput;
        assert inchiriere.getDataSfarsit() == dataSfarsit;
    }

    public void getSetteri()
    {
        LocalDate dataInceput = LocalDate.of(2023,10,1);
        LocalDate dataInceputNou = LocalDate.of(2024,10,1);
        LocalDate dataSfarsit = LocalDate.of(2023,10,10);
        LocalDate dataSfarsitNou = LocalDate.of(2024,10,10);
        Masina masina = new Masina(1,"audi","a4");
        Masina masinaNou = new Masina(2,"bmw","m3");
        Inchiriere inchiriere = new Inchiriere(1, masina, dataInceput, dataSfarsit);
        inchiriere.setMasina(masinaNou);
        inchiriere.setDataInceput(dataInceputNou);
        inchiriere.setDataSfarsit(dataSfarsitNou);
        assert inchiriere.getId() == 1;
        assert inchiriere.getMasina() == masinaNou;
        assert inchiriere.getDataInceput() == dataInceputNou;
        assert inchiriere.getDataSfarsit() == dataSfarsitNou;
    }
}
