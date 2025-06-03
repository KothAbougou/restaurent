package db.dao;

import db.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDAO extends DAO{

    private static MenuDAO instance;

    private MenuDAO()
    {
        this.storage = "src/db/storage/menu.txt";
    }

    public static MenuDAO getInstance()
    {
        if(instance == null)
            instance = new MenuDAO();
        return instance;
    }

    @Override
    public Menu create() {
        return new Menu();
    }

    @Override
    public Menu getById(int id) {
        String[] ligne = this.read().get(id);

        return !ligne[0].equals(EMPTY_LINE) ?
                new Menu(id, ligne) : null;
    }

    @Override
    public List<Menu> getAll() {
        List<Menu> menus = new ArrayList<>();

        int i = 0;
        for(String[] ligne : this.read()) {
            if(!ligne[0].equals(EMPTY_LINE) && !ligne[0].isEmpty())
                menus.add(new Menu(i, ligne));
            i++;
        }

        return menus;
    }
}
