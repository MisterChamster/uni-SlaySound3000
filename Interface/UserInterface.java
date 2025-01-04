import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface  extends JFrame{

    public UserInterface() {
        super("User Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamkniecie okna konczy dzialanie aplikacji
        setSize(800,600);
        setLayout(new FlowLayout());

        JButton CreateNoteButton = new JButton("Create Note");
        JButton CreateChordButton = new JButton("Create Chord");
        JButton ExportToWavButton = new JButton("Export to .wav");

        CreateNoteButton.addActionListener(e -> {
            System.out.println("Button 'Create Note' clicked");
        });

        CreateChordButton.addActionListener(e -> {
            System.out.println("Button 'Create Chord' clicked");
        });

        ExportToWavButton.addActionListener(e -> {
            System.out.println("Button 'Export To Wav' clicked");
        });
        
        JLabel SampleSizeLabel = new JLabel("Sample Size: ");
        String[] sampleSizes = {"8-bit", "16-bit"};
        JComboBox<String> sampleSizeDropdown = new JComboBox<>(sampleSizes);

        sampleSizeDropdown.addActionListener(e -> {
            String selectedSize = (String) sampleSizeDropdown.getSelectedItem();
            System.out.println("Selected Sample Size: "+ selectedSize);
        });

        add(CreateNoteButton);
        add(CreateChordButton);
        add(ExportToWavButton);
        add(SampleSizeLabel);
        add(sampleSizeDropdown);
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.setVisible(true);
    }
}