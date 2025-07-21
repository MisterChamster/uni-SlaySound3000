import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.*;
import java.io.*;



public class UserInterface extends JFrame {
    // ======================= Fields =======================
    // UI variables
    private JButton createNoteButton         = new JButton("Create Note");
    private JButton createChordButton        = new JButton("Create Chord");
    private JButton exportToWavButton        = new JButton("Export to .wav");
    private JButton deleteNoteButton         = new JButton("Delete Note");
    private JButton deleteChordButton        = new JButton("Delete Chord");
    private JButton setBasicSampleRateButton = new JButton("Set Basic");
    private JButton playSoundButton          = new JButton("Play Sound");
    private JLabel  sampleSizeLabel          = new JLabel("Sample Size: ");

    private JComboBox<String> sampleSizeDropdown = new JComboBox<>(new String[] {"8-bit", "16-bit"});
    private JLabel            sampleRateLabel    = new JLabel("Sample Rate: ");
    private JTextField        sampleRateField    = new JTextField(10);

    // Backend variables
    public  int    mainSampleSize   = 8;
    public  float  mainSampleRate   = 44100;
    private String basicNotesPath   = "src/data/notes/basicNotes.txt";
    public  String userNotesPath    = "src/data/notes/userNotes.txt";
    private String basicNotesetPath = "src/data/noteSets/basicNotesets.txt";
    public  String userNotesetPath  = "src/data/noteSets/userNotesets.txt";


    // ===================== Constructors =====================
    public UserInterface() {
        super("SlaySound 3000");
        initialize();
        addListeners();
    }


    // ======================= Methods =======================
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel settingsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        settingsPanel.add(sampleSizeLabel);
        settingsPanel.add(sampleSizeDropdown);
        settingsPanel.add(sampleRateLabel);
        sampleRateField.setText(String.valueOf(mainSampleRate));
        settingsPanel.add(sampleRateField);
        settingsPanel.add(new JLabel());
        settingsPanel.add(setBasicSampleRateButton);

        settingsPanel.setPreferredSize(new Dimension(400, 150));

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(settingsPanel, gbc);

        JPanel actionsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionsPanel.add(createNoteButton);
        actionsPanel.add(createChordButton);
        actionsPanel.add(playSoundButton);
        actionsPanel.add(deleteNoteButton);
        actionsPanel.add(deleteChordButton);
        actionsPanel.add(exportToWavButton);

        actionsPanel.setPreferredSize(new Dimension(400, 150));

        gbc.gridy = 1;
        mainPanel.add(actionsPanel, gbc);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        createNoteButton.addActionListener(_ -> {
            createNoteListenFunction();
        });

        createChordButton.addActionListener(_ -> {
            createChordListenFunction();
        });

        exportToWavButton.addActionListener(_ -> {
            exportToWavListenFunction();
        });

        deleteNoteButton.addActionListener(_ -> {
            deleteNoteListenFunction();
        });

        deleteChordButton.addActionListener(_ -> {
            deleteChordListenFunction();
        });

        playSoundButton.addActionListener(_ -> {
            playSoundListenFunction();
        });

        // should recompute all notes and chords
        sampleSizeDropdown.addActionListener(_ -> {
            String selectedSize = (String) sampleSizeDropdown.getSelectedItem();
            if (selectedSize.equals("8-bit"))
                mainSampleSize = 8;
            else
                mainSampleSize = 16;
            // System.out.println("Program sample size: " + mainSampleSize);
        });

