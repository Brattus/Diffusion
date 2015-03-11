import processing.core.*;

/**
 * Created by Gøran on 11.03.2015.
 */
public class CSV extends PApplet {

    processing.data.Table table;

    public void setup(){

        table = new processing.data.Table();

        table.addColumn("up");
        table.addColumn("right");
        table.addColumn("down");
        table.addColumn("left");

        processing.data.TableRow newRow = table.addRow();
    }

    /**
     * Sick binomisk fordeling (1000 partikler!)
     * P(X partikler mot venstre) = (1000 choose X) * 0,25^X * 0,75^(1000-X)
     * Sick standardavvik, og forventningsverdi.
     * Inverse transform sampling.
     */
    public void addData(String up, String right, String down, String left) {
        processing.data.TableRow newRow = table.addRow();
        newRow.setString("up", up);
        newRow.setString("right", right);
        newRow.setString("down", down);
        newRow.setString("left", left);
        saveTable(table, "data/new.csv");
    }

}
