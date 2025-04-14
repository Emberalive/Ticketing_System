package org.Emberalive.user.search;

public class userSearchTicketModel {
    public userSearchTicket StartGUI() {
        userSearchTicket view = new userSearchTicket();
        view.setVisible(true);
        return view;
    }

    public void FinishGUI(userSearchTicket view) {
        view.dispose();
    }
}
