package view.client;

import db.dao.ClientDAO;
import db.dao.MenuDAO;
import db.dao.ReservationDAO;
import db.model.*;
import db.model.Reservation;
import view.Page;
import view.View;

import java.lang.invoke.Invokers$Holder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReserverTableView extends View {

    protected static Restaurent restaurent = Restaurent.getInstance();
    protected static ReservationDAO reservationDAO = ReservationDAO.getInstance();
    protected static ClientDAO clientDAO = ClientDAO.getInstance();
    protected static MenuDAO menuDAO = MenuDAO.getInstance();

    public ReserverTableView() {
        super();
        this.actions = new String[]{
            "Retour à votre page Client"
        };

        this.commandes.put("ADD     ", "Ajouter une réservation");
        this.commandes.put("DEL <id>", "Supprimer une réservation");
    }

    @Override
    protected void content() {
        this.showListReservationsClient();
    }

    @Override
    protected final void userAction() {
        String action = USER_ACTION;

        while(( isNumeric(action) && (Integer.parseInt(action) <= 0 || Integer.parseInt(action) > this.actions.length+1))
                || (!isNumeric(action) && action.isEmpty()))
        {
            action = this.input();

            if(isNumeric(action)) switch (Integer.parseInt(action))
            {
                case 1 -> View.changeTo(Page.CLIENT);
                default -> action = "";
            }
            else {
                String[] act = action.split(" ");
                String cmd = act[0];

                action = switch(cmd){
                    case "ADD" -> this.addNewReservation();
                    case "DEL" -> this.deleteReservation(Integer.parseInt(act[1]));
                    default -> USER_ACTION;
                };
            }
        }
    }

    private String deleteReservation(int id) {
        Reservation reservation = reservationDAO.getByIdAndClient(id, restaurent.getClient().getId());
        if(reservation != null)
        {
            reservationDAO.delete(reservation);
            System.out.println(reservation.showFromStorage() + " a bien été supprimé");
        }else System.out.println("Vous ne pouvez pas supprimer cette réservation.");

        return USER_ACTION;
    }

    private String addNewReservation() {
        Reservation reservation = reservationDAO.create();

        int nbPersonnes = Integer.parseInt(this.input("Réservation pour combien de personnes :"));
        System.out.println("Recherche de tables disponibles pour "+ nbPersonnes + " personnes ...");
        Table tableDisponible = reservationDAO.getTableAvailableByCapacity(nbPersonnes);

        if(tableDisponible != null)
        {
            System.out.println("Liste des menus");
            for(Menu m : menuDAO.getAll())
                System.out.println(m.showFromStorage());

            reservation.setClient(restaurent.getClient());
            reservation.addMenu(menuDAO.getById(Integer.parseInt(this.input("Quel menu choisissez-vous :"))));
            reservationDAO.save(reservation);

            for(int i=1; i < nbPersonnes; i++)
            {
                Reservation r = reservationDAO.create();

                System.out.println("Invité "+i+" :");
                Client c = clientDAO.create();
                c.setNom(this.input("Nom :"));
                c.setPrenom(this.input("Prénom :"));
                c.setSoldeBancaire(Float.parseFloat(this.input("Solde bancaire :")));

                c = (Client) clientDAO.flush(c);

                reservation.setClient(c);
                reservation.addMenu(menuDAO.getById(Integer.parseInt(this.input("Menu de l'invité :"))));
                reservationDAO.save(r);
            }

            System.out.println("Votre table a bien été réservé");

        }else System.out.println("Aucune réseration n'est possible maintenant");

        return USER_ACTION;
    }

    private void showListReservationsClient() {
        System.out.println(String.format("\tVous avez %d réservations", reservationDAO.getAllByClientId(restaurent.getClient().getId()).size()));
        for(Reservation r : reservationDAO.getAll())
            System.out.println("\t" + r.showFromStorage());
    }
}
