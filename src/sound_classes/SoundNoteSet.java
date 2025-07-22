package sound_classes;



public class SoundNoteSet extends Sound {
    // ======================= Fields =======================
    public SoundNote[] noteArray;


    // ===================== Constructors =====================
    public SoundNoteSet() {
        this.noteArray = new SoundNote[0];
    }

    // Constructor for adding note sets
    public SoundNoteSet(String soundName) {
        super();
        this.noteArray = new SoundNote[0];
    }

    public SoundNoteSet(float sampleRate, int sampleSize, double durationInSeconds, SoundNote[] noteArray, String soundName) {
        super(sampleRate, sampleSize, durationInSeconds, soundName);
        this.noteArray = noteArray;
    }

    // Testing constructor
    public SoundNoteSet(float sampleRate, int sampleSize, double durationInSeconds, String soundName) {
        super(sampleRate, sampleSize, durationInSeconds, soundName);
        this.noteArray = new SoundNote[0];
    }


    // ======================= Methods =======================
    // This class needs just note frequency and name,
    // the rest can be erased with that function
    private void stripNote(SoundNote note) {
        note.sampleRate = 0;
        note.sampleSize = 0;
        note.durationInSeconds = 0;
        note.sampleArray = null;
        note.format = null;
        note.line = null;
    }

    public boolean isNoteInNoteArray(SoundNote note) {
        for (int i = 0; i < noteArray.length - 1; i++) {
            if (noteArray[i].frequency == note.frequency) {
                return true;
            }
        }
        return false;
    }

    public void addNote(SoundNote inputNote) {
        // cant have two same freq notes
        if (!isNoteInNoteArray(inputNote)) {
            stripNote(inputNote);
            SoundNote[] tempArray = new SoundNote[noteArray.length + 1];
            for (int i = 0; i < noteArray.length; i++) {
                tempArray[i] = noteArray[i];
            }
            tempArray[tempArray.length - 1] = inputNote;
            noteArray = tempArray;
        }
    }

    public void delNote(int index) {
        SoundNote[] tempArray = new SoundNote[noteArray.length - 1];
        for (int i = 0; i < index; i++) {
            tempArray[i] = noteArray[i];
        }
        for (int i = index + 1; i < noteArray.length; i++) {
            tempArray[i - 1] = noteArray[i];
        }
        noteArray = tempArray;
    }

    public void computeSampleArray() {
        int sampleArrayLength = (int) (Math.ceil(sampleRate * durationInSeconds) * (sampleSize / 8));
        sampleArray = new byte[sampleArrayLength];

        for (int i = 0; i < noteArray.length; i++) {
            noteArray[i].setSampleRate(this.sampleRate);
            noteArray[i].setSampleSize(this.sampleSize);
            noteArray[i].setDurationInSec(this.durationInSeconds);
            noteArray[i].computeSampleArray();
        }

        int temp = 0;
        // for all values in byte array...
        for (int i = 0; i < sampleArrayLength; i++) {
            // for all notes in note array...
            for (int j = 0; j < noteArray.length; j++) {
                temp += (int) noteArray[j].sampleArray[i];
            }
            sampleArray[i] = (byte) ((int) (temp / noteArray.length));
            temp = 0;
        }

        // Notes in noteArray no longer need to take so much space
        for (int i = 0; i < noteArray.length; i++) {
            stripNote(noteArray[i]);
        }
    }

    @Override
    public void prepareToPlay() {
        computeSampleArray();
        super.prepareToPlay();
    }

    @Override
    public void playSound() {
        // condition not necessary in final version
        if (noteArray.length < 2) {
            System.out.println("Noteset can be played if it has 2 or more different notes");
            System.exit(0);
        }
        super.playSound();
    }

    public void run() {
        this.playSound();
    }

    // debug function
    public void printNoteArray() {
        for (int i = 0; i < noteArray.length; i++) {
            System.out.println((i + 1) + ". " + noteArray[i].frequency);
        }
    }
}
