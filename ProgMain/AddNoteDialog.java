import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddNoteDialog extends JDialog {
    private JTextField notesUsedField;

    public AddNoteDialog(Frame owner, JTextField notesUsedField) {
        super(owner, "Add Note", true);
        this.notesUsedField = notesUsedField;
        initialize();
    }

    private void initialize() {
        setSize(350, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());

        String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4"};
        JList<String> notesList = new JList<>(notes);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notesList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton confirmButton = new JButton("Add");

        dialogButtonPanel.add(cancelButton);
        dialogButtonPanel.add(confirmButton);
        add(dialogButtonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());

        confirmButton.addActionListener(confirmAction(notesList));

        setVisible(true);
    }

    private ActionListener confirmAction(JList<String> notesList) {
        return e -> {
            String selectedNote = notesList.getSelectedValue();
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