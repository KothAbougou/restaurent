package view;

import view.client.ClientView;
import view.client.EnregistrementClientView;
import view.client.PayerView;
import view.client.ReserverTableView;
import view.restaurateur.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class View {
    protected static final String USER_ACTION = "";
    protected static final String INPUT_YES = "y";
    protected static final String INPUT_NO = "n";
    protected static final String INPUT_QUESTION = "?";
    protected String title;
    protected String[] actions = new String[]{};
    protected HashMap<String, String> commandes = new HashMap<>();
    protected Scanner input;

    public View()
    {
        this.input = new Scanner(System.in);
    }

    /**
     * Changer de vue
     * @param page
     */
    public static View changeTo(Page page)
    {
        View view = switch (page){
            case Page.ACCUEIL -> new AccueilView();

            case Page.CLIENT -> new ClientView();
            case Page.ENREGISTREMENT_CLIENT ->  new EnregistrementClientView();
            case Page.RESERVER_TABLE ->  new ReserverTableView();
            case Page.PAYER ->  new PayerView();

            case Page.RESTAURATEUR -> new RestaurateurView();
            case Page.SERVEURS_MANAGER -> new ServeurManagerView();
            case Page.TABLE_MANAGER -> new TableManagerView();
            case Page.STOCK_MANAGER -> new StockManagerView();
            case Page.MENU_MANAGER -> new MenuManagerView();
            default -> new AccueilView();
        };

        view.setTitle(page.getName());
        view.render();

        return view;
    }

    protected abstract void content();

    /**
     * Rendu visuel d'une page
     */
    public void render()
    {
        // Contenu de la page
        System.out.println();
        System.out.println("------ * NOUVELLE PAGE * ------");
        System.out.println(" # " + this.title);
        this.content();

        // Affichage de la liste des actions
        if(this.actions.length > 0)
        {
            System.out.println("-------------------------------");
            for(int i=0; i < this.actions.length; i++)
                System.out.println(String.format("\t%d. %s", i+1, this.actions[i]));

            for (Map.Entry<String, String> entry : this.commandes.entrySet())
                System.out.println("\t" + entry.getKey() + " \t " + entry.getValue());
        }
        System.out.println("-------------------------------");
        this.userAction();

    }

    /**
     * Les actions de l'utilisateur
     */
    protected abstract void userAction();

    /**
     * Saisi d'un utilisateur avec message
     * @param message Le message avant la saisis
     */
    protected String input(String message)
    {
        System.out.print(message + " ");
        return this.input.nextLine();
    }

    /**
     * Saisi d'un utilisateur
     */
    protected String input()
    {
        System.out.print(">> ");
        return this.input.nextLine();
    }

    /**
     * Change le titre d'une vue
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Vérifie la saisie utilisateur
     * @param str
     * @return
     */
    protected static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}
