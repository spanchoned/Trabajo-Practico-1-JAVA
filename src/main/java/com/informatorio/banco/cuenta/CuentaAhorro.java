package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public class CuentaAhorro extends Cuenta implements OperacionesCuenta {
    private static final double TASA_INTERES_ANUAL = 0.90;

    public CuentaAhorro(String numero, Cliente titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    public double getTasaInteres() {
        return TASA_INTERES_ANUAL;
    }

    @Override
    public void agregarIntereses() {
        double intereses = getSaldo() * getTasaInteres();
        depositar(intereses);
        System.out.println("Intereses agregados a la cuenta de ahorro. Nuevo saldo: " + getSaldo());
    }
    @Override
    public String exportarCSV() {
        return super.exportarCSV() + "," + this.getClass().getSimpleName();
    }
}
