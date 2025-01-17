import java.awt.*;     //FOR TESTING
import java.awt.event.ActionListener;
import javax.swing.*;     //FOR TESTING
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;


public class AddNoteToNoteSetFrame extends JFrame {
    private CreateChord parentFrame;
    private JComboBox<String> basicNotesDropdown;
    private JComboBox<String> userNotesDropdown;
    private JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JButton cancelButton = new JButton("Cancel");
    private JButton addButton = new JButton("Add");

    JTextField notesUsedField;

    public AddNoteToNoteSetFrame(CreateChord parentFrame) {
        super("Add Note");
        this.parentFrame = parentFrame;
        this.notesUsedField = parentFrame.notesUsedField;
        initialize();
    }

    
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());

        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Basic Notes:"));
        basicNotesDropdown = new JComboBox<>(parentFrame.parentFrame.basicNoteArray);
        inputPanel.add(basicNotesDropdown);
        inputPanel.add(new JLabel("User Notes:"));
        userNotesDropdown = new JComboBox<>(parentFrame.parentFrame.userNoteArray);
        inputPanel.add(userNotesDropdown);
        add(inputPanel, BorderLayout.CENTER);

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());
        addButton.addActionListener(confirmAction(basicNotesDropdown, userNotesDropdown));
    }

    private ActionListener confirmAction(JComboBox<String> notesComboBox, JComboBox<String> userNotesComboBox) {
        return e -> {
            String selectedNote = (String) notesComboBox.getSelectedItem();
            if (selectedNote != null) {
                String currentNotes = notesUsedField.getText();
                notesUsedField.setText(currentNotes.isEmpty() ? selectedNote : currentNotes + ", " + selectedNote);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a note!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
