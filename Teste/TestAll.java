package Teste;
import org.junit.Test;
import Repository.DuplicateEntityException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class TestAll
{
    @Test
    public void testAll() throws DuplicateEntityException, ParseException, IOException, SQLException {
        TestInchiriere testInchiriere = new TestInchiriere();
        testInchiriere.getGetteri();
        testInchiriere.getSetteri();
        TestMasina testMasina = new TestMasina();
        testMasina.testGetteri();
        testMasina.testSetteri();
        TestRepo testRepo = new TestRepo();
        testRepo.testAdd();
        testRepo.testFind();
        testRepo.testRemove();
        TestServiceMasini testServiceMasini = new TestServiceMasini();
        testServiceMasini.testAdd();
        testServiceMasini.testModify();
        testServiceMasini.testReadMasina();
        testServiceMasini.testRemove();
        TestServiceInchirieri testServiceInchirieri = new TestServiceInchirieri();
        testServiceInchirieri.testAdd();
        testServiceInchirieri.testModify();
        testServiceInchirieri.testRemove();
        testServiceInchirieri.testReadInchiriere();
        System.out.println("testele sunt bune!");
    }
}

