import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Lab07A_InvoiceGUI extends JFrame {
    private List<LineItem> lineItems;
    private JTextArea invoiceTextArea;

    public Lab07A_InvoiceGUI() {
        lineItems = new ArrayList<>();
        invoiceTextArea = new JTextArea(10, 40);
        invoiceTextArea.setEditable(false);

        JButton addButton = new JButton("Add Line Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = JOptionPane.showInputDialog("Product Name:");
                int quantity = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));
                double unitPrice = Double.parseDouble(JOptionPane.showInputDialog("Unit Price:"));

                LineItem lineItem = new LineItem(new Product(productName, unitPrice), quantity);
                lineItems.add(lineItem);

                updateInvoiceDisplay();
            }
        });

        JPanel panel = new JPanel();
        panel.add(addButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(invoiceTextArea), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        setTitle("Invoice App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void updateInvoiceDisplay() {
        double totalAmount = 0.0;
        invoiceTextArea.setText("Invoice:\n\n");

        invoiceTextArea.append("Customer Address:\n");
        invoiceTextArea.append("321 Main Avenue\n");
        invoiceTextArea.append("New York City, New York, 56756\n\n");

        invoiceTextArea.append("Line Items:\n");
        invoiceTextArea.append(String.format("%-30s%-10s%-15s%-10s%n", "Product", "Quantity", "Unit Price", "Total"));
        invoiceTextArea.append(String.format("%-30s%-10s%-15s%-10s%n", "--------------------------------", "--------", "---------------", "--------"));

        for (LineItem item : lineItems) {
            totalAmount += item.calculateTotal();
            invoiceTextArea.append(String.format("%-30s%-10d$%-15.2f$%-10.2f%n",
                    item.getProduct().getName(), item.getQuantity(),
                    item.getProduct().getUnitPrice(), item.calculateTotal()));
        }
        
        invoiceTextArea.append(String.format("%-50s$%-10.2f%n", "Total Amount Due:", totalAmount));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Lab07A_InvoiceGUI app = new Lab07A_InvoiceGUI();
            app.setVisible(true);
        });
    }
}
