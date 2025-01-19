import javax.swing.*;
import java.awt.*;

public class DeleteNote extends JFrame {
    JComboBox<String> dropdown1;
    JButton cancelButton, deleteButton;
    UserInterface parentFrame;
    String[] userNoteArray;

    String delString = "";

    public DeleteNote(UserInterface parentFrame) {
        super("Delete user note");
        this.parentFrame = parentFrame;
        this.userNoteArray = parentFrame.getUserNoteArray();
        initialize();
        addListeners();
    }

    private void initialize() {
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
        mainPanel.add(new JLabel("Notes:"), gbc);

        gbc.weightx = 1.5;
        gbc.gridx = 1;
        dropdown1 = new JComboBox<>(userNoteArray);
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
            String selectedNote = (String) dropdown1.getSelectedItem();
            delString = selectedNote;
            dispose();
        });
    }
}