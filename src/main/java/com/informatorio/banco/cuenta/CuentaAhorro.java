package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class CuentaAhorro extends Cuenta {
    private static final double TASA_INTERES_ANUAL = 0.10; // 10% anual

    public CuentaAhorro(String numero, Cliente titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    public double getTasaInteres() {
        return TASA_INTERES_ANUAL;
    }

    public double calcularIntereses() {
        double intereses = getSaldo() * TASA_INTERES_ANUAL;
        depositar(intereses);
        return intereses;
    }

    @Override
    public String exportarCSV() {
        return super.exportarCSV() + "," + this.getClass().getSimpleName();
    }
}
