package org.overdrive.recetasdigitales.tools;

public class GestorTiempo {
    private final long horas;
    private final long minutos;

    public GestorTiempo(long tiempoEnMinutos) {
        this.horas = tiempoEnMinutos / 60;
        this.minutos = tiempoEnMinutos % 60;
    }

    public long getHoras() {
        return horas;
    }

    public long getMinutos() {
        return minutos;
    }

    public static int getHoras(long tiempoEnMinutos) {
        return (int) tiempoEnMinutos / 60;
    }

    public static int getMinutos(long tiempoEnMinutos) {
        return (int) tiempoEnMinutos % 60;
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

    public static long getTiempoMinutos(int horas, int minutos) {
        return (horas * 60L) + minutos;
    }

    public static boolean validarTiempoString(String sTiempo, int min, int max) {

        try {
            int numero = Integer.parseInt(sTiempo.trim());
            if (numero < min || numero > max) {
                return false;
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}

