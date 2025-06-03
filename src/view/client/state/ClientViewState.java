package view.client.state;

import view.View;

public interface ClientViewState {
    public ClientViewState changeToRegisteredState();
    public ClientViewState changeToUnRegisteredState();
    public View changeViewToPageEnregistrementClient();
    public View changeViewToPageReserverTable();
    public View changeViewToPagePayer();
}
