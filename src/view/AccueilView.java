package view;

public class AccueilView extends View{

    public AccueilView() {
        super();
        this.actions = new String[]{
            "Je suis Client",
            "Je suis Restaurateur"
        };
    }

    @Override
    protected void content() {
        System.out.println("Contenu de la page");
    }

    @Override
    protected void userAction(){
        int action = 0;
        while(action <= 0 || action > this.actions.length+1)
        {
            switch(action = Integer.parseInt(this.input()))
            {
                case 1: View.changeTo(Page.CLIENT); break;
                case 2: View.changeTo(Page.RESTAURATEUR); break;
                default: action = 0; break;
            }


        }
    }
}

