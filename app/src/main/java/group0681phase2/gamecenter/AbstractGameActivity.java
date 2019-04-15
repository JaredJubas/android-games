package group0681phase2.gamecenter;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class AbstractGameActivity extends AppCompatActivity {


    /**
     * The main save file.
     */
    protected static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    protected static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";


    /**
     * The board manager.
     */
    protected AbstractBoardManager boardManager;

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     * Get the name of the save file for the current user.
     *
     * @param currentUserName the name of the current user
     * @return the save file name
     */
    public String getSaveFileName(String currentUserName, String gameName) {
        String result = gameName + "_" + SAVE_FILENAME;
        if (currentUserName != null) {
            result = currentUserName + "_" + result;
        }
        return result;
    }

    /**
     * Get the name of the temporary save file for the current user.
     *
     * @param currentUserName the name of the current user
     * @return the temporary save file name
     */
    public String getTempFileName(String currentUserName, String gameName) {
        String result = gameName + "_" + TEMP_SAVE_FILENAME;
        if (currentUserName != null) {
            result = currentUserName + "_" + result;
        }
        return result;
    }

}
