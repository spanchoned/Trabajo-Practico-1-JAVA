package com.informatorio.banco.dominio.dominios.cuentaAhorro;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;

import java.util.List;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(Cliente titular, double saldo) {
        super(titular, saldo);
        this.tasaInteres = 0.90;
    }

    @Override
    public void depositar(double monto) {
        double intereses = monto * tasaInteres;
        double montoFinal = monto + intereses;
        setSaldo(getSaldo() + montoFinal);
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= getSaldo()) {
            setSaldo(getSaldo() - monto);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void calcularInteres() {
    }

    @Override
    public void eliminarCuenta(List<CuentaBancaria> cuentas, String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }

    public void agregarIntereses() {
        double intereses = getSaldo() * tasaInteres / 100;
        depositar(intereses);
    }

    public double getTasaInteres() {
        return tasaInteres;
    }
}


