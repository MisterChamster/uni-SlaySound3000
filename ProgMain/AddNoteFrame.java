import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddNoteFrame extends JFrame {
    JTextField notesUsedField;
    CreateChord parentFrame;

    public AddNoteFrame(CreateChord parentFrame, JTextField notesUsedField) {
        super("Add Note");
        this.parentFrame = parentFrame;
        this.notesUsedField = notesUsedField;
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());

        String[] notes = {"C4", "D4", "E4", "F4", "G4", "A4", "B4"};
        JList<String> notesList = new JList<>(notes);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notesList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel frameButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton addButton = new JButton("Add");

        frameButtonPanel.add(cancelButton);
        frameButtonPanel.add(addButton);
        add(frameButtonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());
        addButton.addActionListener(confirmAction(notesList));
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
