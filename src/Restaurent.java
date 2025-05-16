import view.Page;
import view.View;

public class Restaurent {

    private static Restaurent instance;
    private View view;

    private Restaurent(){}

    /**
     * Singleton
     */
    public static Restaurent getInstance(){
        if(instance == null)
            instance = new Restaurent();
        return instance;
    }

    public void changeView(Page page)
    {
        this.view = View.changeTo(page);
    }



}
