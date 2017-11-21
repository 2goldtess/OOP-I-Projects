package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePackage extends JFrame implements ActionListener {

    private String trackingNumber;
    private ShippingStore ss;
    private boolean isDeleted;

    private JTextField textFieldTrackingNumber;
    private JButton btnDelete;
    private JPanel panelButtons;
    private JButton btnShowPackages;



    DeletePackage() {
        // default initial settings for the frame
        setSize(600, 75);
        setTitle("Delete a Package");
        setLayout(new FormLayout());

        // initializing shipping store object;
        ss = new ShippingStore().readDatabase();

        panelButtons = new JPanel();

        //tracking number field
        add(new JLabel("  Tracking Number"));
        textFieldTrackingNumber = new JTextField(10);

        // delete button
        btnDelete = new JButton("Delete Package");
        btnDelete.addActionListener(this);

        // show packages button
        btnShowPackages = new JButton("Show All Packages");
        btnShowPackages.addActionListener(this);

        // adding components to delete panel
        panelButtons.add(textFieldTrackingNumber);
        panelButtons.add(btnDelete);
        panelButtons.add(btnShowPackages);

        // adding panel to frame
        add(panelButtons);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setAlwaysOnTop(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnDelete)) {
            if (textFieldTrackingNumber.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(new JFrame(), "Please enter a tracking number.");
            }
            else {
                trackingNumber = textFieldTrackingNumber.getText();
                isDeleted = ss.deletePackage(trackingNumber);

                if (isDeleted)
                    JOptionPane.showMessageDialog(new JFrame(), "Package was deleted");
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Package with the given tracking number (#" + trackingNumber + ") does not exist.");
                ss.writeDatabase();
            }
        }

        if (e.getSource().equals(btnShowPackages)) {
            ShowPackages sp = new ShowPackages();
            sp.setLocation(this.getX(), this.getY() + 76);
        }
    }
}
