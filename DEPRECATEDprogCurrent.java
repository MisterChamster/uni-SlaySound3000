import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
//incorporate sample size. PRIORITY


abstract class Sound extends Thread{
    String soundName;
    float sampleRate;
    double durationInSeconds;
    byte[] sampleArray;
    AudioFormat format;
    SourceDataLine line;

    Sound(){
        this.soundName = "";
        this.sampleRate = 0;
        this.durationInSeconds = 0;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    void setSampleRate(float input){
        this.sampleRate = input;
    }

    void setDurationInSec(double input){
        this.durationInSeconds = input;
    }

    void setNamee(String input){
        this.soundName = input;
    }

    byte[] getSampleArray(){
        return this.sampleArray;
    }

    void prepareToPlay(){}
    void playSound(){}
    void stopSound(){}
}

class SoundNote extends Sound{
    String soundName;
    float sampleRate;
    double durationInSeconds;
    double frequency;
    byte[] sampleArray;
    AudioFormat format;
    SourceDataLine line;

    SoundNote(double durationInSec, double frequency){
        this.soundName = "";
        this.sampleRate = 44100;
        this.durationInSeconds = durationInSec;
        this.frequency = frequency;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    @Override
    void setSampleRate(float input){
        this.sampleRate = input;
    }

    @Override
    void setDurationInSec(double input){
        this.durationInSeconds = input;
    }

    void setFrequency(double input){
        this.frequency = input;
    }

    @Override
    void setNamee(String input){
        this.soundName = input;
    }

    @Override
    byte[] getSampleArray(){
        return this.sampleArray;
    }

    void computeSampleArray(){
        double twoPIFreq = 2.0 * Math.PI * frequency;
        sampleArray = new byte[(int) Math.ceil(sampleRate * durationInSeconds)];

        for (int i = 0; i < sampleArray.length; i++) {
            double angle = twoPIFreq * i / sampleRate;
            sampleArray[i] = (byte) (Math.sin(angle) * 127); //scale to byte range
        }
    }

    @Override
    void prepareToPlay(){
        computeSampleArray();
        format = new AudioFormat(sampleRate, 8, 1, true, true); //8 bits sample size
        try {line = AudioSystem.getSourceDataLine(format);}
        catch (LineUnavailableException e) {System.exit(0);}
    }

    @Override
    void playSound(){
        try {line.open(format);}
        catch (LineUnavailableException e) {System.exit(0);}
        line.start();
        //here it starts playing
        line.write(sampleArray, 0, sampleArray.length);

        //cleanup
        line.drain();
        line.close();
    }

    @Override
    void stopSound(){
        line.drain();
        line.close();
    }

    public void run(){
        this.playSound();
    }
}

class SoundChord extends Sound{
    String soundName;
    SoundNote[] noteArray;
    int noteArrayCLen;
    float sampleRate;
    double durationInSeconds;
    byte[] sampleArray;
    AudioFormat format;
    SourceDataLine line;

