package view.client;


import db.model.Restaurent;
import view.Page;
import view.View;
import view.client.state.ClientViewState;
import view.client.state.ClientViewStateRegistered;
import view.client.state.ClientViewStateUnregistered;

public class ClientView extends View {

    private final ClientViewState clientViewState;
    private static final Restaurent restaurent = Restaurent.getInstance();

    public ClientView() {
        super();

        clientViewState = restaurent.issetSession() ?
                new ClientViewStateRegistered() : new ClientViewStateUnregistered();

        this.actions = new String[]{
                "M'enregistrer",
                "Réserver une table",
                "Payer",
                "Fermer ma session",
                "Retour à l'accueil"
        };
    }
    @Override
    protected void content() {
        if(restaurent.issetSession())
            System.out.println("\tBienvenue " + restaurent.getClient().getFullName() + " !");
    }

    @Override
    protected void userAction(){
        int action = 0;
        while(action <= 0 || action > this.actions.length+1)
        {
            switch(action = Integer.parseInt(this.input()))
            {
                case 1: if(this.clientViewState.changeViewToPageEnregistrementClient() == null) action = 0; break;
                case 2: if(this.clientViewState.changeViewToPageReserverTable() == null) action = 0; break;
                case 3: if(this.clientViewState.changeViewToPagePayer() == null) action = 0; break;
                case 4: if(this.clientViewState.changeViewToEndSession() == null) action = 0; break;
                case 5: View.changeTo(Page.ACCUEIL); break;
                default: action = 0; break;
            }
        }
    }
}
