package ui;

import ui.property.Property;
import ui.property.SimpleProperty;
import ui.property.ValueChangedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooser implements ActionListener {
    private final JLabel filenameLabel;
    private final JButton chooseFileButton;
    private final SimpleProperty<File> _fileProperty = new SimpleProperty<>();
    private Component parent;

    public FileChooser() {
        this.filenameLabel = new JLabel();
        this.chooseFileButton = new JButton();
        this.chooseFileButton.setText("Choose file");
        this.chooseFileButton.addActionListener(this);
        this._fileProperty.addListener(this::onFileChanged);
    }

    public Property<File> fileProperty() {
        return _fileProperty;
    }

    private void onFileChanged(ValueChangedEvent<File> event) {
        var file = event.newValue();
        var filename = file != null ? file.getName() : "";
        this.filenameLabel.setText(filename);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setMultiSelectionEnabled(false);
        if (dialog.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
            var file = dialog.getSelectedFile();
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
