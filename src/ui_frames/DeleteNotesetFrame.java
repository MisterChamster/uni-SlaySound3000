package ui_frames;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class DeleteNotesetFrame extends JFrame {
    // ======================= Fields =======================
    private JComboBox<String> dropdown1;
    private JButton           cancelButton, deleteButton;
    private MainFrame     parentFrame;
    private String[]          userNotesetArray;


    // ===================== Constructors =====================
    public DeleteNotesetFrame(MainFrame parentFrame) {
        super("Delete user chord");
        this.parentFrame = parentFrame;
        this.userNotesetArray = parentFrame.getUserNotesetArray();
        initialize();
        addListeners();
    }


    // ======================= Methods =======================
    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 170);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(parentFrame);

        for (int i = 0; i < userNotesetArray.length; i++) {
            String[] temp = userNotesetArray[i].split(", ");
            userNotesetArray[i] = temp[0];
        }

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 15, 15, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Chords:"), gbc);

        gbc.weightx = 1.5;
        gbc.gridx = 1;
        dropdown1 = new JComboBox<>(userNotesetArray);
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
        cancelButton.addActionListener(_ -> dispose());
        deleteButton.addActionListener(_ -> {
            deleteButtonListenFunction();
        });
    }

    private void deleteButtonListenFunction() {
        String delString = (String) dropdown1.getSelectedItem();
        if (!delString.equals("")) {
            deleteNotesetLineFromFileByName(delString);
        }
        dispose();
    }

    private void deleteNotesetLineFromFileByName(String notesetNameToDelete) {
        String tempNoteFile = "src/data/noteSets/temp.txt";
        Boolean delSuccesful = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(parentFrame.userNotesetPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempNoteFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String notesetName = line.split(", ")[0];
                if (!notesetName.equals(notesetNameToDelete)) {
                    writer.write(line + "\n");
                } else
                    System.out.println("Deleted note: " + notesetNameToDelete);
            }
            delSuccesful = true;
        } catch (IOException ex) {
            // I don't know how to test this, but should be alright I guess(?)
            JOptionPane.showMessageDialog(parentFrame, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (delSuccesful) {
            try {
                File OGfile = new File(parentFrame.userNotesetPath);
                File newFile = new File(tempNoteFile);
                OGfile.delete();
                newFile.renameTo(OGfile);
            } catch (Exception ex) {
                // I don't know how to test this, but should be alright I guess(?)
                JOptionPane.showMessageDialog(parentFrame, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
