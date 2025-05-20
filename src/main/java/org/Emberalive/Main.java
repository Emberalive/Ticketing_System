package org.Emberalive;

import org.Emberalive.dataStructures.bucket.Bucket_Queue;
import org.Emberalive.login.LoginController;
import org.Emberalive.login.LoginModel;
import org.Emberalive.login.LoginView;


public class Main {
    private static Bucket_Queue bucket_queue = new Bucket_Queue();

    public static void main(String[] args) {
//        new IT_employee("employee", "password");

        bucket_queue = setBucket();

        if (bucket_queue != null) {
            //         Initialize the model, view, and controller
            LoginModel model = new LoginModel();
            LoginView view = new LoginView();
            LoginController controller = new LoginController(model, view);

//         Start the GUI through the controller
            controller.startGUI();
        } else {
            System.out.println("You may need to set up the postgresql library, this is outlined in the pre-requisites in the 'README.md'");
            System.exit(1);
        }
    }
    public static Bucket_Queue getBucket() {
        return bucket_queue;
    }

    public static Bucket_Queue setBucket() {
        try {
            return Bucket_Queue.fillFromDB(bucket_queue);
        }catch(Exception e) {
            System.out.println("Error while filling bucket: " + e.getMessage());
        }
        return null;
    }
}