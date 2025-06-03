package db.dao;

import db.model.Client;
import db.model.Reservation;
import db.model.Table;

import java.util.ArrayList;
import java.util.List;

public class ReservationDAO extends DAO{

    private static ReservationDAO instance;

    private static TableDAO tableDAO = TableDAO.getInstance();

    private ReservationDAO()
    {
        this.storage = "src/db/storage/reservation.txt";
    }

    public static ReservationDAO getInstance()
    {
        if(instance == null)
            instance = new ReservationDAO();
        return instance;
    }

    @Override
    public Reservation create() {
        return new Reservation();
    }

    @Override
    public Reservation getById(int id) {
        String[] ligne = this.read().get(id);

        return !ligne[0].equals(EMPTY_LINE) ?
                new Reservation(id, ligne) : null;
    }

    public Reservation getByIdAndClient(int id, int idClient) {
        Reservation reservation = this.getById(id);

        return  reservation.getClient().getId() == idClient ?
                reservation : null;
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();

        int i = 0;
        for(String[] ligne : this.read()) {
            if(!ligne[0].equals(EMPTY_LINE) && !ligne[0].isEmpty())
                reservations.add(new Reservation(i, ligne));
            i++;
        }

        return reservations;
    }

    public List<Reservation> getAllByClientId(int idClient)
    {
        return this.getAll().stream()
                .filter(r -> r.isClient(idClient))
                .toList();
    }

    public int countClientPerBookedTable(int idTable) {
        List<Client> clients = this.getAll().stream()
                .filter(r -> r.isTable(idTable))
                .filter(r -> !r.isPaid())
                .map(Reservation::getClient)
                .distinct()
                .toList();

        return clients.size();
    }

    public Table getTableAvailableByCapacity(int capacite)
    {
        for(Table t : tableDAO.getTablesForNbPlaces(capacite))
            if(t.isFree())
                return t;

        return null;
    }
}
