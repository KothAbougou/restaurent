package view;

public class RestaurateurView extends View{
    public RestaurateurView() {
        super();
        this.actions = new String[]{
            "Gérer les tables",
            "Gerer les stocks",
            "Gérer les menu",
            "Retour à l'accueil"
        };
    }
    @Override
    protected void content() {
        System.out.println("Point de vue Restaurateur");
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
