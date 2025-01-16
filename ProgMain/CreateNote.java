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
    UserInterface parentFrame;
    JTextField noteNameField;
    JTextField frequencyField;
    JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton discardButton = new JButton("Discard");
    JButton createButton = new JButton("Create");

    // Sound variables
    SoundNote createdNote = new SoundNote(0, " ");
    // String[] basicNoteArray, userNoteArray;
    // String basicNotesPath = "notes/basicNotes.txt";
    // String userNotesPath = "notes/userNotes.txt";

    public CreateNote(UserInterface parentFrame) {
        super("Create Note");
        this.parentFrame = parentFrame;
        initialize();
        addListeners();
    }

    private void initialize(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10)); 
        setLocationRelativeTo(null); 

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

        parentFrame.updateBasicNoteArray();
        parentFrame.updateUserNoteArray();
        
        // if (!(new File(basicNotesPath).isFile())) {
        //     JOptionPane.showMessageDialog(this, "Error: Could not find file: " + basicNotesPath, "Error", JOptionPane.ERROR_MESSAGE);
        //     this.dispose();
        //     System.exit(0);
        // } else {
        //     try {
        //         basicNoteArray = loadNotesFromFile(basicNotesPath);
        //     } catch (Exception e) {
        //         JOptionPane.showMessageDialog(this, "Could not load basic notes. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //         this.dispose();
        //         System.exit(0);
        //     }
        // }

        // if (!(new File(userNotesPath).isFile())) {
        //     JOptionPane.showMessageDialog(this, "Error: Could not find file: " + userNotesPath, "Error", JOptionPane.ERROR_MESSAGE);
        //     this.dispose();
        //     System.exit(0);
        // } else {
        //     try {
        //         userNoteArray = loadNotesFromFile(userNotesPath);
        //     } catch (Exception e) {
        //         JOptionPane.showMessageDialog(this, "Could not load user notes. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        //         this.dispose();
        //         System.exit(0);
        //     }
        // }
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

        if (noteName.isEmpty() || noteName.length() > 20) {
            JOptionPane.showMessageDialog(this,
                    "Note name cannot be empty and must be 20 characters or less.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            float frequency = Float.parseFloat(frequencyText);
            if (frequency > 0 && frequency <= 22000) {
                if (parentFrame.isNoteNameInBasicNoteArray(noteName)){
                    JOptionPane.showMessageDialog(this, "Note named " + noteName + " already exists in basicNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (parentFrame.isNoteNameInUserNoteArray(noteName)){
                    JOptionPane.showMessageDialog(this, "Note named " + noteName + " already exists in userNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (parentFrame.isNoteFrequencyInBasicNoteArray(frequency)){
                    JOptionPane.showMessageDialog(this, "Note with frequency " + frequency + " exists in basicNotes.txt", "Error", JOptionPane.ERROR_MESSAGE);
                }

                else if (parentFrame.isNoteFrequencyInUserNoteArray(frequency)){
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