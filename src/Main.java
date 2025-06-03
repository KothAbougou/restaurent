import db.model.Restaurent;
import view.Page;

public class Main {
    public static void main(String[] args) {
        Restaurent resto = Restaurent.getInstance();
        resto.changeView(Page.ACCUEIL);
    }
}
