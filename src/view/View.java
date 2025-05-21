package view;

import java.util.Scanner;

public abstract class View {
    protected String title;
    protected String[] actions = new String[]{};
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
            case Page.RESTAURATEUR -> new RestaurateurView();
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
        System.out.println("------ * NOUVELLE PAGE * ------");
        System.out.println(this.title);
        this.content();

        // Affichage de la liste des actions
        if(this.actions.length > 0)
        {
            System.out.println("--- * Actions * ---");
            for(int i=0; i < this.actions.length; i++)
                System.out.println(String.format("%d. %s", i+1, this.actions[i]));
        }
        System.out.println("--- * Input * ---");
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
        System.out.print(message);
        return this.input();
    }

    /**
     * Saisi d'un utilisateur
     */
    protected String input()
    {
        return this.input.nextLine();
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

}
