import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class UserInterface extends JFrame{
    //UI variables
    JButton createNoteButton = new JButton("Create Note");
    JButton createChordButton = new JButton("Create Chord");
    JButton exportToWavButton = new JButton("Export to .wav");
    JLabel sampleSizeLabel = new JLabel("Sample Size: ");
    String[] sampleSizes = {"8-bit", "16-bit"};
    JComboBox<String> sampleSizeDropdown = new JComboBox<>(sampleSizes);

    JLabel sampleRateLabel = new JLabel("Sample Rate: ");
    JTextField sampleRateField = new JTextField(10);
    JButton setBasicButton = new JButton("Set Basic");

    //Backend variables
    int mainSampleSize = 8;
    float mainSampleRate = 44100;
    String[] basicNoteArray, userNoteArray;
    String basicNotesPath = "notes/basicNotes.txt";
    String userNotesPath = "notes/userNotes.txt";
    // SoundNote[] mainNotesUsedArray = new SoundNote[5];
    // SoundNoteSet[] mainNoteSetsUsedArray = new SoundNoteSet[5];

    private void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamkniecie okna konczy dzialanie aplikacji
        setSize(800, 600);
        setLayout(new FlowLayout());

        if (!(new File(basicNotesPath).isFile())) {
            JOptionPane.showMessageDialog(this, "Error: Could not find file: " + basicNotesPath, "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        } else {
            try {
                basicNoteArray = loadNotesFromFile(basicNotesPath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Could not load basic notes. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                System.exit(0);
            }
        }

        if (!(new File(userNotesPath).isFile())) {
            JOptionPane.showMessageDialog(this, "Error: Could not find file: " + userNotesPath, "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            System.exit(0);
        } else {
            try {
                basicNoteArray = loadNotesFromFile(userNotesPath);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Could not load basic notes. Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                System.exit(0);
            }
        }

        add(sampleSizeLabel);
        add(sampleSizeDropdown);
        add(createNoteButton);
        add(createChordButton);
        add(exportToWavButton);

        add(sampleRateLabel);
        sampleRateField.setText(String.valueOf(mainSampleRate));
        add(sampleRateField);
        add(setBasicButton);
    }
 
    public UserInterface() {
        super("SlaySound 3000");
        initialize();

        createNoteButton.addActionListener(e -> {
            this.setEnabled(false);
            CreateNote createNoteWindow = new CreateNote(this);
            createNoteWindow.setVisible(true);
            System.out.println(createNoteWindow.getCreatedNote().soundName);
            System.out.println(createNoteWindow.getCreatedNote().frequency);
        });

        createChordButton.addActionListener(e -> {
            System.out.println("Button 'Create Chord' clicked");
        });

        exportToWavButton.addActionListener(e -> {
            System.out.println("Button 'Export To Wav' clicked");
        });

        //should recompute all notes and chords
        sampleSizeDropdown.addActionListener(e -> {
            String selectedSize = (String) sampleSizeDropdown.getSelectedItem();
            if(selectedSize.equals("8-bit")) mainSampleSize = 8;
            else mainSampleSize = 16;
            System.out.println("Program sample size: " + mainSampleSize);
        });

        sampleRateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                sampleRateField.addActionListener(enter -> {
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

        setBasicButton.addActionListener(e -> {
            if (mainSampleRate != 44100) {
                mainSampleRate = 44100;
                sampleRateField.setText(String.valueOf(mainSampleRate));
                System.out.println("Sample rate reset to basic: 44100");
            }
        });
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
}
