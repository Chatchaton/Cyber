package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileChooser implements ActionListener {

    private final List<FileChangedListener> fileChangedListeners = new ArrayList<>();
    private final JLabel filenameLabel;
    private final JButton chooseFileButton;
    private File file;
    private Component parent;

    public FileChooser() {
        this.filenameLabel = new JLabel();
        this.chooseFileButton = new JButton();
        this.chooseFileButton.setText("Choose file");
        this.chooseFileButton.addActionListener(this);
    }

    public void addListener(FileChangedListener listener) {
        fileChangedListeners.add(listener);
    }

    public boolean removeListener(FileChangedListener listener) {
        return fileChangedListeners.remove(listener);
    }

    private void notifyFileChangedListeners() {
        var arg = new FileChangedEvent(file);
        fileChangedListeners.forEach(e -> e.call(arg));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new JFileChooser();
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setMultiSelectionEnabled(false);
        if (dialog.showOpenDialog(this.parent) == JFileChooser.APPROVE_OPTION) {
            this.file = dialog.getSelectedFile();
            this.filenameLabel.setText(this.file.getName());
            notifyFileChangedListeners();
        }
    }

    public JLabel getFilenameLabel() {
        return filenameLabel;
    }

    public JButton getChooseFileButton() {
        return chooseFileButton;
    }

    public File getFile() {
        return file;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public interface FileChangedListener {
        void call(FileChangedEvent arg);
    }

    public record FileChangedEvent(File file) {
    }
}
