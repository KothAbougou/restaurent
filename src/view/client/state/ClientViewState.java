package view.client.state;

import view.View;

public interface ClientViewState {
    public View changeViewToPageEnregistrementClient();
    public View changeViewToPageReserverTable();
    public View changeViewToPagePayer();
    public View changeViewToEndSession();
}
