import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateNote extends JFrame {
    //UI variables
    JTextField noteNameField;
    JTextField frequencyField;
    JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5)); // uklad siatki (GridLayout)
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    //backend variables
    SoundNote createdNote = new SoundNote(0, " ");

    public CreateNote() {
        super("Create Note");
        initialize();
    }

    private void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamkniecie okna konczy dzialanie aplikacji
        setSize(400, 200);
        setLayout(new BorderLayout());

        inputPanel.add(new JLabel("Note Name:"));
        noteNameField = new JTextField();
        inputPanel.add(noteNameField);

        inputPanel.add(new JLabel("Frequency (Hz):"));
        frequencyField = new JTextField();
        inputPanel.add(frequencyField);

        add(inputPanel, BorderLayout.CENTER);

        JButton discardButton = new JButton("Discard");
        JButton createButton = new JButton("Create");

        buttonPanel.add(discardButton);
        buttonPanel.add(createButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }


}