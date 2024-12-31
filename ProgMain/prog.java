//incorporate sample size. PRIORITY
//work on constructors

public class prog{
    public static void main(String[] args){
        SoundNote note1 = new SoundNote(2, 440);
        SoundNote note2 = new SoundNote(2, 659.25);
        SoundNote note3 = new SoundNote(2, 880);
        note1.prepareToPlay();
        note2.prepareToPlay();
        note3.prepareToPlay();

        SoundChord chord1 = new SoundChord();
        chord1.setDurationInSec(2);
        chord1.setSampleRate(44100);
        chord1.addNote(note3);
        chord1.addNote(note3);
        chord1.addNote(note3);
        chord1.addNote(note2);
        chord1.addNote(note2);
        chord1.addNote(note1);
        // chord1.printNoteArray();
        // chord1.delNote(13);
        chord1.prepareToPlay();

        // WavFileSaver.saveWavFile(note1.sampleArray, 44100, 8);
        WavFileSaver.saveWavFile(chord1.sampleArray, 44100, 8);
        
        chord1.prepareToPlay();
        chord1.start();
        chord1.stopSound();

        // note1.prepareToPlay();
        // note1.start();
        // note2.start();
        // note3.start();
    }
}