import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class CreateChord extends JFrame {
    // UI Components
    UserInterface parentFrame;
    private JTextField chordNameField;
    JTextField notesUsedField;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JButton discardButton;
    private JButton createButton;
    private JButton addNoteButton;
    private JButton deleteNoteButton;

    // Backend variables
    SoundNoteSet createdNoteset;

    public CreateChord(UserInterface parentFrame) {
        super("Create Chord");
        this.parentFrame = parentFrame;
        this.createdNoteset = new SoundNoteSet();
        initialize();
        addListeners();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400); 
        setLayout(new BorderLayout(15, 15)); 
        setLocationRelativeTo(null); 

        inputPanel = new JPanel(new GridBagLayout()); 
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Name:", SwingConstants.CENTER), gbc); 

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        chordNameField = new JTextField(30);
        inputPanel.add(chordNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Notes used:", SwingConstants.CENTER), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        notesUsedField = new JTextField(30);
        notesUsedField.setEditable(false);
        inputPanel.add(notesUsedField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        addNoteButton = new JButton("Add note...");
        deleteNoteButton = new JButton("Delete note...");
        addNoteButton.setPreferredSize(new Dimension(140, 40));
        deleteNoteButton.setPreferredSize(new Dimension(140, 40));

        JPanel notesButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        notesButtonPanel.add(addNoteButton);
        notesButtonPanel.add(deleteNoteButton);
        inputPanel.add(notesButtonPanel, gbc);
        add(inputPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        discardButton = new JButton("Discard");
        createButton = new JButton("Create");
        discardButton.setPreferredSize(new Dimension(140, 40));
        createButton.setPreferredSize(new Dimension(140, 40));
        buttonPanel.add(discardButton);
        buttonPanel.add(createButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (parentFrame != null) {
                    parentFrame.setEnabled(true);
                }
            }
        });

        discardButton.addActionListener(e -> {
            dispose();
            if (parentFrame != null) {
                parentFrame.setEnabled(true);
            }
        });

        createButton.addActionListener(e -> {createFunction();});
        addNoteButton.addActionListener(e -> openAddNoteToNoteSetFrame());
        deleteNoteButton.addActionListener(e -> openDeleteNoteToNoteSetFrame());
    }

    public void createFunction() {
        String createdNotesetName = chordNameField.getText();

        if (createdNotesetName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chord name cannot be empty.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (createdNotesetName.equals(" --empty-- ") || createdNotesetName.equals(" --used-- ")) {
            JOptionPane.showMessageDialog(this, "Chord name cannot be --empty-- or --used--.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (createdNotesetName.contains(",")) {
            JOptionPane.showMessageDialog(this, "Chord name cannot contain commas.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (getNotesUsedArr().length < 2) {
            JOptionPane.showMessageDialog(this, "You must add at least two notes to create a chord.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            String[] tempArr = getNotesUsedArr();
            for (int i = 0; i < tempArr.length; i++) {
                String tempName, tempFreq;
                String[] divider = tempArr[i].split(" ");
                tempFreq = divider[divider.length - 1];
                divider = Arrays.copyOf(divider, divider.length-1);
                tempName = String.join(" ", divider);
                createdNoteset.addNote(new SoundNote(tempName, Float.parseFloat(tempFreq)));
            }
        }

        //START HERE
        String[] basicNotesetArray = parentFrame.getBasicNotesetArray();
        String[] userNotesetArray = parentFrame.getUserNotesetArray();

        if (parentFrame.isNotesetNameInNotesetArray(createdNotesetName, basicNotesetArray)) {
            JOptionPane.showMessageDialog(this, "Chord named " + createdNotesetName + " already exists in basicNotesets.txt",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (parentFrame.isNotesetNameInNotesetArray(createdNotesetName, userNotesetArray)) {
            JOptionPane.showMessageDialog(this, "Chord named " + createdNotesetName + " already exists in userNotesets.txt",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] notesInCreatedNoteset = new String[createdNoteset.noteArray.length];
        for (int i=0; i<createdNoteset.noteArray.length; i++) {
            notesInCreatedNoteset[i] = createdNoteset.noteArray[i].soundName;
        }

        if (parentFrame.doesNotesetWithTheseNotesExist(notesInCreatedNoteset, basicNotesetArray)) {
            JOptionPane.showMessageDialog(this, "Chord with notes " + Arrays.toString(notesInCreatedNoteset) + " already exists in basicNotesets.txt",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (parentFrame.doesNotesetWithTheseNotesExist(notesInCreatedNoteset, userNotesetArray)) {
            JOptionPane.showMessageDialog(this, "Chord with notes " + Arrays.toString(notesInCreatedNoteset) + " already exists in userNotesets.txt",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        createdNoteset.soundName = createdNotesetName;
        dispose();
        if (parentFrame != null) {
            parentFrame.setEnabled(true);
        }
    }

    public String[] getNotesUsedArr() {
        return notesUsedField.getText().split(", ");
    }

    public void sortNotesUsedArr() {
        String[] tempArr = getNotesUsedArr();
        if (tempArr.length <= 1) return;
        Float[] noteFrequencies = new Float[tempArr.length];

        for (int i = 0; i < tempArr.length; i++) {
            String[] divider = tempArr[i].split(" ");
            noteFrequencies[i] = Float.parseFloat(divider[divider.length - 1]);
        }

        for (int i=0; i<noteFrequencies.length-1; i++) {
            float minF;
            for (int j=i+1; j<noteFrequencies.length; j++) {
                if (noteFrequencies[j] < noteFrequencies[i]) {
                    minF = noteFrequencies[j];
                    noteFrequencies[j] = noteFrequencies[i];
                    noteFrequencies[i] = minF;

                    String temp = tempArr[j];
                    tempArr[j] = tempArr[i];
                    tempArr[i] = temp;
                }
            }
            notesUsedField.setText(String.join(", ", tempArr));
        }
    }

    public void deleteNote(String inputNote) {
        String[] tempArr = notesUsedField.getText().split(", ");
        String[] retArr = new String[tempArr.length - 1];
        int index = 0;
        while (index < tempArr.length) {
            if (!tempArr[index].equals(inputNote)) {retArr[index] = tempArr[index];}
            else break;
            index++;
        }
        index++;
        while (index < tempArr.length) {
            retArr[index - 1] = tempArr[index];
            index++;
        }

        String text = "";
        if (retArr.length > 1) {
            for (int i = 0; i < retArr.length - 1; i++) {
                text += retArr[i] + ", ";
            }
        }
        if (retArr.length > 0) {
            text += retArr[retArr.length - 1];
        }
        notesUsedField.setText(text);
    }

    private void openAddNoteToNoteSetFrame() {
        this.setEnabled(false);
        AddNoteToNoteSetFrame addFrame = new AddNoteToNoteSetFrame(this);
        addFrame.setVisible(true);

        addFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                sortNotesUsedArr();
                setEnabled(true);
            }
        });
    }

    private void openDeleteNoteToNoteSetFrame() {
        this.setEnabled(false);
        DeleteNoteToNoteSetFrame delFrame = new DeleteNoteToNoteSetFrame(this);
        delFrame.setVisible(true);

        delFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
            }
        });
    }

    public SoundNoteSet getCreatedNoteset() {
        return createdNoteset;
    }
}