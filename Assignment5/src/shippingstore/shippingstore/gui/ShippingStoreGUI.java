package shippingstore.shippingstore.gui;

import sun.font.TextLabel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ShippingStoreGUI  extends JFrame {

    private static final List<String> menuOptions = Arrays.asList("Show All Packages", "Add a New Package",
                                                    "Delete a Package", "Search for a Package", "Show All Users",
                                                    "Add a New User", "Update an Existing User", "Deliver a Package",
                                                    "Show All Transactions") ;

    private final DefaultListModel menuName = new DefaultListModel();
    private ListSelectionModel listSelectionModel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               ShippingStoreGUI app = new ShippingStoreGUI();
               //app.pack();
               app.setVisible(true);
            }
        });
    }


    private ShippingStoreGUI() {
        //setting up main window
        setTitle("Shipping Store");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0,0));


        //create a panel for the sidebar menu
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setPreferredSize(new Dimension(200, 600));
        panelMenu.setBackground(Color.WHITE);


        // setting up menu list for sidebar
        for (String mn: menuOptions) {
            menuName.addElement(mn);
        }

        final JList listMenu = new JList(menuName);
        listMenu.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listMenu.setSelectedIndex(0);
        listMenu.setVisibleRowCount(10);

        // adding list of menu options to scrollable sidebar
        JScrollPane listMenuScroller = new JScrollPane(listMenu);
        listMenu.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Store Main Menu"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        listMenuScroller.getBorder()));
        panelMenu.add(listMenuScroller);





        listSelectionModel = listMenu.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent lse) {
//                        System.out.println(e.getFirstIndex());
                        if (!lse.getValueIsAdjusting()) {
                            if (listMenu.getSelectedIndex() == 0 ) {
                                DefaultListModel model = (DefaultListModel) listMenu.getModel();
                                System.out.println(model.elementAt(0));
                            }
                            System.out.println(lse.getLastIndex() + ": Selection triggered");
                        }

                    }
                });





        // adding child components to main window
        add(panelMenu, BorderLayout.WEST);


//        JPanel p2 = new JPanel();
//        add(p2, BorderLayout.CENTER);




    }









}
