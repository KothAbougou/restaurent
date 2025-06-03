package db.dao;

import db.model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableDAO extends DAO{

    private static TableDAO instance;

    private TableDAO()
    {
        this.storage = "src/db/storage/table.txt";
    }

    public static TableDAO getInstance()
    {
        if(instance == null)
            instance = new TableDAO();
        return instance;
    }

    @Override
    public Table create() {
        return new Table();
    }

    @Override
    public Table getById(int id) {
        String[] ligne = this.read().get(id);

        return !ligne[0].equals(EMPTY_LINE) ?
                new Table(id, ligne) : null;
    }

    @Override
    public List<Table> getAll() {
        List<Table> tables = new ArrayList<>();

        int i = 0;
        for(String[] ligne : this.read()) {
            if(!ligne[0].equals(EMPTY_LINE) && !ligne[0].isEmpty())
                tables.add(new Table(i, ligne));
            i++;
        }

        return tables;
    }

    public int getCapaciteTotale(){
        int capacite = 0;
        for(Table t : this.getAll())
            capacite += t.getNbPlace();
        return capacite;
    }

    public List<Table> getTablesForNbPlaces(int nbPlaces)
    {
        return this.getAll().stream()
                .filter(t -> t.isCapable(nbPlaces))
                .toList();
    }
}
