package db.dao;

import db.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO{

    private static ClientDAO instance;

    private ClientDAO()
    {
        this.storage = "src/db/storage/client.txt";
    }

    public static ClientDAO getInstance()
    {
        if(instance == null)
            instance = new ClientDAO();
        return instance;
    }

    @Override
    public Client create() {
        return new Client();
    }

    @Override
    public Client getById(int id) {
        String[] ligne = this.read().get(id);

        return !ligne[0].equals(EMPTY_LINE) ?
                new Client(id, ligne) : null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();

        int i = 0;
        for(String[] ligne : this.read()) {
            if(!ligne[0].equals(EMPTY_LINE) && !ligne[0].isEmpty())
                clients.add(new Client(i, ligne));
            i++;
        }

        return clients;
    }
}
