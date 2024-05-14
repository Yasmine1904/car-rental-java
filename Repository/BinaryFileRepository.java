package Repository;

import Domain.Entitate;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BinaryFileRepository<T extends Entitate & Serializable> implements IRepository<T>
{
    private final String fileName;

    public BinaryFileRepository(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void add(T entity) throws IOException
    {
        List<T> entities = loadEntities();
        if (entity == null)
        {
            throw new IllegalArgumentException("Entitatea nu poate fi null");
        }

        if (find(entity.getId()) != null)
        {
            throw new IllegalArgumentException("Entitatea deja există");
        }

        entities.add(entity);
        saveEntities(entities);
    }

    @Override
    public void modify(int id, T entity) throws IOException
    {
        List<T> entities = loadEntities();
        if (entity == null)
        {
            throw new IllegalArgumentException("Entitatea nu poate fi null");
        }

        if (find(entity.getId()) == null)
        {
            throw new IllegalArgumentException("Entitatea nu există");
        }

        for (int i = 0; i < entities.size(); i++)
        {
            if (entities.get(i).getId() == id)
            {
                entities.set(i, entity);
                break;
            }
        }

        saveEntities(entities);
    }

    @Override
    public void remove(int id)
    {
        List<T> entities = loadEntities();
        boolean removed = entities.removeIf(e -> e.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Entitatea nu există");
        }
        saveEntities(entities);
    }

    @Override
    public T find(int id)
    {
        List<T> entities = loadEntities();
        return entities.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Collection<T> getAll()
    {
        return new ArrayList<T>(loadEntities());
    }

    @Override
    public int size()
    {
        List<T> entities = loadEntities();
        return entities.size();
    }

    private void saveEntities(List<T> entities)
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            outputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<T> loadEntities()
    {
        List<T> entities = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName)))
        {
            entities = (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println("suntem prosti");
        }
        return entities;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayList<T>(loadEntities()).iterator();
    }

    public void setAll(Collection<T> entitati)
    {;
    }
}