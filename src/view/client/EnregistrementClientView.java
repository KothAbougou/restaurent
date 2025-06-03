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

public class EnregistrementClientView extends View {

    protected static ClientDAO clientDAO = ClientDAO.getInstance();
    protected static Restaurent restaurent = Restaurent.getInstance();


    public ReserverTableView() {
        super();
        this.actions = new String[]{
            "Retour à votre page Client"
        };

        this.commandes.put("REGISTER", "S'enregistrer");
    }

    @Override
    protected void content() {}

    @Override
    protected final void userAction() {
        System.out.println("Identification");
        String nom = this.input("Nom :");
        String prenom = this.input("Prénom : ");

        Client c = clientDAO.findClientByFullname()
    }
}
