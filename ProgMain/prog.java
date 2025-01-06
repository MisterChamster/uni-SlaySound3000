//two different freq notes can't be named the same
//can create custom notes if note of the same freq or name doesn't exist

//Noteset must have at least two different notes

//every note must have name and freq

public class prog{
    public static void main(String[] args){
        SoundNote note1 = new SoundNote(44100, 16, 2, 440, "A4");
        SoundNote note2 = new SoundNote(44100, 16, 2, 659.25f, "E5");
        SoundNote note3 = new SoundNote(44100, 16, 2, 880, "A5");
        note1.prepareToPlay();
        note2.prepareToPlay();
        note3.prepareToPlay();

        SoundNoteSet chord1 = new SoundNoteSet(44100, 16, 2, "TrialChord");
        chord1.addNote(note1);
        chord1.addNote(note2);
        chord1.addNote(note3);
        chord1.prepareToPlay();
        // WavFileSaver.saveWavFile(chord1);
        // chord1.start();


        UserInterface ui = new UserInterface();
        ui.setVisible(true);



        // WavFileSaver.saveWavFile(note1);
        // note1.start();
    }
}