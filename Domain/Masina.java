package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Masina extends Entitate implements Serializable {

    private static final long serialVersionUID = 1000L;
    String marca;
    String model;


    public Masina(int id)
    {
        super(id);
    }

    public Masina(int id, String marca, String model)
    {
        super(id);
        this.marca=marca;
        this.model=model;
    }

    public String getMarca()
    {
        return marca;
    }

    public void setMarca(String marca)
    {
        this.marca=marca;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    @Override
    public String toString()
    {
        return id +"," + marca + "," + model;
    }

}



