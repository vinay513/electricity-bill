import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;


class Transaction {
    private String type;
    private double amount;
    private Date timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}

class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        } else {
            return false;
        }
    }
}

public class BankManagementSystemGUI {
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bank Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton createAccountBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceInquiryBtn = new JButton("Balance Inquiry");
        JButton transactionHistoryBtn = new JButton("Transaction History");
        JButton exitBtn = new JButton("Exit");

        createAccountBtn.addActionListener(e -> createAccount());
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        balanceInquiryBtn.addActionListener(e -> balanceInquiry());
        transactionHistoryBtn.addActionListener(e -> transactionHistory());
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(createAccountBtn);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(balanceInquiryBtn);
        panel.add(transactionHistoryBtn);
        panel.add(exitBtn);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void createAccount() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        String accountHolderName = JOptionPane.showInputDialog("Enter account holder name:");
        double initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial balance:"));

        Account account = new Account(accountNumber, accountHolderName, initialBalance);
        accounts.put(accountNumber, account);
        JOptionPane.showMessageDialog(null, "Account created successfully");
    }

    private static void deposit() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        Account account = accounts.get(accountNumber);
        if (account != null) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to deposit:"));
            account.deposit(amount);
            JOptionPane.showMessageDialog(null, "Deposited: $" + amount);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private static void withdraw() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        Account account = accounts.get(accountNumber);
        if (account != null) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to withdraw:"));
            if (account.withdraw(amount)) {
                JOptionPane.showMessageDialog(null, "Withdrawn: $" + amount);
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient balance");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private static void balanceInquiry() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        Account account = accounts.get(accountNumber);
        if (account != null) {
            JOptionPane.showMessageDialog(null, "Balance: $" + account.getBalance());
        } else {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }

    private static void transactionHistory() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        Account account = accounts.get(accountNumber);
        if (account != null) {
            List<Transaction> transactions = account.getTransactionHistory();
            if (transactions.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No transactions found");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Transaction transaction : transactions) {
                    sb.append(transaction).append("\n");
                }
                JOptionPane.showMessageDialog(null, "Transaction History:\n" + sb.toString());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Account not found");
        }
    }
}
