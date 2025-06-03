package db.model;

abstract public class Model {
    public static final int UNREGISTRED = -1;

    protected int id = UNREGISTRED;

    /**
     * Récupérer l'Id d'un model
     * @return
     */
    public int getId() {
        return id;
    }

    public abstract String show();
    public abstract String showFromStorage();

    public boolean isStored(){
        return this.getId() > UNREGISTRED;
    }
}
