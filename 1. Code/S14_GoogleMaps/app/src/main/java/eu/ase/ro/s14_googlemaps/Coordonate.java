package eu.ase.ro.s14_googlemaps;

public class Coordonate {

    private Double latitudine;
    private Double longitudine;
    private String numeOras;

    public Coordonate(Double latitudine, Double longitudine, String numeOras) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.numeOras = numeOras;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    public String getNumeOras() {
        return numeOras;
    }

    public void setNumeOras(String numeOras) {
        this.numeOras = numeOras;
    }

    @Override
    public String toString() {
        return "Coordonate{" +
                "latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", numeOras='" + numeOras + '\'' +
                '}';
    }
}
