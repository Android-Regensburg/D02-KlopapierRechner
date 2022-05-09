package de.ur.mi.android.klopapierrechner.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Diese Klasse erlaubt die einfache Berechnung des Klopapierrollenvorrats
 */
public class LooRollCalculator {

    // Angenommene Anzahl der Blätter pro Rolle
    private static final int SHEETS_PER_ROLL = 150;
    // Angenommener Vebrauch pro Klogang in Blätter
    private static final int SHEETS_PER_SITTING = 10;
    // Angenommene Anzahl der Klogänge pro Person
    private static final int LOO_FREQUENCY_PER_DAY_AND_PERSON = 3;
    // Sicherheitsabstand zwischen Einkaufsempfehlung und Vorratsende in Tagen
    private static final int SAFETY_BUFFER_IN_DAYS = 3;

    // Instanzvariable für tatsächliche Anzahl an Rollen
    private int numberOfRolls = LooRollCalculatorConfig.DEFAULT_NUMBER_OF_ROLLS;
    // Instanzvariable für tatsächliche Anzahl an Personen im Haushalt
    private int numberOfPeople = LooRollCalculatorConfig.DEFAULT_NUMBER_OF_PEOPLE;

    // Erlaubt das Eintragen der tatsächlichen Anzahl verfügbarer Rollen
    public void setNumberOfRolls(int rolls) {
        this.numberOfRolls = rolls;
    }

    // Erlaubt das Eintragen der tatsächlichen Anzahl von Personen im Haushalt
    public void setNumberOfPeople(int people) {
        this.numberOfPeople = people;
    }

    // Gibt zurück, wie viele Tage der Vorrat auf Basis der übergebenen Werte noch reicht
    public int getSupplyInDays() {
        // Berechnung: Vorhandene Blätter / Blätterverbrauch pro Tag
        return (numberOfRolls * SHEETS_PER_ROLL) / (numberOfPeople * LOO_FREQUENCY_PER_DAY_AND_PERSON * SHEETS_PER_SITTING);
    }

    // Gibt eine Empfehlung zurück, an welchem Tag (String-formatiert) neues Klopapier
    // gekauft werden sollte
    public String getDayForNextShoppingTrip() {
        // Wenn der Vorrat bereits nicht mehr für den Sicherheitsabstand in Tagen reicht, wird
        // eine sofortige Kaufemnpfehlung ausgesprochen,
        int dayUntilNextShoppingTrip = getSupplyInDays() - SAFETY_BUFFER_IN_DAYS;
        if (dayUntilNextShoppingTrip < 0) {
            dayUntilNextShoppingTrip = 0;
        }
        // Berechnung und Formatierung des empfohlenen Kaufdatums
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM");
        LocalDate shoppingDate = LocalDate.parse(LocalDate.now().toString()).plusDays(dayUntilNextShoppingTrip);
        return shoppingDate.format(formatter);
    }

}
