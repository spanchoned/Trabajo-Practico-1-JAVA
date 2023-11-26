package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

public abstract class Cuenta implements OperacionesCuenta {
    private String numero;
    private Cliente titular;
    protected double saldo;

    public abstract void agregarIntereses();

    public Cuenta(String numero, Cliente titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    @Override
    public double depositar(double monto) {
        saldo += monto;
        return saldo;
    }

    public boolean retirar(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public double consultarSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String exportarCSV() {
        return titular.getNumeroUnico() + "," + titular.getNombre() + "," + saldo + "," + this.getClass().getSimpleName();
    }

    public void realizarOperacionEnCuenta(double monto) {
        if (monto >= 0) {
            depositar(monto);
            System.out.println("Operación realizada con éxito.");
        } else {
            double saldoDisponible = consultarSaldo();
            if (saldoDisponible >= Math.abs(monto)) {
                retirar(Math.abs(monto));
                System.out.println("Operación realizada con éxito.");
            } else {
                System.out.println("Saldo insuficiente para realizar la operación.");
            }
        }
    }
}