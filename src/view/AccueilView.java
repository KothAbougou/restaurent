package view;

public class AccueilView extends View{

    final String[] actions = {
            "Je suis Client",
            ""
    };

    @Override
    protected  void content() {
        System.out.println("1. Je suis Client");
        System.out.println("2. Je suis Restaurateur");
    }
}
