package travellingsalesman.model;

import general.Dataset;

import java.util.ArrayList;

public class TravellingSalesmanDataset implements Dataset {

    private int size;
    ArrayList<ArrayList<Double>> coordinates;
    private final String file;

    private int optimum;

    public TravellingSalesmanDataset(int size, ArrayList<ArrayList<Double>> coordinates, int optimum, String file) {
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

    public ArrayList<ArrayList<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
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
