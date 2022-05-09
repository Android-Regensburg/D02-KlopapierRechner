package de.ur.mi.android.klopapierrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import de.ur.mi.android.klopapierrechner.data.LooRollCalculatorConfig;

/**
 * Mit dieser Anwendung haben NutzerInnen die Möglichkeit, ihren persönlichen Klopapierrollenvorrat
 * einzuschätzen. Dazu werden Anzahl der verfügbaren Rollen und die Größe des Haushalts eingegeben.
 * Die Anwendung berechnet dann, ie wie viele Tage dieser Vorrat noch reichen wird und wann ein
 * guter Zeitpunkt zum Einkaufen wären. Die Hinweise werden in einer zweiten Activity angezeigt.
 */

/**
 * Diese Activity dient der Eingabe der notwendigen Daten durch die NutzerInnen
 */
public class InputActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    /**
     * Instanzvariablen für die relevanten UI-Elemente zum Auslesen de ausgewählten Bestände an
     * Rollen bzw. Personen im Haushalt. Der Inhalt der TextViews wird bei jeder Änderung der
     * SeekBars angepasst und vor dem Wechsel in die nächste Activity aus den TextViews ausgelesen.
     */
    private TextView currentRollCount;
    private TextView currentNumberOfPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_input);
        // Erstellen der Referenzen auf die UI-Elemente aus der Layout-Datei
        currentRollCount = findViewById(R.id.text_rolls_value);
        currentNumberOfPeople = findViewById(R.id.text_persons_value);
        // Die Initalisierung der SeekBars ist etwas komplexer und wird daher in eine separate
        // Methode ausgelagert, die wiederverwendbar für beide Elemente gestaltet ist.
        initSeekBar(R.id.seeker_rolls_value, LooRollCalculatorConfig.MIN_NUMBER_OF_ROLLS, LooRollCalculatorConfig.MAX_NUMBER_OF_ROLLS, LooRollCalculatorConfig.DEFAULT_NUMBER_OF_ROLLS, this);
        initSeekBar(R.id.seeker_persons_value, LooRollCalculatorConfig.MIN_NUMBER_OF_PEOPLE, LooRollCalculatorConfig.MAX_NUMBER_OF_PEOPLE, LooRollCalculatorConfig.DEFAULT_NUMBER_OF_PEOPLE, this);
        //
        Button resultButton = findViewById(R.id.resultButton);
        resultButton.setOnClickListener(v -> onResultButtonClicked());
    }

    /**
     * Initialisiert eine beliebige SeekBar, die über die übergebene ID identifiziert wird, mit
     * einem Minimalwert, einem Maximalwert, einem aktuellen Wert und einem Listener, der über
     * Änderungen (ausgelöst durch die Verwendung der Bar durch NutzerInnen) informiert wird.
     */
    private void initSeekBar(int id, int min, int max, int value, SeekBar.OnSeekBarChangeListener listener) {
        SeekBar seekBar = findViewById(id);
        // Setzen eines Listener, der über Änderungen an der SeekBar informiert werden soll
        seekBar.setOnSeekBarChangeListener(this);
        // Setzen des minimalen Wert, der mit der SeekBar ausgewählt werden kann
        seekBar.setMin(min);
        // Setzen des maximalen Wert, der mit der SeekBar ausgewählt werden kann
        seekBar.setMax(max);
        // Setzen des aktuellen Wert, der in der Seekbar angzeigt wird
        // Hier wird bereits das erste Mal die Callback-Methode des oben gesetzten Listeners
        // aufgerufen und damit auch der Wert des entsprechenden TextViews (Vgl. onRollCountChanged
        // bzw. onPeopleCountChanged) geändert.
        seekBar.setProgress(value);
    }

    /**
     * Wird aus der Callback-Methode des SeekBar-Listeners aufgerufen, um den aktuellen Wert der
     * SeekBar zur Auswahl der Anzahl der Rollen in den entsprechenden TextView zu übertragen.
     */
    private void onRollCountChanged(int value) {
        currentRollCount.setText(String.valueOf(value));
    }

    /**
     * Wird aus der Callback-Methode des SeekBar-Listeners aufgerufen, um den aktuellen Wert der
     * SeekBar zur Auswahl der Anzahl der Personen in den entsprechenden TextView zu übertragen.
     */
    private void onPeopleCountChanged(int value) {
        currentNumberOfPeople.setText(String.valueOf(value));
    }

    /**
     * Wird aus der Callback-Methode des Button-Listeners aufgerufen und startet die zweite Activity
     * mit den aktuellen Werten aus den SeekBars bzw. TextViews.
     */
    private void onResultButtonClicked() {
        // Auslesen der aktuell ausgewählten Anzahl an Klopapierrollen
        int numberOfRolls = Integer.parseInt(currentRollCount.getText().toString());
        // Auslesen der aktuell ausgewählten Anzahl an Personen im Haushalt
        int numberOfPeople = Integer.parseInt(currentNumberOfPeople.getText().toString());
        // Erstellen des Intents zum Wechsel von dieser in die nächste Activit
        Intent intent = new Intent(getBaseContext(), OutputActivity.class);
        // Hinzufügen der ausgewählten Werte zum Intent, damit diese in der nächsten Activity
        // genutzt werden können
        intent.putExtra(OutputActivity.ROLLS_KEY, numberOfRolls);
        intent.putExtra(OutputActivity.PEOPLE_KEY, numberOfPeople);
        // Starten der zweiten Activity
        startActivity(intent);

    }

    /**
     * Callback-Methode, über die diese Activity über Änderungen an den SeekBars informiert wird.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // Beide SeekBars "teilen" sich eine Callback-Methode, d.h. in beiden Fällen landen wir an
        // dieser Stelle. Über die (eindeutige) ID der SeekBar, auf der der Event ausgelöst wurde,
        // können wir unterscheiden, welcher Wert geändert wurde und die entsprechende Methode zum
        // Anpassen des richtigen TextViews aufrufen. Der neue Wert der SeekBar, also der Wert, der
        // von den NutzerInnen ausgewählt wurde, wird als "progress" an die Callback-Methode
        // übergeben.
        switch (seekBar.getId()) {
            case R.id.seeker_rolls_value:
                onRollCountChanged(progress);
                break;
            case R.id.seeker_persons_value:
                onPeopleCountChanged(progress);
                break;
            default:
                break;
        }
    }

    /**
     * Ungenutzte, zusätzliche Callback-Methode aus dem OnSeekBarChangeListener-Interface, über die
     * der Listener über den Zeitpunkt informiert wird, an dem die NutzerInnen mit der Verwendung
     * der SeekBar beginnen. Uns interessiert nur die endgültige Änderung (onProgressChanged) und
     * wir ignorieren diesen Event daher.
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Ungenutzte, zusätzliche Callback-Methode aus dem OnSeekBarChangeListener-Interface, über die
     * der Listener über den Zeitpunkt informiert wird, an dem die NutzerInnen mit der Verwendung
     * der SeekBar aufhören. Uns interessiert nur die endgültige Änderung (onProgressChanged) und
     * wir ignorieren diesen Event daher.
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}