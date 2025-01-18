import javax.swing.*;
import java.awt.*;

public class PlaySound extends JFrame {
    JTextField durationField;
    JComboBox<String> dropdown1, dropdown2, dropdown3, dropdown4;
    JButton cancelButton, playButton;

    public PlaySound(JFrame parentFrame) {
        super("Play");
        initialize(parentFrame);
        addListeners();
    }

    private void initialize(JFrame parentFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(parentFrame); 

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.insets = new Insets(9, 10, 9, 10); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Basic notes:"), gbc);

        gbc.gridx = 1;
        dropdown1 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown1, gbc);

        gbc.insets = new Insets(9, 10, 9, 10); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("User notes:"), gbc);

        gbc.gridx = 1;
        dropdown2 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Basic chords:"), gbc);

        gbc.gridx = 1;
        dropdown3 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("User chords:"), gbc);

        gbc.gridx = 1;
        dropdown4 = new JComboBox<>(new String[]{"A", "B", "C"});
        mainPanel.add(dropdown4, gbc);

        gbc.insets = new Insets(9, 10, 3, 10); 
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Duration:"), gbc);

        gbc.gridx = 1;
        durationField = new JTextField(20);
        mainPanel.add(durationField, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        cancelButton = new JButton("Cancel");
        playButton = new JButton("Play");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        playButton.setPreferredSize(new Dimension(100, 40));
        buttonPanel.add(cancelButton);
        buttonPanel.add(playButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> dispose());
        playButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Playing sound with selected options!", "Play", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}