import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.ArrayList;

//listening to enter in text fields should work as createnote listener
//createButton.addActionListener to addListeners, not separate functions

public class CreateNote extends JFrame {
    // UI variables
    private UserInterface parentFrame;
    private JTextField noteNameField;
    private JTextField frequencyField;
    private JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JButton discardButton = new JButton("Discard");
    private JButton createButton = new JButton("Create");

    // Sound variables
    SoundNote createdNote = new SoundNote();

    public CreateNote(UserInterface parentFrame) {
        super("Create Note");
        this.parentFrame = parentFrame;
        initialize();
        addListeners();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10)); 
        setLocationRelativeTo(getOwner()); 

        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Note name:"));
        noteNameField = new JTextField(15);
        inputPanel.add(noteNameField);
        inputPanel.add(new JLabel("Frequency (Hz):"));
        frequencyField = new JTextField(15); 
        inputPanel.add(frequencyField);
        add(inputPanel, BorderLayout.CENTER);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); 
        discardButton.setPreferredSize(new Dimension(100, 30));
        createButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(discardButton);
        buttonPanel.add(createButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {parentFrame.setEnabled(true);}
        });

        discardButton.addActionListener(e -> {
            dispose(); 
            parentFrame.setEnabled(true); 
        });

        createButton.addActionListener(e -> {createFunction();});

        noteNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                noteNameField.addActionListener(enter -> {
                    createFunction();
                });
            }
            public void focusLost(FocusEvent e) {}
        });

        frequencyField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                frequencyField.addActionListener(enter -> {
                    createFunction();
                });
            }
            public void focusLost(FocusEvent e) {}
        });
    }

    private void createFunction() {
        String noteName = noteNameField.getText();
        String frequencyText = frequencyField.getText();

        if (noteName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Note name cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        else if (noteName.length() > 20) {
            JOptionPane.showMessageDialog(this,
                    "Note name must be 20 characters or less.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        else if (noteName.contains(",")) {
            JOptionPane.showMessageDialog(this,
                    "Note name cannot contain commas.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        else if (noteName == " --empty-- " || noteName == " --used-- ") {
            JOptionPane.showMessageDialog(this,
                    "Note name cannot be --empty-- or --used--.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        else if (frequencyText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Frequency cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            float frequency = Float.parseFloat(frequencyText);
            if (frequency > 0 && frequency <= 22000) {
                String[] basicNoteArray, userNoteArray;
                basicNoteArray = parentFrame.getBasicNoteArray();
                userNoteArray = parentFrame.getUserNoteArray();

                if (parentFrame.isNoteNameInBasicNoteArray(noteName, basicNoteArray)) {
                    JOptionPane.showMessageDialog(this, "Note named " + noteName + " already exists in basicNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (parentFrame.isNoteNameInUserNoteArray(noteName, userNoteArray)) {
                    JOptionPane.showMessageDialog(this, "Note named " + noteName + " already exists in userNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (parentFrame.isNoteFrequencyInBasicNoteArray(frequency, basicNoteArray)) {
                    JOptionPane.showMessageDialog(this, "Note with frequency " + frequency + " exists in basicNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (parentFrame.isNoteFrequencyInUserNoteArray(frequency, basicNoteArray)){
                    JOptionPane.showMessageDialog(this, "Note with frequency " + frequency + " exists in userNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else {
                    createdNote.setNamee(noteName);
                    createdNote.setFrequency(frequency);
                    dispose();
                    parentFrame.setEnabled(true);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Frequency must be between 0 and 22000.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Frequency must be a valid number and cannot be empty.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public SoundNote getCreatedNote() {
        return createdNote;
    }
}