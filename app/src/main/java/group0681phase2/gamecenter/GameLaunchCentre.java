package group0681phase2.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The game launch centre.
 */
public class GameLaunchCentre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch_centre);
        addPlayAsGuestButton();
        addLogInSignUpButton();
    }

    /**
     * Activate the play as guest button.
     */
    private void addPlayAsGuestButton() {
        Button playAsGuestButton = findViewById(R.id.PlayAsGuestButton);
        playAsGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToStarting();
            }
        });
    }

    /**
     * Activate the LogInSignUp button.
     */
    private void addLogInSignUpButton() {
        Button logInSignUpButton = findViewById(R.id.LogInSignUpButton);
        logInSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }

    /**
     * Switch to the StartingActivity view to start the game.
     */
    private void switchToStarting() {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the Login view to sign up.
     */
    private void switchToLogin() {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }
}
