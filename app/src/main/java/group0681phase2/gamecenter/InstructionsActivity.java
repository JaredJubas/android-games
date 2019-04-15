package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The instructions for Simon.
 */
public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        addSimonPlayButtonListener();
    }

    /**
     * Activate the Simon button.
     */
    private void addSimonPlayButtonListener() {
        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSimon();
            }
        });
    }

    /**
     * Switch to Simon game.
     */
    private void switchToSimon() {
        Intent tmp = new Intent(this, ComplexityActivity.class);
        startActivity(tmp);
    }
}
