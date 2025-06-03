package db.model;

import db.dao.ClientDAO;
import db.dao.MenuDAO;
import db.dao.TableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reservation extends Model{

    private int idTable;
    private int idClient;
    private boolean paid = false;
    private List<Integer> idMenus;

    private static final ClientDAO clientDAO = ClientDAO.getInstance();
    private static final TableDAO tableDAO = TableDAO.getInstance();
    private static final MenuDAO menuDAO = MenuDAO.getInstance();

    public Reservation(){
        this.idMenus = new ArrayList<>();
    }
    public Reservation(int id, String... args)
    {
        this.id = id;
        this.idTable = Integer.parseInt(args[0]);
        this.idClient = Integer.parseInt(args[1]);
        this.paid = Boolean.parseBoolean(args[2]);

        this.idMenus = new ArrayList<>();
        for(int i=3; i < args.length; i++)
            this.idMenus.add(Integer.parseInt(args[i]));
    }

    public Table getTable()
    {
        return tableDAO.getById(this.idTable);
    }

    public void setTable(Table table)
    {
        this.idTable = table.getId();
    }

    public Client getClient()
    {
        return clientDAO.getById(this.idClient);
    }

    public void setClient(Client client)
    {
        this.idClient = client.getId();
    }

    public List<Menu> getMenu()
    {
        List<Menu> menus = new ArrayList<>();

        for(int idMenu : this.idMenus)
            menus.add(menuDAO.getById(idMenu));

        return menus;
    }

    public void addMenu(Menu menu)
    {
        this.idMenus.add(menu.getId());
    }

    public float getPrix()
    {
        float prix = 0;
        for(Menu menu : this.getMenu())
            prix += menu.getPrix();

        return prix;
    }

    public boolean isPaid()
    {
        return this.paid;
    };

    public boolean isTable(int idTable)
    {
        return this.idTable == idTable;
    }

    public void setPaid(boolean paid)
    {
        this.paid = paid;
    }

    @Override
    public String toString()
    {
        return String.format("%d;%d;%b;%s", this.idTable, this.idClient, this.paid, this.idMenus.stream().map(String::valueOf).collect(Collectors.joining(";")));
    }

    @Override
    public String show() {
        String done = this.isPaid() ? "payé" : "en cours";
        return String.format("Réservation : Table %d par %s, %.2f € [%s]", this.idTable, this.getClient().getFullName(), this.getPrix(), done);
    }

    @Override
    public String showFromStorage() {
        return String.format("%d. T%d %s %b", this.getId(), this.idTable, this.getClient().getFullName(), this.paid);
    }

    public boolean isClient(int idClient) {
        return this.idClient == idClient;
    }
}
