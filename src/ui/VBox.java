package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class VBox extends JPanel {

    public void setChildren(Component... children) {
        setChildren(Arrays.stream(children).toList());
    }

    public void setChildren(List<Component> children) {
        var layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var horizontalGroup = layout.createParallelGroup();
        children.forEach(horizontalGroup::addComponent);

        var verticalGroup = layout.createSequentialGroup();
        children.forEach(verticalGroup::addComponent);

        layout.setHorizontalGroup(horizontalGroup);
        layout.setVerticalGroup(verticalGroup);

    }
}
