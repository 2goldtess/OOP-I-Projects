package shippingstore.shippingstore.gui;


import shippingstore.Package;
import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPackage extends JFrame implements ActionListener {
    private JPanel panelSearch;
    private JTextField textFieldSearch;
    private JButton btnSearch;
    private JPanel panelSearchResults;
    private ShippingStore ss;
    private Package packageFound;
    private JButton btnClear;
    private JPanel panelClear;
    private JLabel labelSearchResults;

    String trackingNumber;

    SearchPackage() {
        setSize(700, 150);
        setTitle("Search Packages");
        setLayout(new FormLayout());

        ss = new ShippingStore().readDatabase();

        textFieldSearch = new JTextField(10);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        panelSearch = new JPanel();
        panelSearch.add(textFieldSearch);
        panelSearch.add(btnSearch);

        add(new JLabel("  Find a Package"));
        add(panelSearch);

        add(new JLabel(""));
        panelSearchResults = new JPanel();
        add(panelSearchResults);

        // clear btn
        btnClear = new JButton("Clear Results");
        btnClear.addActionListener(this);

        panelClear = new JPanel();
        panelClear.add(btnClear);

        add(new JLabel(""));
        add(panelClear);

        // search results
        labelSearchResults = new JLabel();


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnSearch)) {
            if (textFieldSearch.getText().trim().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(new JFrame(), "Please enter a tracking number.");
            }
            else {
                trackingNumber = textFieldSearch.getText().trim();
                System.out.println(trackingNumber);

                try {
                    packageFound = ss.findPackage(trackingNumber);
                    packageFound.getPtn();
                    labelSearchResults.setText("Search results: " + packageFound.getFormattedText());
                    panelSearchResults.add(labelSearchResults);
                    repaint();
                    setVisible(true);
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(new JFrame(), "Package not found. Please check the tracking number and try again.");
                }
            }
        }

        if (e.getSource().equals(btnClear)) {
            labelSearchResults.setText("");
            System.out.println("clicked");
        }
    }
}
