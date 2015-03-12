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

        table.addRow();
    }

    /**
     * Sick binomisk fordeling (1000 partikler!)
     * P(X partikler mot venstre) = (1000 choose X) * 0,25^X * 0,75^(1000-X)
     * Sick standardavvik, og forventningsverdi.
     * Inverse transform sampling.
     */
    public void addData(int up, int right, int down, int left) {
        table.setInt(0, "up", up);
        table.setInt(0, "right", right);
        table.setInt(0, "down", down);
        table.setInt(0, "left", left);
        saveTable(table, "data.csv");
    }


}
