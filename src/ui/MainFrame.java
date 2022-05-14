package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        var frame = new MainFrame();
    }

    public MainFrame() {
        super("Cyber");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buildComponents();

        setVisible(true);
    }


    private void buildComponents() {

        this.setMinimumSize(new Dimension(800, 600));

        var tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Sign", new SigningPanel());
        tabbedPane.addTab("Authenticate", buildAuthenticatingPanel());
        getContentPane().add(tabbedPane);
        pack();
    }


    private Component buildAuthenticatingPanel() {
        var panel = new JPanel();
        var layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var fileLabel = new JLabel("File:");
        var fileChooser = new FileChooser();
        var signatureLabel = new JLabel("Signature:");
        var signatureChooser = new FileChooser();

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(fileLabel)
                                .addComponent(signatureLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(fileChooser.getFilenameLabel())
                                        .addComponent(fileChooser.getChooseFileButton()))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(signatureChooser.getFilenameLabel())
                                        .addComponent(signatureChooser.getChooseFileButton())))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(fileLabel)
                                .addComponent(fileChooser.getFilenameLabel())
                                .addComponent(fileChooser.getChooseFileButton()))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(signatureLabel)
                                .addComponent(signatureChooser.getFilenameLabel())
                                .addComponent(signatureChooser.getChooseFileButton()))
        );

        return panel;
    }

}


