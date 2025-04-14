package org.Emberalive.user.DeleteTicket;

public class DeleteTicketModel {
    DeleteTicketView deleteTicketView;
    public DeleteTicketModel(int ticketID, String status) {
        this.deleteTicketView = new DeleteTicketView(ticketID);
    }

    public void startGUI () {
        this.deleteTicketView.setVisible(true);
    }
}
