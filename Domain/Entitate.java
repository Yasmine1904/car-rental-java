package Domain;

import java.io.Serializable;

public abstract class Entitate implements Serializable {

    private static final long serialVersionUID = 1000L;

    protected int id;


    public Entitate(int id)
    {
        this.id=id;
    }

    public int getId()
    {
        return id;
    }
}
