import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.*;

public class CreateNote extends JFrame {
    // UI variables
    JTextField noteNameField;
    JTextField frequencyField;
    JFrame parentFrame;
    JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton discardButton = new JButton("Discard");
    JButton createButton = new JButton("Create");

    // Sound variables
    SoundNote createdNote = new SoundNote(0, " ");

    public CreateNote(JFrame parentFrame) {
        super("Create Note");
        this.parentFrame = parentFrame;
        initialize();
        addListeners();
    }

    private void initialize(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        inputPanel.add(new JLabel("Note name:"));
        noteNameField = new JTextField();
        inputPanel.add(noteNameField);

        inputPanel.add(new JLabel("Frequency (Hz):"));
        frequencyField = new JTextField();
        inputPanel.add(frequencyField);

        add(inputPanel, BorderLayout.CENTER);

        buttonPanel.add(discardButton);
        buttonPanel.add(createButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parentFrame.setEnabled(true);
            }
        });

        discardButton.addActionListener(e -> {
            dispose(); 
            parentFrame.setEnabled(true); 
        });

        createButton.addActionListener(e -> {
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
                    System.out.println("Note Name: " + noteName);
                    System.out.println("Frequency: " + frequency);
                    dispose();
                    parentFrame.setEnabled(true);
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
        });
    }

    
}
