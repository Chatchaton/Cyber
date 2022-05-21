package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HBox extends JPanel {


    public static HBox of(Component... children) {
        var box = new HBox();
        box.setChildren(children);
        return box;
    }

    public void setChildren(Component... children) {
        setChildren(Arrays.stream(children).toList());
    }

    public void setChildren(List<Component> children) {
        var layout = new GroupLayout(this);

        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var horizontalGroup = layout.createSequentialGroup();
        children.forEach(horizontalGroup::addComponent);

        var verticalGroup = layout.createParallelGroup();
        children.forEach(verticalGroup::addComponent);

        layout.setHorizontalGroup(horizontalGroup);
        layout.setVerticalGroup(verticalGroup);
    }

}