        addSampleRateListeners();
    }

    private void createNoteListenFunction() {
        this.setEnabled(false);
        CreateNote createNoteWindow = new CreateNote(this);
        createNoteWindow.setVisible(true);

        createNoteWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (createNoteWindow.getCreatedNote().soundName != null) {
                    String noteToFile = createNoteWindow.getCreatedNote().soundName + " "
                            + createNoteWindow.getCreatedNote().frequency;

                    try (FileWriter writer = new FileWriter(userNotesPath, true)) {
                        writer.write(noteToFile + "\n");
                    } catch (IOException ex) {
                        // I don't know how to test this, but should be alright I guess(?)
                        JOptionPane.showMessageDialog(null, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                setEnabled(true);
            }
        });
    }

    private void createChordListenFunction() {
        this.setEnabled(false);
        CreateChord createChordWindow = new CreateChord(this);
        createChordWindow.setVisible(true);

        createChordWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (createChordWindow.getCreatedNoteset().soundName != null) {
                    String notesetToFile = createChordWindow.getCreatedNoteset().soundName;
                    for (SoundNote note : createChordWindow.getCreatedNoteset().noteArray) {
                        notesetToFile += ", " + note.soundName;
                    }
                    System.out.println(notesetToFile);

                    try (FileWriter writer = new FileWriter(userNotesetPath, true)) {
                        writer.write(notesetToFile + "\n");
                    } catch (IOException ex) {
                        // I don't know how to test this, but should be alright I guess(?)
                        JOptionPane.showMessageDialog(null, "Exception " + ex, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                setEnabled(true);
            }
        });
    }

    private void exportToWavListenFunction() {
        this.setEnabled(false);
        ExportWAV exportWAVWindow = new ExportWAV(this);
        exportWAVWindow.setVisible(true);
        exportWAVWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
            }
        });
    }

    private void playSoundListenFunction() {
        this.setEnabled(false);
        PlaySound playSoundWindow = new PlaySound(this);
        playSoundWindow.setVisible(true);
        playSoundWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
            }
        });
    }

    private void deleteNoteListenFunction() {
        this.setEnabled(false);
        DeleteNote delNoteWindow = new DeleteNote(this);
        delNoteWindow.setVisible(true);
        delNoteWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
            }
        });
    }

    private void deleteChordListenFunction() {
        this.setEnabled(false);
        DeleteNoteset delNotesetWindow = new DeleteNoteset(this);
        delNotesetWindow.setVisible(true);
        delNotesetWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setEnabled(true);
            }
        });
    }

    private void addSampleRateListeners() {
        sampleRateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                sampleRateField.addActionListener(_ -> {
                    sampleRateField.transferFocus();
                });
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = sampleRateField.getText();
                try {
                    float newSampleRate = Float.parseFloat(text);
                    if (newSampleRate > 0 && newSampleRate <= 200000) {
                        if (mainSampleRate != newSampleRate) {
                            mainSampleRate = newSampleRate;
                            System.out.println("Sample rate set to: " + mainSampleRate);
                        }
                    } else {
                        sampleRateField.setText(String.valueOf(mainSampleRate));
                        JOptionPane.showMessageDialog(null,
                                "Sample rate must be between 0 and 200000.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    sampleRateField.setText(String.valueOf(mainSampleRate));
                    JOptionPane.showMessageDialog(null,
                            "Sample rate must be a valid float number.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setBasicSampleRateButton.addActionListener(_ -> {
            if (mainSampleRate != 44100) {
                mainSampleRate = 44100;
                sampleRateField.setText(String.valueOf(mainSampleRate));
                System.out.println("Sample rate reset to basic: 44100");
            }
        });
    }

    public String[] getBasicNoteArray() {
        String[] basicNoteArray = new String[0];

        if (!(new File(basicNotesPath).isFile())) {
            JOptionPane.showMessageDialog(null, "Error: Could not find file: " + basicNotesPath, "Error",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        } else {
            try {
                basicNoteArray = loadNotesFromFile(basicNotesPath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not load basic notes. Error: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                System.exit(0);
            }
        }

        return basicNoteArray;
    }

    public String[] getUserNoteArray() {
        String[] userNoteArray = new String[0];

        if (!(new File(userNotesPath).isFile())) {
            JOptionPane.showMessageDialog(null, "Error: Could not find file: " + userNotesPath, "Error",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        } else {
            try {
                userNoteArray = loadNotesFromFile(userNotesPath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not load user notes. Error: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                System.exit(0);
            }
        }

        return userNoteArray;
    }

    public String[] getBasicNotesetArray() {
        String[] basicNotesetArray = new String[0];

        if (!(new File(basicNotesetPath).isFile())) {
            JOptionPane.showMessageDialog(null, "Error: Could not find file: " + basicNotesetPath, "Error",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        } else {
            try {
                basicNotesetArray = loadNotesFromFile(basicNotesetPath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not load basic noteset. Error: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                System.exit(0);
            }
        }

        return basicNotesetArray;
    }

    public String[] getUserNotesetArray() {
        String[] userNotesetArray = new String[0];

        if (!(new File(userNotesetPath).isFile())) {
            JOptionPane.showMessageDialog(null, "Error: Could not find file: " + userNotesetPath, "Error",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        } else {
            try {
                userNotesetArray = loadNotesFromFile(userNotesetPath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not load user noteset. Error: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                System.exit(0);
            }
        }

        return userNotesetArray;
    }

    private String[] loadNotesFromFile(String filePath) {
        List<String> notesList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                notesList.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return notesList.toArray(new String[0]);
    }

    public Boolean isNoteNameInNoteArray(String noteName, String[] basicNoteArray) {
        for (String name : basicNoteArray) {
            if (noteName.equals(name.split(" ")[0]))
                return true;
        }
        return false;
    }

    public Boolean isNoteFrequencyInNoteArray(float noteFrequency, String[] basicNoteArray) {
        for (String freq : basicNoteArray) {
            if (noteFrequency == Float.parseFloat(freq.split(" ")[1]))
                return true;
        }
        return false;
    }

    public Boolean isNotesetNameInNotesetArray(String notesetName, String[] notesetArray) {
        String[] names = new String[notesetArray.length];
        for (int i = 0; i < notesetArray.length; i++) {
            names[i] = notesetArray[i].split(", ")[0];
        }
        for (String name : names) {
            if (name.equals(notesetName))
                return true;
        }
        return false;
    }

    public Boolean doesNotesetWithTheseNotesExist(String[] notes, String[] notesetArray) {
        Arrays.sort(notes);
        for (String node : notesetArray) {
            String[] splitNode = node.split(", ");
            String[] setOfNotes = new String[splitNode.length - 1];
            for (int i = 1; i < splitNode.length; i++) {
                setOfNotes[i - 1] = splitNode[i];
            }
            Arrays.sort(setOfNotes);

            if (Arrays.equals(notes, setOfNotes))
                return true;
        }
        return false;
    }

    public float getNoteFreqFromFile(String noteName, String arg) {
        String[] loadArr = new String[0];
        if (arg.equals("basic"))
            loadArr = getBasicNoteArray();
        if (arg.equals("user"))
            loadArr = getUserNoteArray();

        for (String noteLine : loadArr) {
            String[] splitNote = noteLine.split(" ");
            String strFreq = splitNote[splitNote.length - 1];
            float freq = Float.parseFloat(strFreq);

            splitNote = Arrays.copyOf(splitNote, splitNote.length - 1);
            if (noteName.equals(String.join(" ", splitNote)))
                return freq;
        }
        return 0.0f;
    }

    public String[] getNotesetNoteNamesFromFile(String notesetName, String arg) {
        String[] loadArr = new String[0];
        if (arg.equals("basic"))
            loadArr = getBasicNotesetArray();
        if (arg.equals("user"))
            loadArr = getUserNotesetArray();

        for (String notesetLine : loadArr) {
            String[] splitNoteset = notesetLine.split(", ");

            if (notesetName.equals(splitNoteset[0])) {
                String[] noteNames = new String[splitNoteset.length - 1];
                for (int i = 1; i < splitNoteset.length; i++) {
                    noteNames[i - 1] = splitNoteset[i];
                }
                return noteNames;
            }
        }
        throw new RuntimeException("Noteset not found: " + notesetName);
    }
}
