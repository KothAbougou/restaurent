import db.dao.ReservationDAO;
import db.model.Restaurent;
import view.Page;

public class Main {
    public static final ReservationDAO  reservationDAO = ReservationDAO.getInstance();
    public static void main(String[] args) {
        run();
//        test();
    }

    public static void run()
    {
        Restaurent resto = Restaurent.getInstance();
        resto.changeView(Page.ACCUEIL);
    }

    public static void test()
    {
        System.out.println(reservationDAO.getTableAvailableByCapacity(6).showFromStorage());
    }
}
