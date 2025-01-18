import javax.swing.*;
import java.awt.*;

public class DeleteNoteset extends JFrame {
    JTextField field1, field2;
    JComboBox<String> dropdown1, dropdown2;
    JButton cancelButton, deleteButton;

    public DeleteNoteset(JFrame parentFrame) {
        super("Delete user chord");
        initialize(parentFrame);
        addListeners();
    }

    private void initialize(JFrame parentFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 170); 
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 15, 15, 12)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Chords:"), gbc);

        gbc.weightx = 1.5;
        gbc.gridx = 1;
        dropdown1 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown1, gbc);

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
}