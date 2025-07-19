import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class DeleteNote extends JFrame {
    // ======================= Fields =======================
    JComboBox<String> dropdown1;
    JButton cancelButton, deleteButton;
    UserInterface parentFrame;
    String[] userNoteArray;

    String delString = "";


    // ===================== Constructors =====================
    public DeleteNote(UserInterface parentFrame) {
        super("Delete user note");
        this.parentFrame = parentFrame;
        this.userNoteArray = parentFrame.getUserNoteArray();
        initialize();
        addListeners();
    }


    // ======================= Methods =======================
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
            deleteButtonListenFunction();
        });
    }

    private void deleteButtonListenFunction() {
        String delString = (String) dropdown1.getSelectedItem();
        if (!delString.equals("")) {
            deleteNoteLineFromFile(delString);
            deleteNotesetLineWithNoteFromFile(delString);
        }
        dispose();
    }

    private void deleteNoteLineFromFile(String delString) {
        String tempNoteFile = "notes/temp.txt";
        Boolean delSuccesful = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(parentFrame.userNotesPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempNoteFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(delString)) {
                    writer.write(line + "\n");
                } else
                    System.out.println("Deleted note: " + delString);
            }
            delSuccesful = true;
        } catch (IOException ex) {
            // I don't know how to test this, but should be alright I guess(?)
            JOptionPane.showMessageDialog(parentFrame, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (delSuccesful) {
            try {
                File OGfile = new File(parentFrame.userNotesPath);
                File newFile = new File(tempNoteFile);
                OGfile.delete();
                newFile.renameTo(OGfile);
            } catch (Exception ex) {
                // I don't know how to test this, but should be alright I guess(?)
                JOptionPane.showMessageDialog(parentFrame, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteNotesetLineWithNoteFromFile(String delString) {
        String delNoteName = String.join(" ", Arrays.copyOf(delString.split(" "), delString.split(" ").length - 1));
        String tempNotesetFile = "noteSets/temp.txt";
        Boolean delSuccesful = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(parentFrame.userNotesetPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempNotesetFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Boolean delLine = false;
                String[] notesInNoteset = line.split(", ");
                for (int i = 1; i < notesInNoteset.length; i++) {
                    if (notesInNoteset[i].equals(delNoteName)) {
                        delLine = true;
                    }
                }
                if (!delLine) {
                    writer.write(line + "\n");
                } else
                    System.out.println("Deleted stuff: " + delNoteName);
            }

            delSuccesful = true;
        } catch (IOException ex) {
            // I don't know how to test this, but should be alright I guess(?)
            JOptionPane.showMessageDialog(parentFrame, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (delSuccesful) {
            try {
                File OGfile = new File(parentFrame.userNotesetPath);
                File newFile = new File(tempNotesetFile);
                OGfile.delete();
                newFile.renameTo(OGfile);
            } catch (Exception ex) {
                // I don't know how to test this, but should be alright I guess(?)
                JOptionPane.showMessageDialog(parentFrame, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
