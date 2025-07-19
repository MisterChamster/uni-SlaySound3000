import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Arrays;



public class AddNoteToNoteSetFrame extends JFrame {
    // ======================= Fields =======================
    private CreateChord parentFrame;
    private JComboBox<String> basicNotesDropdown;
    private JComboBox<String> userNotesDropdown;
    private JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JButton cancelButton = new JButton("Cancel");
    private JButton addButton = new JButton("Add");

    JTextField notesUsedField;
    String[] basicNoteArray, userNoteArray;


    // ===================== Constructors =====================
    public AddNoteToNoteSetFrame(CreateChord parentFrame) {
        super("Add Note");
        this.parentFrame = parentFrame;
        this.notesUsedField = parentFrame.notesUsedField;
        this.basicNoteArray = parentFrame.parentFrame.getBasicNoteArray();
        this.userNoteArray = parentFrame.parentFrame.getUserNoteArray();
        initialize();
    }


    // ======================= Methods =======================
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(getOwner());

        setupInputPanel();

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> dispose());
        addButton.addActionListener(confirmAction());
    }


    private void setupInputPanel() {
        Boolean wasNoteUsedFlag;
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] tempStringArr = new String[1 + basicNoteArray.length];
        tempStringArr[0] = "  --empty--  ";

        for (int i = 0; i < basicNoteArray.length; i++) {
            wasNoteUsedFlag = false;
            if (parentFrame.getNotesUsedArr() != null) {
                for (String note : parentFrame.getNotesUsedArr()) {
                    if (note.equals(basicNoteArray[i])) {
                        wasNoteUsedFlag = true;
                    }
                }
            }
            if (!wasNoteUsedFlag)
                tempStringArr[i + 1] = basicNoteArray[i];
            else
                tempStringArr[i + 1] = "  --used--  ";
        }
        basicNotesDropdown = new JComboBox<>(tempStringArr);

        tempStringArr = new String[1 + userNoteArray.length];
        tempStringArr[0] = "  --empty--  ";
        for (int i = 0; i < userNoteArray.length; i++) {
            wasNoteUsedFlag = false;
            if (parentFrame.getNotesUsedArr() != null) {
                for (String note : parentFrame.getNotesUsedArr()) {
                    if (note.equals(userNoteArray[i])) {
                        wasNoteUsedFlag = true;
                    }
                }
            }
            if (!wasNoteUsedFlag)
                tempStringArr[i + 1] = userNoteArray[i];
            else
                tempStringArr[i + 1] = "  --used--  ";
        }
        userNotesDropdown = new JComboBox<>(tempStringArr);

        inputPanel.add(new JLabel("Basic Notes:"));
        inputPanel.add(basicNotesDropdown);
        inputPanel.add(new JLabel("User Notes:"));
        inputPanel.add(userNotesDropdown);
        add(inputPanel, BorderLayout.CENTER);
    }


    private ActionListener confirmAction() {
        return e -> {
            String selectedBasicNote = (String) basicNotesDropdown.getSelectedItem();
            String selectedUserNote = (String) userNotesDropdown.getSelectedItem();
            String currentNotes = notesUsedField.getText();
            String[] evilArr = new String[] { "  --empty--  ", "  --used--  " };

            if (!Arrays.asList(evilArr).contains(selectedBasicNote) ||
                    !Arrays.asList(evilArr).contains(selectedUserNote)) {

                if (!selectedBasicNote.equals("  --empty--  ") && !selectedBasicNote.equals("  --used--  ")) {
                    if (currentNotes.length() > 0) {
                        currentNotes += ", ";
                    }
                    currentNotes += selectedBasicNote;
                }
                if (!selectedUserNote.equals("  --empty--  ") && !selectedUserNote.equals("  --used--  ")) {
                    if (currentNotes.length() > 0) {
                        currentNotes += ", ";
                    }
                    currentNotes += selectedUserNote;
                }

                notesUsedField.setText(currentNotes);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Select at least one note.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
