package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class CuentaAhorro extends Cuenta {
    private static final double TASA_INTERES_ANUAL = 0.10; // un 10% anual

    public CuentaAhorro(String numero, Cliente titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    public double getTasaInteres() {
        return TASA_INTERES_ANUAL;
    }

    public void agregarIntereses() {
        double intereses = getSaldo() * getTasaInteres(); // Suponiendo que tasaInteres es un atributo de la clase
        depositar(intereses);
    }

    @Override
    public String exportarCSV() {
        return super.exportarCSV() + "," + this.getClass().getSimpleName();
    }
}
