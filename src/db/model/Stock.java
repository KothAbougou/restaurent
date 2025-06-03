package db.model;

public class Stock extends Model{
    private String typeProduit;
    private String produit;
    private int qte = 0;

    public Stock(){}
    public Stock(int id, String... args)
    {
        this.id = id;
        this.typeProduit = args[0];
        this.produit = args[1];
        this.qte = Integer.parseInt(args[2]);
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public String commercialName()
    {
        return String.format("%s, %s", this.produit, this.typeProduit);
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int delta) {
        this.qte += delta;
    }

    @Override
    public String toString()
    {
        return String.format("%s;%s;%d", this.typeProduit, this.produit, this.qte);
    }

    @Override
    public String show() {
        return String.format("%d\t%s, %s", this.qte, this.produit, this.typeProduit);
    }

    @Override
    public String showFromStorage() {
        return String.format("%d. %s : %d %s", this.getId(), this.typeProduit, this.qte, this.produit);
    }
}
