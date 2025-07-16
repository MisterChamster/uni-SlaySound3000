import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;

public class WavFileSaver {
    static void saveWavFile(Sound soundChild) {
        int waveOffset = (int) Math.pow(2, soundChild.sampleSize-1);
        for (int i=0; i<soundChild.sampleArray.length; i++) {
            soundChild.sampleArray[i] = (byte) ((int)soundChild.sampleArray[i] + waveOffset);
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
                writeWavHeader(fos, soundChild.sampleArray.length, soundChild.sampleRate, soundChild.sampleSize);

                // Write the audio data
                fos.write(soundChild.sampleArray);

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
        fos.write(intToLittleEndian(sampleSize, 2));            // Bits per sample
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