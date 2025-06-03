package db.dao;

import db.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockDAO extends DAO{

    private static StockDAO instance;

    private StockDAO()
    {
        this.storage = "src/db/storage/stock.txt";
    }

    public static StockDAO getInstance()
    {
        if(instance == null)
            instance = new StockDAO();
        return instance;
    }

    @Override
    public Stock create() {
        return new Stock();
    }

    @Override
    public Stock getById(int id) {
        String[] ligne = this.read().get(id);

        return !ligne[0].equals(EMPTY_LINE) ?
                new Stock(id, ligne) : null;
    }

    @Override
    public List<Stock> getAll() {
        List<Stock> stocks = new ArrayList<>();

        int i = 0;
        for(String[] ligne : this.read()) {
            if(!ligne[0].equals(EMPTY_LINE) && !ligne[0].isEmpty())
                stocks.add(new Stock(i, ligne));
            i++;
        }

        return stocks;
    }
}
