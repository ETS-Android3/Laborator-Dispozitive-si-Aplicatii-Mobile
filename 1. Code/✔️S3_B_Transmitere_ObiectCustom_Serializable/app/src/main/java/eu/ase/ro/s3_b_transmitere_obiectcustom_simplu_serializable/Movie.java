package eu.ase.ro.s3_b_transmitere_obiectcustom_simplu_serializable;

import java.io.Serializable;

public class Movie implements Serializable {
    private String name;
    private int profit;


    public Movie(String name, int profit) {
        this.name = name;
        this.profit = profit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", profit=" + profit +
                '}';
    }
}
