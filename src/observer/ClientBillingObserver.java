package observer;

import db.dao.ClientDAO;
import db.dao.ReservationDAO;
import db.model.Client;
import db.model.Reservation;

public class ClientBillingObserver implements TableBillingObserver{
    private final Client client;
    private final Reservation reservation;
    private static final ClientDAO clientDAO = ClientDAO.getInstance();
    private static final ReservationDAO reservationDAO = ReservationDAO.getInstance();

    public ClientBillingObserver(Client client, Reservation reservation) {
        this.client = client;
        this.reservation = reservation;
    }

    @Override
    public void onTableBilled() {
        if (!this.reservation.isPaid()) {
            float facture = -this.reservation.getPrix();
            if (this.client.isAbleToPay(Math.abs(facture))) {
                this.client.setSoldeBancaire(facture);
                clientDAO.save(client);
                this.reservation.setPaid(true);
                reservationDAO.save(reservation);
                System.out.println(this.client.getFullName() + " a payé sa réservation.");
            } else {
                System.out.println(this.client.getFullName() + " : solde insuffisant.");
            }
        }
    }
}
