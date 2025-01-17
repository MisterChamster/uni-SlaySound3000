import java.awt.*;     //FOR TESTING
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.*;     //FOR TESTING


public class AddNoteToNoteSetFrame extends JFrame {
    private CreateChord parentFrame;
    private JComboBox<String> basicNotesDropdown;
    private JComboBox<String> userNotesDropdown;
    private JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton cancelButton;
    JButton addButton;

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

        String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4"};
        JComboBox<String> notesComboBox = new JComboBox<>(notes);
        inputPanel.add(new JLabel("Note:"));
        inputPanel.add(notesComboBox);

        add(inputPanel, BorderLayout.CENTER);

        cancelButton = new JButton("Cancel");
        addButton = new JButton("Add");
        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());
        addButton.addActionListener(confirmAction(notesComboBox));
    }

    private ActionListener confirmAction(JComboBox<String> notesComboBox) {
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