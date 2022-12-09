package travellingsalesman.model;

import general.Dataset;

import java.util.ArrayList;

public class TSDataset implements Dataset {

    private int size;
    private ArrayList<Coordinates> coordinates;
    private final String file;

    private int optimum;

    public TSDataset(int size, ArrayList<Coordinates> coordinates, int optimum, String file) {
        this.size = size;
        this.coordinates = coordinates;
        this.file = file;
        this.optimum = optimum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    public int getOptimum() {
        return optimum;
    }

    public void setOptimum(int optimum) {
        this.optimum = optimum;
    }

    @Override
    public String getFile() {
        return file;
    }
}
