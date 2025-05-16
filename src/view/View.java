package view;

public abstract class View {
    protected String title;
    protected String[] actions;


    public static View changeTo(Page page)
    {
        View view = switch (page){
            case Page.ACCUEIL -> new AccueilView();
            default -> new AccueilView();
        };

        view.setTitle(page.getName());
        view.render();

        return view;
    }

    protected abstract void content();

    public void render()
    {
        System.out.println("------ * ------");
        System.out.println(this.title);
        System.out.println("------ * ------");
        this.content();
        System.out.println("--- * Actions * ---");

    }

    public void setTitle(String title)
    {
        this.title = title;
    }

}
