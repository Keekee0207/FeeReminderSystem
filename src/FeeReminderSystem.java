import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;

public class FeeRemainderSystem extends JFrame {
    private JTextField tfName, tfFatherName, tfRollNo, tfPhone, tfYear;
    private JComboBox<String> cbStream;
    private JCheckBox cbHostel, cbBus;
    private JTextArea taReceipt;
    private JButton btnGenerate, btnReset, btnPrint;

    public FeeRemainderSystem() {
        setTitle("Fee Reminder System - Standalone GUI");
        setSize(600, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel labelTitle = new JLabel("Fee Reminder System");
        labelTitle.setFont(new Font("Verdana", Font.BOLD, 20));
        labelTitle.setBounds(180, 10, 300, 30);
        add(labelTitle);

        JLabel labelName = new JLabel("Student Name:");
        labelName.setBounds(30, 60, 120, 25);
        add(labelName);
        tfName = new JTextField();
        tfName.setBounds(150, 60, 150, 25);
        add(tfName);

        JLabel labelFather = new JLabel("Father's Name:");
        labelFather.setBounds(30, 100, 120, 25);
        add(labelFather);
        tfFatherName = new JTextField();
        tfFatherName.setBounds(150, 100, 150, 25);
        add(tfFatherName);

        JLabel labelRoll = new JLabel("Roll Number:");
        labelRoll.setBounds(30, 140, 120, 25);
        add(labelRoll);
        tfRollNo = new JTextField();
        tfRollNo.setBounds(150, 140, 150, 25);
        add(tfRollNo);

        JLabel labelPhone = new JLabel("Phone Number:");
        labelPhone.setBounds(30, 180, 120, 25);
        add(labelPhone);
        tfPhone = new JTextField();
        tfPhone.setBounds(150, 180, 150, 25);
        add(tfPhone);

        JLabel labelYear = new JLabel("Year of Passing:");
        labelYear.setBounds(30, 220, 120, 25);
        add(labelYear);
        tfYear = new JTextField();
        tfYear.setBounds(150, 220, 150, 25);
        add(tfYear);

        JLabel labelStream = new JLabel("Stream:");
        labelStream.setBounds(30, 260, 120, 25);
        add(labelStream);
        String[] streams = {"Select", "Science", "Commerce", "Arts", "Engineering", "Management"};
        cbStream = new JComboBox<>(streams);
        cbStream.setBounds(150, 260, 150, 25);
        add(cbStream);

        cbHostel = new JCheckBox("Hostel Facility");
        cbHostel.setBounds(150, 300, 150, 25);
        add(cbHostel);

        cbBus = new JCheckBox("Bus Facility");
        cbBus.setBounds(150, 340, 150, 25);
        add(cbBus);

        taReceipt = new JTextArea();
        taReceipt.setBounds(30, 380, 520, 130);
        taReceipt.setEditable(false);
        taReceipt.setBorder(BorderFactory.createLineBorder(Color.gray));
        add(taReceipt);

        btnGenerate = new JButton("Generate Receipt");
        btnGenerate.setBounds(50, 520, 150, 30);
        add(btnGenerate);

        btnReset = new JButton("Reset");
        btnReset.setBounds(230, 520, 100, 30);
        add(btnReset);

        btnPrint = new JButton("Print Receipt");
        btnPrint.setBounds(370, 520, 150, 30);
        add(btnPrint);

        // Button Actions
        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReceipt();
            }
        });

        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    taReceipt.print();
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(null, "Printer Error: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    // Generate Receipt and Trigger Notification
    private void generateReceipt() {
        String name = tfName.getText();
        String father = tfFatherName.getText();
        String roll = tfRollNo.getText();
        String phone = tfPhone.getText();
        String year = tfYear.getText();
        String stream = cbStream.getSelectedItem().toString();

        if (name.isEmpty() || father.isEmpty() || roll.isEmpty() || phone.isEmpty() ||
                year.isEmpty() || stream.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please fill all details and select stream");
            return;
        }

        int baseFee;
        switch (stream) {
            case "Science": baseFee = 40000; break;
            case "Commerce": baseFee = 35000; break;
            case "Arts": baseFee = 30000; break;
            case "Engineering": baseFee = 60000; break;
            case "Management": baseFee = 55000; break;
            default: baseFee = 0;
        }

        int hostelFee = cbHostel.isSelected() ? 10000 : 0;
        int busFee = cbBus.isSelected() ? 5000 : 0;
        int totalFee = baseFee + hostelFee + busFee;

        StringBuilder receipt = new StringBuilder();
        receipt.append("------------ Fee Receipt ------------\n");
        receipt.append("Student Name: ").append(name).append("\n");
        receipt.append("Father's Name: ").append(father).append("\n");
        receipt.append("Roll Number: ").append(roll).append("\n");
        receipt.append("Phone Number: ").append(phone).append("\n");
        receipt.append("Year of Passing: ").append(year).append("\n");
        receipt.append("Stream: ").append(stream).append("\n");
        receipt.append("Base Fee: ₹").append(baseFee).append("\n");
        if (hostelFee > 0) receipt.append("Hostel Fee: ₹").append(hostelFee).append("\n");
        if (busFee > 0) receipt.append("Bus Fee: ₹").append(busFee).append("\n");
        receipt.append("Total Fee to be Paid: ₹").append(totalFee).append("\n");
        receipt.append("------------------------------------");

        taReceipt.setText(receipt.toString());

        // Save to file
        try (FileWriter writer = new FileWriter("FeeReceipt_" + roll + ".txt")) {
            writer.write(receipt.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving receipt: " + ex.getMessage());
        }

        // Show Desktop Notification
        showDesktopNotification("Fee Reminder: ₹" + totalFee + " due for " + name);
    }

    // Desktop Notification Method
    private void showDesktopNotification(String message) {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

                TrayIcon trayIcon = new TrayIcon(image, "Fee Reminder System");
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("Fee Reminder System");
                tray.add(trayIcon);

                trayIcon.displayMessage("Fee Reminder", message, TrayIcon.MessageType.INFO);

                // Remove the tray icon after a few seconds
                new Timer(5000, e -> tray.remove(trayIcon)).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void resetFields() {
        tfName.setText("");
        tfFatherName.setText("");
        tfRollNo.setText("");
        tfPhone.setText("");
        tfYear.setText("");
        cbStream.setSelectedIndex(0);
        cbHostel.setSelected(false);
        cbBus.setSelected(false);
        taReceipt.setText("");
    }

    public static void main(String[] args) {
        new FeeRemainderSystem();
    }
} 