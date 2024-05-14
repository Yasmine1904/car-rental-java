package Domain;
import com.sun.tools.javac.Main;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Inchiriere extends Entitate implements Serializable {

    private static final long serialVersionUID = 1000L;
    Masina masina;
    LocalDate dataInceput;
    LocalDate dataSfarsit;

    public Inchiriere(int id, Masina masina, LocalDate dataInceput, LocalDate dataSfarsit)
    {
        super(id);
        this.masina=masina;
        this.dataInceput=dataInceput;
        this.dataSfarsit=dataSfarsit;
    }

    public Masina getMasina()
    {
        return masina;
    }

    public void setMasina(Masina masina)
    {
        this.masina=masina;
    }
    public LocalDate getDataInceput()
    {
        return dataInceput;
    }
    public LocalDate getDataSfarsit()
    {
        return dataSfarsit;
    }

    public void setDataInceput(LocalDate dataInceput)
    {
        this.dataInceput=dataInceput;
    }

    public void setDataSfarsit(LocalDate dataSfarsit)
    {
        this.dataSfarsit=dataSfarsit;
    }


    @Override
    public String toString()
    {
        return id + "," + masina + ","
                + dataInceput + "," + dataSfarsit;
    }
}
