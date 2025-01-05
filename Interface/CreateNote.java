import javax.swing.*;
import java.awt.*;

public class CreateNote extends JFrame {
    JTextField noteNameField;
    JTextField frequencyField;
    JFrame parentFrame;

    JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5)); // uklad siatki (GridLayout)
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public CreateNote(JFrame parentFrame) {
        super("Create Note");
        this.parentFrame = parentFrame;
        initialize();
    }

    private void initialize(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //zamkniecie okna konczy dzialanie aplikacji
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

        discardButton.addActionListener(e -> {
            dispose(); 
            parentFrame.setEnabled(true); 
        });
        JButton createButton = new JButton("Create");

        buttonPanel.add(discardButton);
        buttonPanel.add(createButton);
        createButton.addActionListener(e -> {
        String noteName = noteNameField.getText();
        String frequencyText = frequencyField.getText();

        if (noteName.isEmpty() || !noteName.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this,
                    "Note Name must only contain letters and cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return; 
        }

        try {
            float frequency = Float.parseFloat(frequencyText);
            System.out.println("Note Name: " + noteName);
            System.out.println("Frequency: " + frequency);
            dispose();
            parentFrame.setEnabled(true); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Frequency must be a valid number and cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(buttonPanel, BorderLayout.SOUTH);
    }
}