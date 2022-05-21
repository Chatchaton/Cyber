package ui;

import ui.property.Property;
import ui.property.ReadonlyProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooser implements ActionListener {

    private final JLabel filenameLabel;
    private final JButton chooseFileButton;
    private final Property<File> _fileProperty = new Property<>();

    public ReadonlyProperty<File> fileProperty() {
        return _fileProperty;
    }

    private Component parent;

    public FileChooser() {
        this.filenameLabel = new JLabel();
        this.chooseFileButton = new JButton();
        this.chooseFileButton.setText("Choose file");
        this.chooseFileButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setMultiSelectionEnabled(false);
        if (dialog.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
            var file = dialog.getSelectedFile();
            this.filenameLabel.setText(file.getName());
            _fileProperty.setValue(file);
        }
    }

    public JLabel getFilenameLabel() {
        return filenameLabel;
    }

    public JButton getChooseFileButton() {
        return chooseFileButton;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

}
