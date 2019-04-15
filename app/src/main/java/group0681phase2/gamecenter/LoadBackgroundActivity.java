package group0681phase2.gamecenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The activity to load the background.
 */
public class LoadBackgroundActivity extends AppCompatActivity {


    /**
     * Activity Request Code for selecting image.
     */
    private static int RESULT_LOAD_IMAGE = 1;

    /**
     * The username of the user logged into the game.
     * For Guest, username is empty.
     */
    private String username;


    /**
     * Indicates if the default background for the game should be used.
     */
    private boolean useDefaultBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_background);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");
        this.useDefaultBackground = intent.getBooleanExtra("useDefault", true);

        if (!this.useDefaultBackground) {
            try {
                Bitmap bitmap = loadImageFromFile(getBackgroundFileName());
                ImageView imageView = findViewById(R.id.CustomImageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException ex) {
                this.useDefaultBackground = true;
            }
        }
        updateDisplay();

        addLoadImageButtonListener();
        addDefaultImageButtonListener();

    }

    @Override
    public void onBackPressed() {
        if (this.useDefaultBackground) {
            this.setResult(RESULT_FIRST_USER);
        } else {
            this.setResult(RESULT_FIRST_USER + 1);
        }
        super.onBackPressed();

    }

    /**
     * Activate the Load Image button.
     */
    private void addLoadImageButtonListener() {
        Button backgroundButton = findViewById(R.id.LoadImageButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
    }

    /**
     * Activate the Default Image button.
     */
    private void addDefaultImageButtonListener() {
        Button backgroundButton = findViewById(R.id.DefaultImageButton);
        backgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useDefaultBackground(true);
            }
        });
    }

    /**
     * Set the default background option and update the display
     *
     * @param useDefault the default background
     */
    private void useDefaultBackground(boolean useDefault) {
        this.useDefaultBackground = useDefault;
        updateDisplay();
    }

    /**
     * Update the display
     */
    private void updateDisplay() {
        updateDisplay("");
    }

    /**
     * Update the display with the message
     *
     * @param message the display message
     */
    private void updateDisplay(String message) {
        TextView textMessageBox = findViewById(R.id.LoadBackgroundText);
        ImageView imageView = findViewById(R.id.CustomImageView);

        String displayMessage = "";

        if (message != null && message.length() > 0) {
            displayMessage = message + "\n";
        }

        if (this.useDefaultBackground) {
            displayMessage = displayMessage + "Using default image";
            imageView.setVisibility(View.INVISIBLE);
        } else {
            displayMessage = displayMessage + "Using custom image";
            imageView.setVisibility(View.VISIBLE);
        }

        textMessageBox.setText(displayMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
                Bitmap image = getImageFromFileUri(selectedImage);
                if (image != null) {
                    String fileName = getBackgroundFileName();
                    saveImageToFile(image, fileName);
                    ImageView imageView = findViewById(R.id.CustomImageView);
                    imageView.setImageBitmap(image);
                    useDefaultBackground(false);
                } else {
                    handleImageError();
                }

            } catch (IOException ex) {
                handleImageError();

            }

            finishActivity(RESULT_LOAD_IMAGE);

        }

    }

    /**
     * Set the file name for the background
     *
     * @return the file name
     */
    private String getBackgroundFileName() {
        return this.username + "_background.png";
    }

    /**
     * Switch to the default image and display a message stating that the image was
     * unable to load and is being switched to the default image
     */
    private void handleImageError() {
        this.useDefaultBackground = true;
        updateDisplay("Unable to load image ! Using default image.");
    }

    /**
     * Get the selected image
     *
     * @param selectedImage the selected image
     * @return the custom image
     * @throws IOException if selected imageis not retrieved
     */
    private Bitmap getImageFromFileUri(Uri selectedImage) throws IOException {
        Bitmap customImage = null;

        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(selectedImage, "r");
        if (parcelFileDescriptor != null) {
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            customImage = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
        }

        return customImage;
    }

    /**
     * Save the image to the file
     *
     * @param image    the image
     * @param fileName the name of the file to be saved to
     * @throws IOException if there is no save
     */
    private void saveImageToFile(Bitmap image, String fileName) throws IOException {
        FileOutputStream out = openFileOutput(fileName, MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
    }

    /**
     * Load the image from the file
     *
     * @param fileName the name of the file
     * @return the image loaded from the file
     * @throws IOException if the image can not be loaded
     */
    private Bitmap loadImageFromFile(String fileName)
            throws IOException {
        Bitmap loadedImage;
        FileInputStream input = openFileInput(fileName);
        loadedImage = BitmapFactory.decodeStream(input);
        return loadedImage;
    }

}