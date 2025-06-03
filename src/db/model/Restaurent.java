package db.model;

import view.Page;
import view.View;

public class Restaurent {

    private static Restaurent instance;
    private View view;
    private Client client;

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

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Client getClient()
    {
        return this.client;
    }



}
