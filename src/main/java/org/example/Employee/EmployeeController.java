package org.example.Employee;

public class EmployeeController {
    private final EmployeeView view;
    private final EmployeeModel model;

    public EmployeeController(EmployeeView view, EmployeeModel model) {
        this.view = view;
        this.model = model;
    }
    public void startGUI() {
        view.setVisible(true);
    }
}
