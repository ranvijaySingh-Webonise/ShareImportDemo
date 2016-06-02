package webonise.shareimportdemo;

import android.app.Activity;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

    private static final String DIRECTORY_NAME = "/share-import-demo/";
    private static final String TAG = "FileUtil";
    private final Activity mActivity;
    private String fileName = "sample.txt";

    public FileUtil(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Function to write content to a file
     *
     * @param content String
     * @return String
     */
    public String writeToFile(String content) {
        String filePath = null;
        if (isExternalStorageAvailable()) {
            File file = getFile();
            filePath = file.getAbsolutePath();
            Toast.makeText(mActivity, filePath, Toast.LENGTH_SHORT).show();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();
                Toast.makeText(mActivity, "Message saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, "External storage is not available",
                    Toast.LENGTH_SHORT).show();
        }
        return filePath;
    }

    @NonNull
    private File getFile() {
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File directory = new File(root.getAbsolutePath() + DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
            Toast.makeText(mActivity, "Created new directory",
                    Toast.LENGTH_SHORT).show();
        }
        return new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DCIM).getAbsolutePath() + DIRECTORY_NAME + fileName);
    }

    /**
     * Function is called when user click on read button
     */
    public String readFromFile() {
        File file = getFile();
        String message = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message = bufferedReader.readLine()) != null) {
                stringBuffer.append(message + "\n");
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Checks if external storage is available for read and write
     *
     * @return boolean
     */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
