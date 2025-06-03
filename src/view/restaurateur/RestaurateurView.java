package view.restaurateur;

import view.Page;
import view.View;

public class RestaurateurView extends View {
    public RestaurateurView() {
        super();
        this.actions = new String[]{
            "Gérer les tables",
            "Gerer les stocks",
            "Gérer les menu",
            "Gérer les Serveurs",
            "Retour à l'accueil"
        };
    }
    @Override
    protected void content() { }

    @Override
    protected void userAction(){
        int action = 0;
        while(action <= 0 || action > this.actions.length+1)
        {
            switch(action = Integer.parseInt(this.input()))
            {
                case 1: View.changeTo(Page.TABLE_MANAGER);
                case 2: View.changeTo(Page.STOCK_MANAGER);
                case 3: View.changeTo(Page.MENU_MANAGER);
                case 4: View.changeTo(Page.SERVEURS_MANAGER);
                case 5: View.changeTo(Page.ACCUEIL); break;
                default: action = 0; break;
            }


        }
    }
}
