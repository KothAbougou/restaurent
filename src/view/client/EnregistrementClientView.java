package view.client;

import db.dao.ClientDAO;
import db.model.Client;
import db.model.Restaurent;
import view.Page;
import view.View;

public class EnregistrementClientView extends View {

    private static final ClientDAO clientDAO = ClientDAO.getInstance();
    private static final Restaurent restaurent = Restaurent.getInstance();

    @Override
    protected void content() {

    }

    @Override
    protected void userAction() {
        String nom = this.input("Votre nom :");
        String prenom = this.input("Votre prénom :");

        Client client;
        if((client = clientDAO.getByFullname(nom, prenom))!= null)
            restaurent.setClient(client);
        else{
            client = clientDAO.create();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setSoldeBancaire(Float.parseFloat(this.input("Votre solde bancaire :")));
            client = (Client) clientDAO.flush(client);
            restaurent.setClient(client);
        }

        View.changeTo(Page.CLIENT);

    }
}
