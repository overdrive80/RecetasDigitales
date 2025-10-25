package org.overdrive.recetasdigitales.tools;

public class GestorTiempo {
    private final long horas;
    private final long minutos;

    public GestorTiempo(long tiempoEnMinutos) {
        this.horas = tiempoEnMinutos / 60;
        this.minutos = tiempoEnMinutos % 60;
    }

    public boolean hayTiempo() {
        return horas > 0 || minutos > 0;
    }

    public String toTextoReceta() {
        if (horas > 0 && minutos > 0)
            return horas + " h " + minutos + " min";

        if (horas > 0)
            return horas + " h";

        return minutos + " min";
    }

    public long getHoras() {
        return horas;
    }

    public long getMinutos() {
        return minutos;
    }
}

