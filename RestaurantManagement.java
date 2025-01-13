import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RestaurantManagement extends JFrame 
{
    JPanel mainPanel = new JPanel();
    JLayeredPane layeredPane = new JLayeredPane();
    private JCheckBox[] itemCheckBoxes;
    private JComboBox<String>[] categoryComboboxes;
    private JSpinner[] quantitySpinners;
    private JTextField[] priceTextFields;
    private JTextField totalTextField;
    private JButton orderButton;
    private JButton adminButton;
    private double[][] prices = {
            {250.0, 200.0, 300.0}, // Pizza prices
            {50.0, 45.0, 60.0},    // Tea prices
            {150.0, 120.0, 180.0}, // Burger prices
            {30.0, 25.0, 35.0},    // Drinks prices
            {100.0, 80.0, 120.0},  // Soup prices
            {60.0, 55.0, 70.0}     // Ice Cream prices
    };
    private ArrayList<String> previousBills = new ArrayList<>();
    public JCheckBox deal1, deal2, deal3;
    
    public void showMainPage() {
        setTitle("Restaurant Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    
        // Layered pane
        layeredPane.setSize(800, 600);
    
        // Background panel
        ImageIcon background = new ImageIcon("background5.jpg");
        BackgroundPanel backgroundPanel = new BackgroundPanel(background);
        backgroundPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
    
        // Main panel
        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 0, 800, 600);
    
        // Title
        JLabel titleLabel = new JLabel("Restaurant Menu");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 223, 0)); // Gold color
        titleLabel.setBounds(250, 20, 600, 50);
        mainPanel.add(titleLabel);
    
        // Items labels and checkboxes
        String[] items = {"Pizza", "Tea", "Burger", "Drinks", "Soup", "Ice Cream"};
        itemCheckBoxes = new JCheckBox[items.length];
        JLabel itemsLabel = new JLabel("Item:");
        itemsLabel.setFont(new Font("Serif", Font.BOLD, 18));
        itemsLabel.setForeground(new Color(255, 223, 0)); // Gold color
        itemsLabel.setBounds(60, 100, 100, 30);
        mainPanel.add(itemsLabel);
    
        for (int i = 0; i < items.length; i++) {
            itemCheckBoxes[i] = new JCheckBox(items[i]);
            itemCheckBoxes[i].setBounds(60, 150 + (i * 45), 100, 30);
            itemCheckBoxes[i].setBackground(new Color(255, 223, 0)); // Gold color
            itemCheckBoxes[i].setForeground(Color.BLACK);
            itemCheckBoxes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePrices();
                }
            });
            mainPanel.add(itemCheckBoxes[i]);
        }

        // Category labels and comboboxes
        String[][] categories = {
                {"Chicken Tikka", "BBQ", "Pepperoni"},
                {"Green Tea", "Black Tea", "Herbal Tea"},
                {"Cheese Burger", "Veggie Burger", "Chicken Burger"},
                {"Sting", "Pepsi", "Coca Cola"},
                {"Chicken Soup", "Tomato Soup", "Vegetable Soup"},
                {"Vanilla Flavour", "Chocolate Flavour", "Strawberry Flavour"}
        };
        categoryComboboxes = new JComboBox[items.length];
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Serif", Font.BOLD, 18));
        categoryLabel.setForeground(new Color(255, 223, 0)); // Gold color
        categoryLabel.setBounds(220, 100, 100, 30);
        mainPanel.add(categoryLabel);

        for (int i = 0; i < categories.length; i++) {
            categoryComboboxes[i] = new JComboBox<>(categories[i]);
            categoryComboboxes[i].setBounds(220, 150 + (i * 45), 150, 30);
            categoryComboboxes[i].setBackground(new Color(255, 223, 0)); // Gold color
            categoryComboboxes[i].setForeground(Color.BLACK);
            final int index = i;
            categoryComboboxes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updatePrice(index);
                }
            });
            mainPanel.add(categoryComboboxes[i]);
        }

        // Quantity labels and spinners
        quantitySpinners = new JSpinner[items.length];
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Serif", Font.BOLD, 18));
        quantityLabel.setForeground(new Color(255, 223, 0)); // Gold color
        quantityLabel.setBounds(430, 100, 100, 30);
        mainPanel.add(quantityLabel);

        for (int i = 0; i < items.length; i++) {
            quantitySpinners[i] = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            quantitySpinners[i].setBounds(430, 150 + (i * 45), 70, 30);
            quantitySpinners[i].setBackground(new Color(255, 223, 0)); // Gold color
            quantitySpinners[i].addChangeListener(e -> updatePrices());

            JComponent editor = quantitySpinners[i].getEditor();
            if (editor instanceof JSpinner.DefaultEditor) {
                JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
                spinnerEditor.getTextField().setBackground(new Color(255, 223, 0)); // Gold color
                spinnerEditor.getTextField().setForeground(Color.BLACK);
            }
            
            mainPanel.add(quantitySpinners[i]);
        }

        // Price labels and textfields
        priceTextFields = new JTextField[items.length];
        JLabel priceLabel = new JLabel("Price (Rs):");
        priceLabel.setFont(new Font("Serif", Font.BOLD, 18));
        priceLabel.setForeground(new Color(255, 223, 0)); // Gold color
        priceLabel.setBounds(560, 100, 100, 30);
        mainPanel.add(priceLabel);

        for (int i = 0; i < items.length; i++) {
            priceTextFields[i] = new JTextField();
            priceTextFields[i].setBounds(560, 150 + (i * 45), 100, 30);
            priceTextFields[i].setBackground(new Color(255, 223, 0)); // Gold color
            priceTextFields[i].setEditable(false); // Price is set automatically
            mainPanel.add(priceTextFields[i]);
        }

        // Total label and textfield
        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setFont(new Font("Serif", Font.BOLD, 25));
        totalLabel.setForeground(Color.BLACK);
        totalLabel.setBounds(430, 420, 200, 40);
        mainPanel.add(totalLabel);

        totalTextField = new JTextField();
        totalTextField.setBounds(560, 420, 100, 30);
        totalTextField.setBackground(new Color(255, 223, 0)); // Gold color
        totalTextField.setEditable(false); // Total is calculated automatically
        mainPanel.add(totalTextField);

        // Buttons
        orderButton = new JButton("Order");
        orderButton.setFont(new Font("Serif", Font.BOLD, 18));
        orderButton.setBounds(350, 480, 100, 40);
        orderButton.setBackground(Color.RED); 
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaymentMethodDialog();   //for All Menu Bill
                
            }
        });
        mainPanel.add(orderButton);        

        layeredPane.add(mainPanel, JLayeredPane.PALETTE_LAYER);

    // Adding layeredPane to the frame
    add(layeredPane);
    setVisible(true);
}

    private void updatePrice(int index) {
        if (itemCheckBoxes[index].isSelected()) {
            int categoryIndex = categoryComboboxes[index].getSelectedIndex();
            double price = prices[index][categoryIndex];
            priceTextFields[index].setText(String.valueOf(price));
        } else {
            priceTextFields[index].setText("");
        }
        updatePrices();
    }

    private void updatePrices() {
        double total = 0;
        for (int i = 0; i < itemCheckBoxes.length; i++) {
            if (itemCheckBoxes[i].isSelected()) {
                int categoryIndex = categoryComboboxes[i].getSelectedIndex();
                double price = prices[i][categoryIndex];
                int quantity = (int) quantitySpinners[i].getValue();
                priceTextFields[i].setText(String.valueOf(price * quantity));
                total += price * quantity;
            } else {
                priceTextFields[i].setText("");
            }
        }
        totalTextField.setText(String.valueOf(total));
    }

    private void showPaymentMethodDialog() {
        JFrame paymentFrame = new JFrame("Payment Method");
        paymentFrame.setSize(400, 200);
        paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paymentFrame.setLayout(null);
        paymentFrame.setLocationRelativeTo(null);

        JLabel paymentLabel = new JLabel("Select Payment Method:");
        paymentLabel.setFont(new Font("Serif", Font.BOLD, 18));
        paymentLabel.setBounds(50, 20, 300, 30);
        paymentFrame.add(paymentLabel);

        JComboBox<String> paymentMethodComboBox = new JComboBox<>(new String[]{"Cash", "Card", "EasyPaisa"});
        paymentMethodComboBox.setBounds(50, 60, 300, 30);
        paymentFrame.add(paymentMethodComboBox);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(150, 100, 100, 40);
        confirmButton.addActionListener(e -> {
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            paymentFrame.dispose();
            showBill(paymentMethod); 
            
        });
        paymentFrame.add(confirmButton);

        paymentFrame.setVisible(true);
    }

    private void showDealBill()
    {

        JFrame billFrame = new JFrame("Deal Bill");
    billFrame.setSize(400, 600);
    billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    billFrame.setLayout(null);
    billFrame.setLocationRelativeTo(null);

    JTextArea billTextArea = new JTextArea();
    billTextArea.setEditable(false);
    billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    JScrollPane scrollPane = new JScrollPane(billTextArea);
    scrollPane.setBounds(20, 20, 350, 500);
    billFrame.add(scrollPane);

    double total = 0;
    if (deal1.isSelected()) {
        total += 150;
        billTextArea.append("1xPizza, 1xBurger, 1xDrink        150\n");
    }
    if (deal2.isSelected()) {
        total += 250;
        billTextArea.append("2xPizza, 3xBurger, 2xDrink, 3xTea     250\n");
    }
    if (deal3.isSelected()) {
        total += 350;
        billTextArea.append("5xIce-Cream, 5xBurger, 5xDrink        350\n");
    }

    double tax = 0;
    // if ("Cash".equals(paymentMethod)) {
    //     tax = total * 0.15;
    // } else if ("Card".equals(paymentMethod)) {
    //     tax = total * 0.10;
    // } else if ("EasyPaisa".equals(paymentMethod)) {
    //     tax = total * 0.05;
    // }

    double finalTotal = total + tax;
    StringBuilder billContent = new StringBuilder();
    billContent.append("               MCS CAFE\n");
    billContent.append("      Military College of Signals\n");
    billContent.append("         Adiala Road,Rawalpindi\n");
    billContent.append("     Ph. No.: 0123-456789,93111111111\n");
    //billContent.append("    GSTIN : 06AACCO6344G1ZJ\n");
    billContent.append("      Invoice Number: IN001001259\n");
    billContent.append("     Invoice Date: 30-May-24 08:00\n");
    billContent.append("-------------------------------------\n");
    billContent.append(" Special Deals\n");
    billContent.append("-------------------------------------\n");

    if (deal1.isSelected()) {
        billContent.append("1xPizza, 1xBurger, 1xDrink = 150\n");
    }
    if (deal2.isSelected()) {
        billContent.append("2xPizza, 3xBurger, 2xDrink, 3xTea = 250\n");
    }
    if (deal3.isSelected()) {
        billContent.append("5xIce-Cream, 5xBurger, 5xDrink = 350\n");
    }

    billContent.append("-------------------------------------\n");
    billContent.append(String.format(" Sub Total: %22.2f\n", total));
   // billContent.append(" Payment Method:             ").append(paymentMethod).append("\n");
    billContent.append(String.format(" GS.Tax: %26.2f\n", tax));
    billContent.append(String.format(" Total: %26.2f\n", finalTotal));
    billContent.append("\n\n   Thanks For Visit....\n");

    previousBills.add(billContent.toString());
    billTextArea.setText(billContent.toString());
    billFrame.setVisible(true);

    }


    private void showBill(String paymentMethod) {
        JFrame billFrame = new JFrame("Bill");
        billFrame.setSize(400, 600);
        billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        billFrame.setLayout(null);
        billFrame.setLocationRelativeTo(null);

        JTextArea billTextArea = new JTextArea();
        billTextArea.setEditable(false);
        billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(billTextArea);
        scrollPane.setBounds(20, 20, 350, 500);
        billFrame.add(scrollPane);

        double total = Double.parseDouble(totalTextField.getText());
        double tax = 0;

        if ("Cash".equals(paymentMethod)) {
            tax = total * 0.15;
        } else if ("Card".equals(paymentMethod)) {
            tax = total * 0.10;
        } else if ("EasyPaisa".equals(paymentMethod)) {
            tax = total * 0.05;
        }

        double finalTotal = total + tax;
        StringBuilder billContent = new StringBuilder();
        billContent.append("               MCS CAFE\n");
        billContent.append("      Military College of Signals\n");
        billContent.append("         Adiala Road,Rawalpindi\n");
        billContent.append("     Ph. No.: 0123-456789,93111111111\n");
        //billContent.append("    GSTIN : 06AACCO6344G1ZJ\n");
        billContent.append("      Invoice Number: IN001001259\n");
        billContent.append("     Invoice Date: 30-May-24 08:00\n");
        billContent.append("-------------------------------------\n");
        billContent.append(" Item            Qty.   Rate    Total\n");
        billContent.append("-------------------------------------\n");

        for (int i = 0; i < itemCheckBoxes.length; i++) {
            if (itemCheckBoxes[i].isSelected()) {
                int quantity = (int) quantitySpinners[i].getValue();
                double price = Double.parseDouble(priceTextFields[i].getText());
                billContent.append(String.format("%-15s %3d %7.2f %8.2f\n",
                        itemCheckBoxes[i].getText(), quantity, price / quantity, price));
            }
        }

        billContent.append("-------------------------------------\n");
        billContent.append(String.format(" Total Qty: %23d\n", getTotalQuantity()));
        billContent.append(String.format(" Sub Total: %22.2f\n", total));
       // billContent.append(String.format(" CGST@%.1f%%: %20.2f\n", 2.5, total * 0.025));
       // billContent.append(String.format(" SGST@%.1f%%: %20.2f\n", 2.5, total * 0.025));
        billContent.append(" Payment Method:             ").append(paymentMethod).append("\n");
        billContent.append(String.format(" GS.Tax: %26.2f\n", tax));
        billContent.append(String.format(" Total: %26.2f\n", finalTotal));
        billContent.append("\n\n   Thanks For Visit....\n");

        previousBills.add(billContent.toString());
        billTextArea.setText(billContent.toString());
        billFrame.setVisible(true);


    }

    private int getTotalQuantity() {
        int totalQuantity = 0;
        for (JSpinner quantitySpinner : quantitySpinners) {
            totalQuantity += (int) quantitySpinner.getValue();
        }
        return totalQuantity;
    }

    private void showAdminLogin() {
        JFrame adminLoginFrame = new JFrame("Admin Login");
        adminLoginFrame.setSize(400, 200);
        adminLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminLoginFrame.setLayout(null);
        adminLoginFrame.setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        adminLoginFrame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 50, 165, 25);
        adminLoginFrame.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 80, 25);
        adminLoginFrame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 80, 165, 25);
        adminLoginFrame.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 120, 80, 25);
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (username.equals("admin") && password.equals("password")) {
                adminLoginFrame.dispose();
                showAdminPage();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        });
        adminLoginFrame.add(loginButton);

        adminLoginFrame.setVisible(true);
    }

    private void showAdminPage() {
        JFrame adminFrame = new JFrame("Admin Page");
        adminFrame.setSize(800, 600);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);
        
        // Load the background image
        ImageIcon background = new ImageIcon("admin1.jpg");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 800, 600);
        adminFrame.setContentPane(bgLabel);
        bgLabel.setLayout(null); // Use absolute layout for the background label
    
        JButton viewBillsButton = new JButton("View Previous Bills");
        viewBillsButton.setBounds(120, 100, 200, 50);
        viewBillsButton.setBackground(Color.GRAY);
        viewBillsButton.setForeground(Color.WHITE);
        viewBillsButton.addActionListener(e -> showPreviousBills());
        bgLabel.add(viewBillsButton);
    
        JButton updatePricesButton = new JButton("Update Prices");
        updatePricesButton.setBounds(420, 100, 200, 50);
        updatePricesButton.setBackground(Color.GRAY);
        updatePricesButton.setForeground(Color.WHITE);
        updatePricesButton.addActionListener(e -> showUpdatePricesPage());
        bgLabel.add(updatePricesButton);
    
        adminFrame.setVisible(true);
    }

    private void showPreviousBills() {
        JFrame billsFrame = new JFrame("Previous Bills");
        billsFrame.setSize(400, 600);
        billsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        billsFrame.setLayout(null);
        billsFrame.setLocationRelativeTo(null);

        JTextArea billsTextArea = new JTextArea();
        billsTextArea.setEditable(false);
        for (String bill : previousBills) {
            billsTextArea.append(bill + "\n\n");
        }
        JScrollPane scrollPane = new JScrollPane(billsTextArea);
        scrollPane.setBounds(20, 20, 350, 500);
        billsFrame.add(scrollPane);

        billsFrame.setVisible(true);
    }

    private void showUpdatePricesPage() {
        JFrame updatePricesFrame = new JFrame("Update Prices");
        updatePricesFrame.setSize(800, 600);
        updatePricesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updatePricesFrame.setLocationRelativeTo(null);
    
        ImageIcon background = new ImageIcon("update.jpg");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 800, 600);
        updatePricesFrame.setContentPane(bgLabel);
        bgLabel.setLayout(null); // Use absolute layout for the background label
    
        JLabel updatePricesLabel = new JLabel("Update Prices");
        updatePricesLabel.setFont(new Font("Serif", Font.BOLD, 36));
        updatePricesLabel.setForeground(Color.BLACK); 
        updatePricesLabel.setBounds(300, 15, 300, 60);
        bgLabel.add(updatePricesLabel);
    
        JLabel[] itemLabels = new JLabel[6];
        JLabel[][] categoryLabels = new JLabel[6][3];  // Add category labels
        JTextField[][] priceFields = new JTextField[6][3];
        String[] itemNames = {"Pizza", "Tea", "Burger", "Drinks", "Soup", "Ice Cream"};
        String[][] categoryNames = {
            {"Chicken Tikka", "BBQ", "Pepperoni"},
            {"Green Tea", "Black Tea", "Herbal Tea"},
            {"Cheese Burger", "Veggie Burger", "Chicken Burger"},
            {"Sting", "Pepsi", "Coca Cola"},
            {"Chicken Soup", "Tomato Soup", "Vegetable Soup"},
            {"Vanilla Flavour", "Chocolate Flavour", "Strawberry Flavour"}
        };
    
        for (int i = 0; i < 6; i++) {
            itemLabels[i] = new JLabel(itemNames[i] + ":");
            itemLabels[i].setFont(new Font("Serif", Font.BOLD, 25));
            itemLabels[i].setForeground(Color.BLACK); 
            itemLabels[i].setBounds(50, 100 + (i * 70), 100, 30);
            bgLabel.add(itemLabels[i]);
    
            for (int j = 0; j < 3; j++) {
                // Add category labels
                categoryLabels[i][j] = new JLabel(categoryNames[i][j]);
                categoryLabels[i][j].setFont(new Font("Serif", Font.BOLD, 16));
                categoryLabels[i][j].setForeground(Color.BLACK); 
                categoryLabels[i][j].setBounds(200 + (j * 150), 70 + (i * 70), 120, 30);
                bgLabel.add(categoryLabels[i][j]);
    
                priceFields[i][j] = new JTextField(String.valueOf(prices[i][j]));
                priceFields[i][j].setBounds(200 + (j * 150), 100 + (i * 70), 70, 30);
                priceFields[i][j].setBackground(Color.WHITE); // Gold color
                bgLabel.add(priceFields[i][j]);
            }
        }
    
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(350, 500, 100, 40);
       // updateButton.setBackground(Color.RED); // Red color
        updateButton.addActionListener(e -> {
            try {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 3; j++) {
                        prices[i][j] = Double.parseDouble(priceFields[i][j].getText());
                    }
                }
                JOptionPane.showMessageDialog(null, "Prices updated successfully!");
                updatePricesFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for prices.");
            }
        });
        bgLabel.add(updateButton);
    
        updatePricesFrame.setVisible(true);
    }
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
    
        public BackgroundPanel(ImageIcon imageIcon) {
            this.backgroundImage = imageIcon.getImage();
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("WELCOME Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLayout(new BorderLayout());
    
        
        // Background panel
        ImageIcon front = new ImageIcon("file.jpg");
        BackgroundPanel bg1 = new BackgroundPanel(front);
        bg1.setLayout(null); // Set layout to null for absolute positioning
    

        JLabel deals = new JLabel("MCS CAFE", JLabel.CENTER);
        deals.setFont(new Font("MV Boli", Font.BOLD, 35));
        deals.setForeground(Color.BLACK); 
        deals.setBounds(90, 20, 300, 40);
        bg1.add(deals);


        String[] list = {"Choose Plz:", "All Menu", "Special Offers"};
        JComboBox<String> comboBox = new JComboBox<>(list);
        comboBox.setSelectedIndex(0);
        comboBox.setBounds(192, 273, 100, 35);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(new Color(255, 140, 0)); 
        comboBox.setOpaque(true);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedItem() == list[2]) {
                    showDeals();
                } else if (comboBox.getSelectedItem() == list[1]) {
                    showMainPage();
                }
            }
        });
    
        JButton adminButton = new JButton("Admin");
        adminButton.setForeground(Color.WHITE);
        adminButton.setBackground(new Color(255, 140, 0)); 
        adminButton.setBounds(192, 350, 100, 35);
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAdminLogin();
            }
        });
    
        bg1.add(comboBox);
        bg1.add(adminButton);
    
        frame.setBounds(420,160,500,450);
        frame.add(bg1, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void showDeals() {
        JFrame dealsFrame = new JFrame("Deals Page");
        dealsFrame.setSize(900, 576);
        dealsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dealsFrame.setLocationRelativeTo(null);
    
        // Load the background image
        ImageIcon background = new ImageIcon("special.jpg");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 900, 576);
        dealsFrame.setContentPane(bgLabel);
        bgLabel.setLayout(null); 
    
        JLabel deals = new JLabel("HOT DEALS", JLabel.CENTER);
        deals.setFont(new Font("MV Boli", Font.BOLD, 35));
        deals.setForeground(new Color(150,75,0));
        deals.setBounds(20, 20, 300, 40);
        bgLabel.add(deals);
    
        deal1 = new JCheckBox("1xPizza, 1xBurger, 1xDrink                  = Rs 150");
        deal1.setFont(new Font("MV Boli", Font.BOLD, 15));
        deal1.setBounds(90, 160, 430, 35);
        deal1.setForeground(new Color(150,75,0));
        deal1.setBackground(Color.WHITE);
        deal2 = new JCheckBox("2xPizza, 3xBurger, 2xDrink, 3xTea = Rs 250");
        deal2.setFont(new Font("MV Boli", Font.BOLD, 15));
        deal2.setBounds(90, 210, 430, 35);
        deal2.setForeground(new Color(150,75,0));
        deal2.setBackground(Color.WHITE);
        deal3 = new JCheckBox("5xIce-Cream, 5xBurger, 5xDrink           = Rs 350");
        deal3.setFont(new Font("MV Boli", Font.BOLD, 15));
        deal3.setBounds(90, 260, 430, 35);
        deal3.setForeground(new Color(150,75,0));
        deal3.setBackground(Color.WHITE);
    
        JButton bill = new JButton("Calculate Bill");
        bill.setForeground(Color.BLACK);
        bill.setBounds(220, 370, 200, 30);
        bill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // calculateBill(false); // Pass false for deals
                showDealBill();   //for Special Offers Bill
            }
        });
    
        bgLabel.add(deal1);
        bgLabel.add(deal2);
        bgLabel.add(deal3);
        bgLabel.add(bill);
    
        dealsFrame.setVisible(true);
    }
    
    public static void main(String[] args) {

        
        RestaurantManagement r = new RestaurantManagement();
        r.createAndShowGUI();
    }
}

