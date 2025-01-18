import javax.swing.*;
import java.awt.*;

public class Del extends JFrame {
    JTextField field1, field2;
    JComboBox<String> dropdown1, dropdown2;
    JButton cancelButton, deleteButton;

    public Del(JFrame parentFrame) {
        super("Delete");
        initialize(parentFrame);
        addListeners();
    }

    private void initialize(JFrame parentFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300); 
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("F1"), gbc);

        gbc.gridx = 1;
        field1 = new JTextField("Text 1", 20);
        mainPanel.add(field1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("1"), gbc);

        gbc.gridx = 1;
        dropdown1 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("F2"), gbc);

        gbc.gridx = 1;
        field2 = new JTextField("Text 2", 20);
        mainPanel.add(field2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("2"), gbc);

        gbc.gridx = 1;
        dropdown2 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown2, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        cancelButton = new JButton("Cancel");
        deleteButton = new JButton("Delete");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        deleteButton.setPreferredSize(new Dimension(100, 40));
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> dispose());
        deleteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Deleted selected item!", "Delete", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        Del delWindow = new Del(null);
        delWindow.setVisible(true);
    }
}
