package com.informatorio.banco.dominio.dominios.cuentaBancaria;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CuentaBancaria {
    private static int contadorCuentas = 1;
    private String numero;
    private Cliente titular;
    protected double saldo;

    public CuentaBancaria(Cliente titular, double saldo) {
        this.numero = "CB" + contadorCuentas++;
        this.titular = titular;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract void depositar(double monto);

    public abstract boolean retirar(double monto);

    public double consultarSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public static void agregarIntereses(Cliente cliente) {
        for (CuentaBancaria cuenta : cliente.getCuentas()) {
            cuenta.calcularInteresAnual();
            cuenta.calcularInteresMensual();
            cuenta.calcularInteresSemanal();
            cuenta.calcularInteresDiario();
        }
    }

    public void eliminarCuenta(List<CuentaBancaria> cuentas, String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }

    public abstract double getTasaInteresAnual();

    public abstract void calcularInteresAnual();

    public abstract void calcularInteresMensual();

    public abstract void calcularInteresSemanal();

    public abstract void calcularInteresDiario();

    public abstract double getInteresesAnuales();

    public abstract double getInteresesMensuales();

    public abstract double getInteresesSemanales();

    public abstract double getInteresesDiarios();
}
