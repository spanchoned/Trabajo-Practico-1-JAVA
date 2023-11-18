package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class CuentaAhorro extends Cuenta {
    private double tasaInteres;

    public CuentaAhorro(String numero, Cliente titular, double saldoInicial, double tasaInteres) {
        super(numero, titular, saldoInicial);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public double calcularIntereses() {
        double intereses = getSaldo() * tasaInteres;
        depositar(intereses);
        return intereses;
    }

    // Método para exportar cuenta a CSV
    @Override
    public String exportarCSV() {
        return super.exportarCSV() + "," + this.getClass().getSimpleName();
    }
}
