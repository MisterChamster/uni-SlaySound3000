import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DeleteNoteDialog extends JDialog {
    private JTextField notesUsedField;

    public DeleteNoteDialog(Frame owner, JTextField notesUsedField) {
        super(owner, "Delete Note", true);
        this.notesUsedField = notesUsedField;
        initialize();
    }

    private void initialize() {
        setSize(350, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());

        String[] currentNotesArray = notesUsedField.getText().split(", ");
        JList<String> notesList = new JList<>(currentNotesArray);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notesList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton confirmButton = new JButton("Delete");

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
                String[] currentNotesArray = notesUsedField.getText().split(", ");
                StringBuilder updatedNotes = new StringBuilder();
                for (String note : currentNotesArray) {
                    if (!note.equals(selectedNote)) {
                        if (updatedNotes.length() > 0) {
                            updatedNotes.append(", ");
                        }
                        updatedNotes.append(note);
                    }
                }
                notesUsedField.setText(updatedNotes.toString());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a note!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}