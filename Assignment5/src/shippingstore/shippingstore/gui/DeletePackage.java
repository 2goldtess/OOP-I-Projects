package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePackage extends JFrame implements ActionListener {
    private JTextField textFieldTrackingNumber;
    private JButton btnDelete;
    private JPanel panelDelete;
    private String trackingNumber;
    private ShippingStore ss;

    DeletePackage() {
        setSize(425, 75);
        setTitle("Delete a Package");
        setLayout(new FormLayout());

        // initializing shipping store object;
        ss = new ShippingStore().readDatabase();

        panelDelete = new JPanel();

        //tracking number field
        add(new JLabel("  Tracking Number"));
        textFieldTrackingNumber = new JTextField(10);

        // delete button
        btnDelete = new JButton("Delete Package");
        btnDelete.addActionListener(this);

        panelDelete.add(textFieldTrackingNumber);
        panelDelete.add(btnDelete);

        add(panelDelete);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnDelete)); {
            if (textFieldTrackingNumber.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(new JFrame(), "Please enter a tracking number.");
            }
            else {

                trackingNumber = textFieldTrackingNumber.getText();
                ss.deletePackage(trackingNumber);

                if (ss.deletePackage(trackingNumber))
                    JOptionPane.showMessageDialog(new JFrame(), "Package was deleted");
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Package with the given tracking number (#" + trackingNumber + ") does not exist.");
                ss.writeDatabase();
            }
        }
    }
}
