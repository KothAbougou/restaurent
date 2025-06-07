package view.client.state;

import db.model.Restaurent;
import view.Page;
import view.View;

public class ClientViewStateRegistered implements ClientViewState{

    private static final Restaurent restaurent = Restaurent.getInstance();

    @Override
    public View changeViewToPageEnregistrementClient() {
        System.out.println("Vous êtes déjà enregistré");
        return null;
    }

    @Override
    public View changeViewToPageReserverTable() {
        return View.changeTo(Page.RESERVER_TABLE);
    }

    @Override
    public View changeViewToPagePayer() {
        return View.changeTo(Page.PAYER);
    }

    @Override
    public View changeViewToEndSession() {
        System.out.println("Fin de votre session...");
        restaurent.endSession();
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        return View.changeTo(Page.ACCUEIL);

    }
}
