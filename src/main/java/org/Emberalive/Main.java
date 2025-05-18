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

//         Initialize the model, view, and controller
        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginController controller = new LoginController(model, view);

//         Start the GUI through the controller
        controller.startGUI();
    }
    public static Bucket_Queue getBucket() {
        return bucket_queue;
    }

    public static Bucket_Queue setBucket() {
        return Bucket_Queue.fillFromDB(bucket_queue);
    }
}