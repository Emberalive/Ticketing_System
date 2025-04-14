package org.Emberalive.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class Styling {
    public void styling (Container container, Color background, Color foreground, Predicate<Component> condition) {
        for (Component component : container.getComponents()){
            //set the background and foreground
            if (condition.test(component)){
                if (component instanceof JComponent){ //checking if the condition applies
                    component.setBackground(background);
                    component.setForeground(foreground);
                    if (component instanceof JComponent){
                        component.setBackground(background);
                        component.setForeground(foreground);
                    }
                }
            }
            //if there are child components, will run recursively through those components
            if (component instanceof Container){
                styling((Container) component, background, foreground, condition);
            }
        }
    }
}