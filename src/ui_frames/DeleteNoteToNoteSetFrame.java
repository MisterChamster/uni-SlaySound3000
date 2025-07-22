package ui_frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;



public class DeleteNoteToNoteSetFrame extends JFrame {
    // ======================= Fields =======================
    private CreateChordFrame parentFrame;
    private JList<String>       notesList;


    // ===================== Constructors =====================
    public DeleteNoteToNoteSetFrame(CreateChordFrame parentFrame) {
        super("Delete Note");
        this.parentFrame = parentFrame;
        initialize();
    }


    // ======================= Methods =======================
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());

        setupScrollPane();

        JPanel frameButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton confirmButton = new JButton("Delete");

        frameButtonPanel.add(cancelButton);
        frameButtonPanel.add(confirmButton);
        add(frameButtonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(_ -> dispose());
        confirmButton.addActionListener(confirmAction(notesList));
    }

    private void setupScrollPane() {
        notesList = new JList<>(parentFrame.getNotesUsedArr());
        // notesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notesList);
        add(scrollPane, BorderLayout.CENTER);
    }

    private ActionListener confirmAction(JList<String> notesList) {
        return _ -> {
            String selectedNote = notesList.getSelectedValue();
            if (selectedNote != null) {
                parentFrame.deleteNote(selectedNote);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please select a note!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
