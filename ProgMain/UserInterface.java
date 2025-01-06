import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JFrame{
    //UI variables
    JButton createNoteButton = new JButton("Create Note");
    JButton createChordButton = new JButton("Create Chord");
    JButton exportToWavButton = new JButton("Export to .wav");
    JLabel sampleSizeLabel = new JLabel("Sample Size: ");
    String[] sampleSizes = {"8-bit", "16-bit"};
    JComboBox<String> sampleSizeDropdown = new JComboBox<>(sampleSizes);

    //Backend variables
    int mainSampleSize = 8;
    float mainSampleRate = 44100;
    String[] basicNoteArray, userNoteArray;
    // SoundNote[] mainNotesUsedArray = new SoundNote[5];
    // SoundNoteSet[] mainNoteSetsUsedArray = new SoundNoteSet[5];


    private void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamkniecie okna konczy dzialanie aplikacji
        setSize(800, 600);
        setLayout(new FlowLayout());

        add(sampleSizeLabel);
        add(sampleSizeDropdown);
        add(createNoteButton);
        add(createChordButton);
        add(exportToWavButton);

    }
 
    public UserInterface() {
        super("SlaySound 3000");
        initialize();

        createNoteButton.addActionListener(e -> {
            this.setEnabled(false);
            CreateNote createNoteWindow = new CreateNote(this);
            createNoteWindow.setVisible(true);
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
    }
}