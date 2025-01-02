import javax.swing.*;
import java.io.*;

public class WavFileSaver {
    //After using this stupid function for some reason You have to use
    //prepareToPlay on the thing from which You took the sampleArray.
    //Please don't ask why.
    static void saveWavFile(byte[] sampleArray, float sampleRate, int sampleSize) {
        int waveOffset = (int)Math.pow(2, sampleSize-1);
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
                writeWavHeader(fos, sampleArray.length, sampleRate, sampleSize);

                // Write the audio data
                fos.write(sampleArray);

                System.out.println("WAV file saved successfully: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeWavHeader(FileOutputStream fos, int sampleArrayLength, float sampleRate, int sampleSize) throws IOException {
        int channels = 1;                                               // Mono audio
        int byteRate = (int) (sampleRate * channels * sampleSize / 8);
        int blockAlign = channels * sampleSize / 8;
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
        fos.write(intToLittleEndian(sampleSize, 2));      // Bits per sample
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