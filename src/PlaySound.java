import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PlaySound extends JFrame {
    // ======================= Fields =======================
    JTextField durationField;
    JComboBox<String> dropdown1, dropdown2, dropdown3, dropdown4;
    JButton cancelButton, playButton;
    UserInterface parentFrame;


    // ===================== Constructors =====================
    public PlaySound(UserInterface parentFrame) {
        super("Play");
        this.parentFrame = parentFrame;
        initialize();
        addListeners();
    }


    // ======================= Methods =======================
    private void initialize() {
        String[] tempArray = new String[0];
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(parentFrame);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.insets = new Insets(9, 10, 9, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Basic notes:"), gbc);

        gbc.gridx = 1;
        tempArray = getNoteNamesArrWithEmpty("basic");
        dropdown1 = new JComboBox<>(tempArray);
        mainPanel.add(dropdown1, gbc);

        gbc.insets = new Insets(9, 10, 9, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("User notes:"), gbc);

        gbc.gridx = 1;
        tempArray = getNoteNamesArrWithEmpty("user");
        dropdown2 = new JComboBox<>(tempArray);
        mainPanel.add(dropdown2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Basic chords:"), gbc);

        gbc.gridx = 1;
        tempArray = getNotesetNamesArrWithEmpty("basic");
        dropdown3 = new JComboBox<>(tempArray);
        mainPanel.add(dropdown3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("User chords:"), gbc);

        gbc.gridx = 1;
        tempArray = getNotesetNamesArrWithEmpty("user");
        dropdown4 = new JComboBox<>(tempArray);
        mainPanel.add(dropdown4, gbc);

        gbc.insets = new Insets(9, 10, 3, 10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Duration:"), gbc);

        gbc.gridx = 1;
        durationField = new JTextField(20);
        mainPanel.add(durationField, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        cancelButton = new JButton("Cancel");
        playButton = new JButton("Play");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        playButton.setPreferredSize(new Dimension(100, 40));
        buttonPanel.add(cancelButton);
        buttonPanel.add(playButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> dispose());
        playButton.addActionListener(e -> {
            playButtonListenFunction();
        });
        dropdown1.addActionListener(e -> {
            if (dropdown1.getSelectedItem() != " --empty-- ") {
                if (dropdown2.getSelectedItem() != " --empty-- ")
                    dropdown2.setSelectedIndex(0);
                if (dropdown3.getSelectedItem() != " --empty-- ")
                    dropdown3.setSelectedIndex(0);
                if (dropdown4.getSelectedItem() != " --empty-- ")
                    dropdown4.setSelectedIndex(0);
            }
            ;
        });
        dropdown2.addActionListener(e -> {
            if (dropdown2.getSelectedItem() != " --empty-- ") {
                if (dropdown1.getSelectedItem() != " --empty-- ")
                    dropdown1.setSelectedIndex(0);
                if (dropdown3.getSelectedItem() != " --empty-- ")
                    dropdown3.setSelectedIndex(0);
                if (dropdown4.getSelectedItem() != " --empty-- ")
                    dropdown4.setSelectedIndex(0);
            }
            ;
        });
        dropdown3.addActionListener(e -> {
            if (dropdown3.getSelectedItem() != " --empty-- ") {
                if (dropdown1.getSelectedItem() != " --empty-- ")
                    dropdown1.setSelectedIndex(0);
                if (dropdown2.getSelectedItem() != " --empty-- ")
                    dropdown2.setSelectedIndex(0);
                if (dropdown4.getSelectedItem() != " --empty-- ")
                    dropdown4.setSelectedIndex(0);
            }
            ;
        });
        dropdown4.addActionListener(e -> {
            if (dropdown4.getSelectedItem() != " --empty-- ") {
                if (dropdown1.getSelectedItem() != " --empty-- ")
                    dropdown1.setSelectedIndex(0);
                if (dropdown2.getSelectedItem() != " --empty-- ")
                    dropdown2.setSelectedIndex(0);
                if (dropdown3.getSelectedItem() != " --empty-- ")
                    dropdown3.setSelectedIndex(0);
            }
            ;
        });
    }

    private void playButtonListenFunction() {
        if (durationField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Duration field is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String durationText = durationField.getText();

        double duration;
        try {
            duration = Double.parseDouble(durationText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parentFrame, "Duration must be a number!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String soundName = (String) dropdown1.getSelectedItem();
        if (!soundName.equals(" --empty-- ")) {
            SoundNote dd1note = new SoundNote(parentFrame.mainSampleRate,
                    parentFrame.mainSampleSize,
                    duration,
                    parentFrame.getNoteFreqFromFile(soundName, "basic"),
                    soundName);
            dd1note.prepareToPlay();
            dd1note.start();
            return;
        }
        soundName = (String) dropdown2.getSelectedItem();
        if (!soundName.equals(" --empty-- ")) {
            SoundNote dd2note = new SoundNote(parentFrame.mainSampleRate,
                    parentFrame.mainSampleSize,
                    duration,
                    parentFrame.getNoteFreqFromFile(soundName, "user"),
                    soundName);
            dd2note.prepareToPlay();
            dd2note.start();
            return;
        }

        soundName = (String) dropdown3.getSelectedItem();
        if (!soundName.equals(" --empty-- ")) {
            String[] noteNames = parentFrame.getNotesetNoteNamesFromFile(soundName, "basic");
            SoundNote[] noteArray = new SoundNote[noteNames.length];
            for (int i = 0; i < noteArray.length; i++) {
                noteArray[i] = new SoundNote(parentFrame.mainSampleRate,
                        parentFrame.mainSampleSize,
                        duration,
                        parentFrame.getNoteFreqFromFile(noteNames[i], "basic"),
                        soundName);
            }
            SoundNoteSet dd1noteset = new SoundNoteSet(parentFrame.mainSampleRate,
                    parentFrame.mainSampleSize,
                    duration,
                    noteArray,
                    soundName);
            dd1noteset.prepareToPlay();
            dd1noteset.start();
            return;
        }
        soundName = (String) dropdown4.getSelectedItem();
        if (!soundName.equals(" --empty-- ")) {
            String[] noteNames = parentFrame.getNotesetNoteNamesFromFile(soundName, "user");
            SoundNote[] noteArray = new SoundNote[noteNames.length];
            for (int i = 0; i < noteArray.length; i++) {
                float freq = parentFrame.getNoteFreqFromFile(noteNames[i], "user");
                if (freq == 0)
                    freq = parentFrame.getNoteFreqFromFile(noteNames[i], "basic");
                noteArray[i] = new SoundNote(parentFrame.mainSampleRate,
                        parentFrame.mainSampleSize,
                        duration,
                        freq,
                        soundName);
            }
            SoundNoteSet dd2noteset = new SoundNoteSet(parentFrame.mainSampleRate,
                    parentFrame.mainSampleSize,
                    duration,
                    noteArray,
                    soundName);
            dd2noteset.prepareToPlay();
            dd2noteset.start();
            return;
        }

        JOptionPane.showMessageDialog(parentFrame, "Choose a sound to play it.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String[] getNoteNamesArrWithEmpty(String arg) {
        String[] loadArr = new String[0];
        if (arg.equals("basic"))
            loadArr = parentFrame.getBasicNoteArray();
        if (arg.equals("user"))
            loadArr = parentFrame.getUserNoteArray();

        String[] tempArr = new String[loadArr.length + 1];
        tempArr[0] = " --empty-- ";
        for (int i = 0; i < loadArr.length; i++) {
            tempArr[i + 1] = String.join(" ", Arrays.copyOf(loadArr[i].split(" "), loadArr[i].split(" ").length - 1));
        }
        return tempArr;
    }

    private String[] getNotesetNamesArrWithEmpty(String arg) {
        String[] loadArr = new String[0];
        if (arg.equals("basic"))
            loadArr = parentFrame.getBasicNotesetArray();
        if (arg.equals("user"))
            loadArr = parentFrame.getUserNotesetArray();

        String[] tempArr = new String[loadArr.length + 1];
        tempArr[0] = " --empty-- ";
        for (int i = 0; i < loadArr.length; i++) {
            tempArr[i + 1] = loadArr[i].split(", ")[0];
        }
        return tempArr;
    }
}
