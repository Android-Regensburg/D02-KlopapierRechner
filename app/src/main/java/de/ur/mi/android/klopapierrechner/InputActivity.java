package de.ur.mi.android.klopapierrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    private static final int DEFAULT_NUMBER_OF_ROLLS = 8;
    private static final int DEFAULT_NUMBER_OF_PEOPLE = 2;
    private TextView currentRollCount;
    private TextView currentNumberOfPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_input);
        currentRollCount = findViewById(R.id.rollValue);
        currentNumberOfPeople = findViewById(R.id.personValue);
        SeekBar rollCountSeeker = findViewById(R.id.rollSlider);
        SeekBar peopleCountSeeker = findViewById(R.id.personSlider);
        rollCountSeeker.setProgress(DEFAULT_NUMBER_OF_ROLLS);
        currentRollCount.setText(String.valueOf(DEFAULT_NUMBER_OF_ROLLS));
        peopleCountSeeker.setProgress(DEFAULT_NUMBER_OF_PEOPLE);
        currentNumberOfPeople.setText(String.valueOf(DEFAULT_NUMBER_OF_PEOPLE));
        rollCountSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onRollCountChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        peopleCountSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onPeopleCountChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void onRollCountChanged(int value) {
        currentRollCount.setText(String.valueOf(value));
    }

    private void onPeopleCountChanged(int value) {
        currentNumberOfPeople.setText(String.valueOf(value));
    }
}
