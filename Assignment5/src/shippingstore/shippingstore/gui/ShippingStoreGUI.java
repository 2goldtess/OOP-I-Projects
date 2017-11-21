package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class ShippingStoreGUI  extends JFrame implements MouseListener, WindowListener {

    private static final List<String> menuOptions = Arrays.asList("Show All Packages", "Add a New Package",
            "Delete a Package", "Search for a Package", "Show All Users",
            "Add a New User", "Update an Existing User", "Deliver a Package",
            "Show All Transactions", "Save & Exit Program") ;

    private final DefaultListModel menuModel;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    ShippingStore ss;
    private JPanel panelMenu;
    private JList  listMenu;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StoreLogger storeLogger = new StoreLogger();
                try {
                    storeLogger.setup();
                } catch (IOException e) {
                    System.out.println("An error occurred while configuring the store logger");
                }

                ShippingStoreGUI app = new ShippingStoreGUI();
                app.setVisible(true);
            }
        });
    }


    private ShippingStoreGUI() {
        LOGGER.info("App is now running");

        // setting up main window
        setTitle("Shipping Store");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0,0));

        ss = new ShippingStore().readDatabase();

        // setting up a panel for the main menu
        panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setBackground(Color.WHITE);


        // setting up menu list model
        menuModel = new DefaultListModel();
        for (String mn: menuOptions) {
            menuModel.addElement(mn);
        }

        // setting up list for sidebar menu
        listMenu = new JList();
        listMenu.setModel(menuModel);
        listMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMenu.setLayoutOrientation(JList.VERTICAL);
        listMenu.addMouseListener(this);
        listMenu.setSelectedIndex(-1);

        // adding list of menu options to scrollable sidebar
        JScrollPane listMenuScroller = new JScrollPane(listMenu);
        listMenu.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Main Menu"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),

                        listMenuScroller.getBorder()));
        panelMenu.add(listMenuScroller);


        // adding child components to main window
        add(panelMenu, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (listMenu.getSelectedIndex()) {
            case 0:
                //user selects 'show all packages'
                LOGGER.info("User selects: Show All packages");
                ShowPackages sp = new ShowPackages();
                sp.setLocation(this.getX(), this.getY());
                break;
            case 1:
                //user selects 'add a new package'
                LOGGER.info("User selects: Add a New Package");
                AddPackage ap = new AddPackage();
                ap.setLocation(this.getX(), this.getY());
                break;
            case 2:
                //user selects 'delete a package'
                LOGGER.info("User selects: Delete a Package");
                DeletePackage dp = new DeletePackage();
                dp.setLocation(this.getX(), this.getY());
                break;
            case 3:
                //user selects 'search for a package'
                LOGGER.info("User selects: Search for a Package");
                SearchPackage searchPackage = new SearchPackage();
                searchPackage.setLocation(this.getX(), this.getY());
                break;
            case 4:
                //user selects 'show all users
                LOGGER.info("User selects: Show All Users");
                ShowUsers su = new ShowUsers();
                su.setLocation(this.getX(), this.getY());
                break;
            case 5:
                //user selects 'add a new user
                LOGGER.info("User selects: Add a New User");
                AddUser au = new AddUser();
                au.setLocation(this.getX(), this.getY());
                break;
            case 6:
                //user selects 'update an existing user
                LOGGER.info("User selects: Update an Existing User");
                UpdateUser uu = new UpdateUser();
                uu.setLocation(this.getX(), this.getY());
                break;
            case 7:
                //user selects 'deliver a package
                LOGGER.info("User selects: Deliver a Package");
                DeliverPackage deliverP = new DeliverPackage();
                deliverP.setLocation(this.getX(), this.getY());
                break;
            case 8:
                //user selects 'show all transactions
                LOGGER.info("User selects: Show All Transactions");
                showTransactions st = new showTransactions();
                st.setLocation(this.getX(), this.getY());
                break;
            case 9:
                LOGGER.info("User selects: Save & Exit Program");
                LOGGER.warning("Exiting Program");
                Runtime.getRuntime().exit(0);
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        ss.readDatabase();
        ss.writeDatabase();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}







