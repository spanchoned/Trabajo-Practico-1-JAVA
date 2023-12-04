package com.informatorio.banco.dominio.dominios.cuentaCorriente;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;

import java.util.List;

public class CuentaCorriente extends CuentaBancaria {
    private double limiteSobregiro;
    private double interesesAnuales;

    public CuentaCorriente(Cliente titular, double saldo, double limiteSobregiro) {
        super(titular, saldo);
        this.limiteSobregiro = limiteSobregiro;
        this.interesesAnuales = 0.0;
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }
    @Override
    public void depositar(double monto) {
        if (monto > 0) {
            setSaldo(getSaldo() + monto);
        }
    }

    public boolean retirar(double monto) {
        double saldoDisponible = getSaldo() + limiteSobregiro;
        if (monto <= saldoDisponible) {
            setSaldo(getSaldo() - monto);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void calcularInteresAnual() {
        double intereses = getSaldo() * getTasaInteresAnual() / 100;
        depositar(intereses);
    }

    @Override
    public void calcularInteresMensual() {
    }

    @Override
    public void calcularInteresSemanal() {
    }

    @Override
    public void calcularInteresDiario() {
    }

    @Override
    public double getTasaInteresAnual() {
        return 0.0;
    }

    @Override
    public double getInteresesAnuales() {
        return interesesAnuales;
    }

    @Override
    public double getInteresesMensuales() {
        return 0.0;
    }

    @Override
    public double getInteresesSemanales() {
        return 0.0;
    }

    @Override
    public double getInteresesDiarios() {
        return 0.0;
    }

    public double calcularLimiteSobregiro(double montoInicial) {
        return 0.9 * montoInicial;
    }
    public double obtenerMontoLimiteSobregiro() {
        return limiteSobregiro;
    }


}