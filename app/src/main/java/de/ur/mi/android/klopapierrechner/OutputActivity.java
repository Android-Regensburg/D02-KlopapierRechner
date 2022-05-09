package de.ur.mi.android.klopapierrechner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.ur.mi.android.klopapierrechner.data.LooRollCalculator;

/**
 * Diese Activity dient der Ausgabe der berechneten Informationen zum Klopapiervorrat
 */

public class OutputActivity extends AppCompatActivity {

    // Öffentliche Konstanten für die Extras, die beim Aufruf dieser Activity übergeben werden
    public static final String ROLLS_KEY = "NUMBER OF ROLLS";
    public static final String PEOPLE_KEY = "NUMBER OF PEOPLE";

    // Instanzvariable für Referenz auf TextView zum Anzeigen der Infonachricht
    private TextView resultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initResultText();
    }

    private void initUI() {
        setContentView(R.layout.activity_output);
        resultView = findViewById(R.id.text_results_value);
    }

    /**
     * Die Methode benutzt die per Intent aus der vorherigen Activity erhaltenen Werte um, mit Hilfe
     * einer separten LooRollCalculator-Klasse einen Informationstext für die NutzerInnen zu
     * erzeugen und im UI anzuzeigen.
     */
    private void initResultText() {
        // Erzeugen einer Instanz der Hilfsklasse für die Vorratsberechnung
        LooRollCalculator calculator = new LooRollCalculator();
        // Auslesen der übergebenen Extras aus dem Intent
        Bundle extras = getIntent().getExtras();
        // Auslesen der übergebenen Anzahl an vorhandenen Klopapierrollen
        int numberOfRolls = extras.getInt(ROLLS_KEY);
        // Auslesen der übergebenen Anzahl an Personen im Haushalt
        int numberOfPeople = extras.getInt(PEOPLE_KEY);
        // Berechnen der geschätzten Werte mit Hilfe des LooRollCalculator
        calculator.setNumberOfRolls(numberOfRolls);
        calculator.setNumberOfPeople(numberOfPeople);
        int daysUntilSupplyDepleted = calculator.getSupplyInDays();
        String dayForNextShoppingTrip = calculator.getDayForNextShoppingTrip();
        // Erzeugen und Anzeigen der Infonachricht im UI
        resultView.setText(createResultMessage(numberOfRolls, numberOfPeople, daysUntilSupplyDepleted, dayForNextShoppingTrip));
    }

    /**
     * Die Methode baut aus den übergebenen Werten und der in strings.xml gespeicherten Vorlage den
     * Informationstext zusammen, der im UI angezeigt werden soll.
     *
     * @param numberOfRolls Anzahl der vorrätigen Rollen
     * @param numberOfPeople Anzahl der Personen im Haushalt
     * @param daysUntilSuplyDepleted Geschätzte Anzahl der Tage, bis Vorrat aufgebraucht ist
     * @param dayForNextShoppingTrip Geschätztes Datum, an dem neues Klopaier gekauft werden sollte
     * @return Zusammengebauter Informationstext
     */
    private String createResultMessage(int numberOfRolls, int numberOfPeople, int daysUntilSuplyDepleted, String dayForNextShoppingTrip) {
        String msg = getResources().getString(R.string.text_result_template);
        msg = msg.replace("$ROLLS", String.valueOf(numberOfRolls));
        msg = msg.replace("$PEOPLE", String.valueOf(numberOfPeople));
        msg = msg.replace("$DAYS", String.valueOf(daysUntilSuplyDepleted));
        msg = msg.replace("$SHOPPING_DAY", dayForNextShoppingTrip);
        return msg;
    }

}
