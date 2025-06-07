package db.model;

import db.dao.ReservationDAO;
import db.dao.ServeurDAO;
import observer.TableBillingObserver;

import java.util.ArrayList;
import java.util.List;

public class Table extends Model{
    private int nbPlace;
    private int idServeur;

    private static final ServeurDAO serveurDAO = ServeurDAO.getInstance();
    private static final ReservationDAO reservationDAO = ReservationDAO.getInstance();

    private List<TableBillingObserver> observers = new ArrayList<>();

    public Table() {}
    public Table(int id, String... args)
    {
        this.id = id;
        this.nbPlace = Integer.parseInt(args[0]);
        this.idServeur = Integer.parseInt(args[1]);
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public Serveur getServeur()
    {
        return serveurDAO.getById(this.idServeur);
    }

    public void setServeur(Serveur serveur)
    {
        this.idServeur = serveur.getId();
    }

    public boolean isCapable(int nbPersonnes)
    {
        return this.nbPlace >= nbPersonnes;
    }

    @Override
    public String toString()
    {
        return String.format("%d;%d", this.nbPlace, this.idServeur);
    }

    @Override
    public String show() {
        return String.format("Table %d : [%d / %d places], %s", this.getId(), this.getNbClient(), this.nbPlace, this.getServeur().show());
    }

    private int getNbClient() {
        return reservationDAO.countClientPerBookedTable(this.getId());
    }

    @Override
    public String showFromStorage() {
        return String.format("%d. %d %d", this.id, this.nbPlace, this.idServeur);
    }

    public boolean isFree()
    {
        return this.getNbClient() == 0;
    }

    public float toBeBilled() {
        float toBeBilled = 0;

        for(Reservation reservation : reservationDAO.getAllUnpaidByIdTable(this.getId()))
            toBeBilled += reservation.getPrix();

        return toBeBilled;
    }

    public List<Reservation> getAllReservations()
    {
        return reservationDAO.getAllByIdTable(this.getId());
    }

    public void addBillingObserver(TableBillingObserver observer) {
        this.observers.add(observer);
    }

    public void clearBillingObservers() {
        this.observers.clear();
    }

    public void notifyBillingObservers() {
        for (TableBillingObserver observer : observers) {
            observer.onTableBilled();
        }
    }




}
