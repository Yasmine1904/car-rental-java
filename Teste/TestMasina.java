package Teste;

import Domain.Masina;

public class TestMasina
{
    public TestMasina() {}

    public void testGetteri()
    {
        Masina masina = new Masina(1, "bmw", "m5");
        assert masina.getId() == 1;
        assert masina.getMarca() == "bmw";
        assert masina.getModel() == "m5";
    }

    public void testSetteri()
    {
        Masina masina = new Masina(1, "bmw", "m5");
        masina.setModel("audi");
        masina.setMarca("a4");
        assert masina.getMarca() == "audi";
        assert masina.getModel() == "a4";
    }

}

