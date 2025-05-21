package view.client;

import view.Page;
import view.View;

public class ClientView extends View {

    public ClientView() {
        super();
        this.actions = new String[]{
                "Réserver une table",
                "Passer commande",
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
                case 4: View.changeTo(Page.ACCUEIL); break;
                default: action = 0; break;
            }


        }
    }
}
