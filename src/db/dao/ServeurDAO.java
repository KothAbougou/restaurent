package db.dao;

import db.model.Serveur;

import java.util.ArrayList;
import java.util.List;

public class ServeurDAO extends DAO{ ;

    private static ServeurDAO instance;

    private ServeurDAO()
    {
        this.storage = "src/db/storage/serveur.txt";
    }

    public static ServeurDAO getInstance()
    {
        if(instance == null)
            instance = new ServeurDAO();
        return instance;
    }

    @Override
    public Serveur create() {
        return new Serveur();
    }


    @Override
    public Serveur getById(int id) {
        String[] ligne = this.read().get(id);

        return !ligne[0].equals(EMPTY_LINE) ?
                new Serveur(id, ligne) : null;
    }

    @Override
    public List<Serveur> getAll() {
        List<Serveur> serveurs = new ArrayList<>();

        int i = 0;
        for(String[] ligne : this.read()) {
            if(!ligne[0].equals(EMPTY_LINE) && !ligne[0].isEmpty())
                serveurs.add(new Serveur(i, ligne));
            i++;
        }

        return serveurs;
    }
}
