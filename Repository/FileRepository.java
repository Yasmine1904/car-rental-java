package Repository;

import Domain.Entitate;
import Domain.IEntityFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class FileRepository<T extends Entitate> extends MemoryRepository<T> implements IRepository<T>
{
    private String fileName;
    private IEntityFactory<T> entityFactory;

    public FileRepository(String fileName, IEntityFactory<T> entityFactory) throws IOException, DuplicateEntityException, SQLException {
        this.fileName = fileName;
        this.entityFactory = entityFactory;
        readFromFile();
    }

    private void readFromFile() throws IOException, DuplicateEntityException, SQLException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            T entity = entityFactory.createEntity(line);
            super.add(entity);
        }
    }
    @Override
    public void add(T entitate) throws IOException, SQLException {
        super.add(entitate);
        Writer wr = new FileWriter(this.fileName, true);
        wr.write(String.valueOf(entitate));
        wr.write("\r\n");
        wr.flush();
        wr.close();
    }
    @Override
    public void modify(int id, T entitate) throws IOException {
        super.modify(id, entitate);
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        String fileContent = "";
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if(line.startsWith(String.valueOf(id)))
            {
                line = String.valueOf(entitate);
            }
            fileContent += line + "\r\n";
        }
        FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
    }
    @Override
    public void remove(int id) throws IOException {
        super.remove(id);
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        String fileContent = "";
        while(scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if(!line.startsWith(String.valueOf(id)))
            {
                fileContent += line + "\r\n";
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
    }



}
