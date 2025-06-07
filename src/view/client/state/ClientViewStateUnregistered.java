package view.client.state;

import view.Page;
import view.View;

public class ClientViewStateUnregistered implements ClientViewState{

    @Override
    public View changeViewToPageEnregistrementClient() {
        return View.changeTo(Page.ENREGISTREMENT_CLIENT);
    }

    @Override
    public View changeViewToPageReserverTable() {
        System.out.println("Veuillez vous enregistrer avant.");
        return null;
    }

    @Override
    public View changeViewToPagePayer() {
        System.out.println("Veuillez vous enregistrer avant.");
        return null;
    }

    @Override
    public View changeViewToEndSession() {
        System.out.println("Vous n'êtes pas enregistré.");
        return null;
    }
}
