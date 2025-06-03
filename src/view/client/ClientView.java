package view.client;


import db.model.Restaurent;
import view.Page;
import view.View;
import view.client.state.ClientViewState;
import view.client.state.ClientViewStateRegistered;
import view.client.state.ClientViewStateUnregistered;

public class ClientView extends View {

    ClientViewState clientViewState;

    public ClientView() {
        super();

        this.clientViewState = Restaurent.getInstance().getClient() != null ?
                new ClientViewStateRegistered() : new ClientViewStateUnregistered();

        this.actions = new String[]{
                "M'enregistrer",
                "Réserver une table",
                "Payer",
                "Retour à l'accueil"
        };
    }
    @Override
    protected void content() {
        System.out.println("Point de vue Client");
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
                case 4: View.changeTo(Page.ACCUEIL); break;
                default: action = 0; break;
            }


        }
    }
}
