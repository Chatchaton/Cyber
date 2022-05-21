package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HBox extends JPanel {

    public HBox(Component... children) {
        var layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var horizontalGroup = layout.createSequentialGroup();
        Arrays.stream(children).forEach(horizontalGroup::addComponent);

        var verticalGroup = layout.createParallelGroup();
        Arrays.stream(children).forEach(verticalGroup::addComponent);

        layout.setHorizontalGroup(horizontalGroup);
        layout.setVerticalGroup(verticalGroup);

    }
}
