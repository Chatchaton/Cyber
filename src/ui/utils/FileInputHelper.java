package ui.utils;

import ui.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FileInputHelper {
    private final List<Item> items = new ArrayList<>();

    public FileInputHelper add(String labelText, FileChooser fileChooser) {
        items.add(new Item(new JLabel(labelText), fileChooser.getFilenameLabel(), fileChooser.getChooseFileButton()));
        return this;
    }

    public void buildLayout(JPanel panel) {
        var layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var horizontalGroup = layout.createSequentialGroup();

        var labelGroup = layout.createParallelGroup();
        items.forEach(e -> labelGroup.addComponent(e.label()));
        horizontalGroup.addGroup(labelGroup);
        var filenameGroup = layout.createParallelGroup();
        items.forEach(e -> filenameGroup.addComponent(e.filename()));
        horizontalGroup.addGroup(filenameGroup);
        var buttonGroup = layout.createParallelGroup();
        items.forEach(e -> buttonGroup.addComponent(e.button()));
        horizontalGroup.addGroup(buttonGroup);

        var verticalGroup = layout.createSequentialGroup();
        items.forEach(e -> verticalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(e.label())
                .addComponent(e.filename())
                .addComponent(e.button())
        ));

        layout.setHorizontalGroup(horizontalGroup);
        layout.setVerticalGroup(verticalGroup);
    }

    private record Item(Component label, Component filename, Component button) {
    }


}
