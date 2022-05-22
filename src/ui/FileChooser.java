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

    public static final String MODE_OPEN = "MODE_OPEN";
    public static final String MODE_SAVE = "MODE_SAVE";
    private String _mode = MODE_OPEN;

    public FileChooser() {
        this(MODE_OPEN);
    }

    public FileChooser(String mode) {
        this.filenameLabel = new JLabel();
        this.chooseFileButton = new JButton();
        this.chooseFileButton.setText("Choose file");
        this.chooseFileButton.addActionListener(this);
        this._fileProperty.addListener(this::onFileChanged);
        this.setMode(mode);
    }

    public String mode() {
        return _mode;
    }

    public void setMode(String mode) {
        switch (mode) {
            case MODE_OPEN, MODE_SAVE -> this._mode = mode;
            default -> throw new RuntimeException(new IllegalArgumentException(mode));
        }
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
        var result = switch (_mode) {
            case MODE_OPEN -> dialog.showOpenDialog(this.parent);
            case MODE_SAVE -> dialog.showSaveDialog(this.parent);
            default ->
                throw new RuntimeException(new IllegalStateException("Property mode has value: \"" + _mode + "\""));
        };
        if (result == JFileChooser.APPROVE_OPTION) {
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