    SoundChord(){
        this.soundName = "";
        this.noteArray = new SoundNote[20];  //max note size of a chord chosen arbitrarily
        this.noteArrayCLen = 0;
        this.sampleRate = 0;
        this.durationInSeconds = 0;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    @Override
    void setSampleRate(float input){
        this.sampleRate = input;
    }

    @Override
    void setDurationInSec(double input){
        this.durationInSeconds = input;
    }

    @Override
    void setNamee(String input){
        this.soundName = input;
    }

    @Override
    byte[] getSampleArray(){
        return this.sampleArray;
    }

    void addNote(SoundNote note){
        if (noteArrayCLen >= noteArray.length - 1){
            //Resizes the array if the user is stupid enough to use more than 20 notes in a chord
            SoundNote[] resizeArray = new SoundNote[noteArray.length + 5];
            for (int i=0; i<noteArray.length; i++){
                resizeArray[i] = noteArray[i];
            }
            noteArray = resizeArray;
        }
        noteArray[noteArrayCLen] = note;
        noteArrayCLen++;
    }

    void delNote(int index){
        noteArrayCLen--;
        for (int i=index+1; i<=noteArrayCLen; i++){
            noteArray[i-1] = noteArray[i];
        }
        noteArray[noteArrayCLen] = null;
    }

    //debug function
    void printNoteArray(){
        for(int i=0; i<noteArrayCLen; i++){
            System.out.println((i+1) + ". " + noteArray[i].frequency);
        }
    }

    void computeSampleArray(){

    }

}

class SoundMix extends Sound{

}

class WavFileSaver {
    //After using this stupid function for some reason You have to use
    //prepareToPlay on the thing from which You took the sampleArray.
    //Please don't ask why.
    static void saveWavFile(byte[] sampleArray, float sampleRate, int sampleSizeInBits) {
        int waveOffset = (int)Math.pow(2, sampleSizeInBits-1);
        for (int i=0; i<sampleArray.length; i++) {
            //This exact line is the problem. If it exists and You try to play
            //the sound it plays distorted version of it. Normally this program
            //(on the contrary to normal audio players) plays samples from -128
            //to 127, but after this damn line it plays from 0 to 255 I persume.
            //I don't think anything changes the range - when I play other notes
            //that haven't been saved it's all good. But after this function,
            //without using prepareToPlay again, it plays that note in different
            //sample range. I've given up trying to understand this issue, sry.
            sampleArray[i] = (byte) ((int)sampleArray[i] + waveOffset);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save WAV File");

        // Show the save dialog and get the selected file
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure the file has a .wav extension
            if (!fileToSave.getName().toLowerCase().endsWith(".wav")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".wav");
            }

            try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                // Write the WAV file header
                writeWavHeader(fos, sampleArray.length, sampleRate, sampleSizeInBits);

                // Write the audio data
                fos.write(sampleArray);

                System.out.println("WAV file saved successfully: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeWavHeader(FileOutputStream fos, int sampleArrayLength, float sampleRate, int sampleSizeInBits) throws IOException {
        int channels = 1;                                               // Mono audio
        int byteRate = (int) (sampleRate * channels * sampleSizeInBits / 8);
        int blockAlign = channels * sampleSizeInBits / 8;
        int subChunk2Size = sampleArrayLength;
        int chunkSize = 36 + subChunk2Size;

        fos.write("RIFF".getBytes());                                    // Chunk ID
        fos.write(intToLittleEndian(chunkSize, 4));             // Chunk size
        fos.write("WAVE".getBytes());                                    // Format
        fos.write("fmt ".getBytes());                                    // Subchunk1 ID
        fos.write(intToLittleEndian(16, 4));              // Subchunk1 size (16 for PCM)
        fos.write(intToLittleEndian(1, 2));               // Audio format (1 for PCM)
        fos.write(intToLittleEndian(channels, 2));              // Number of channels
        fos.write(intToLittleEndian((int) sampleRate, 4));      // Sample rate
        fos.write(intToLittleEndian(byteRate, 4));              // Byte rate
        fos.write(intToLittleEndian(blockAlign, 2));            // Block align
        fos.write(intToLittleEndian(sampleSizeInBits, 2));      // Bits per sample
        fos.write("data".getBytes());                                    // Subchunk2 ID
        fos.write(intToLittleEndian(subChunk2Size, 4));         // Subchunk2 size
    }

    private static byte[] intToLittleEndian(int value, int byteSize) {
        byte[] littleEndian = new byte[byteSize];
        for (int i = 0; i < byteSize; i++) {
            littleEndian[i] = (byte) (value >> (i * 8));
        }
        return littleEndian;
    }
}

public class progCurrent{
    public static void main(String[] args){
        SoundNote note1 = new SoundNote(0.5, 440);
        SoundNote note2 = new SoundNote(0.5, 659.25);
        SoundNote note3 = new SoundNote(0.5, 880);
        note1.prepareToPlay();
        note2.prepareToPlay();
        note3.prepareToPlay();

        SoundChord chord1 = new SoundChord();
        for(int i=0; i<13; i++){
            chord1.addNote(note3);
        }
        chord1.addNote(note2);
        for(int i=0; i<13; i++){
            chord1.addNote(note3);
        }
        chord1.printNoteArray();
        System.out.println("HDSAIKFDSAKFSJNAKF");
        chord1.delNote(13);
        chord1.printNoteArray();

        // WavFileSaver.saveWavFile(note1.sampleArray, 44100, 8);

        // note1.prepareToPlay();
        // note1.start();
        // note2.start();
        // note3.start();
    }
}
