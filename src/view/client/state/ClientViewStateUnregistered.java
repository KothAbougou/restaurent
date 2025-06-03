package view.client.state;

import view.Page;
import view.View;

public class ClientViewStateUnregistered implements ClientViewState{
    @Override
    public ClientViewState changeToRegisteredState() {
        return new ClientViewStateRegistered();
    }

    @Override
    public ClientViewState changeToUnRegisteredState() {
        return this;
    }

    @Override
    public View changeViewToPageEnregistrementClient() {
        return View.changeTo(Page.ENREGISTREMENT_CLIENT);
    }

    @Override
    public View changeViewToPageReserverTable() {
        return null;
    }

    @Override
    public View changeViewToPagePayer() {
        return null;
    }
}
