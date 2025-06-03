package view.client.state;

import view.Page;
import view.View;

public class ClientViewStateRegistered implements ClientViewState{
    @Override
    public ClientViewState changeToRegisteredState() {
        return this;
    }

    @Override
    public ClientViewState changeToUnRegisteredState() {
        return new ClientViewStateUnregistered();
    }

    @Override
    public View changeViewToPageEnregistrementClient() {
        return null;
    }

    @Override
    public View changeViewToPageReserverTable() {
        return View.changeTo(Page.RESERVER_TABLE);
    }

    @Override
    public View changeViewToPagePayer() {
        return View.changeTo(Page.PAYER);
    }
}
