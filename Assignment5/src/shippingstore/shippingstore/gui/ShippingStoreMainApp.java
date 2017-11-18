package shippingstore.shippingstore.gui;

import javax.swing.*;

public class ShippingStoreMainApp {
    public static void main(String[] args) {
        System.out.println("In ShippingStoreMainApp");
        Runnable r = new Runnable() {
            @Override
            public void run() {
//                JFrame frame = new JFrame("JFrame Demo");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                JTextArea textArea = new JTextArea(20, 40);
//                JScrollPane scrollPane = new JScrollPane(textArea);
//
//                frame.add(scrollPane);
//                frame.pack();
//                frame.setVisible(true);

            }
        };

        SwingUtilities.invokeLater(r);


    }
}
