package com.informatorio.banco.dominio.dominios.cuentaAhorro;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteresAnual;
    private double interesesAnuales;
    private double interesesMensuales;
    private double interesesSemanales;
    private double interesesDiarios;

    public CuentaAhorro(Cliente titular, double saldo) {
        super(titular, saldo);
        this.tasaInteresAnual = 0.0;
        this.interesesAnuales = 0.0;
        this.interesesMensuales = 0.0;
        this.interesesSemanales = 0.0;
        this.interesesDiarios = 0.0;
    }

    public void setTasaInteresAnual(double tasaInteresAnual) {
        this.tasaInteresAnual = tasaInteresAnual;
    }

    @Override
    public void depositar(double monto) {
        double intereses = monto * tasaInteresAnual / 100;
        double montoFinal = monto + intereses;
        setSaldo(getSaldo() + montoFinal);
    }

    public void agregarIntereses() {
        interesesAnuales += getSaldo() * tasaInteresAnual / 100;
        interesesMensuales += getSaldo() * tasaInteresAnual / 12 / 100;
        interesesSemanales += getSaldo() * tasaInteresAnual / 52 / 100;
        interesesDiarios += getSaldo() * tasaInteresAnual / 365 / 100;
    }

    @Override
    public boolean retirar(double monto) {
        return false;
    }

    @Override
    public double getTasaInteresAnual() {
        return tasaInteresAnual;
    }

    @Override
    public void calcularInteresAnual() {
        interesesAnuales += getSaldo() * tasaInteresAnual / 100;
    }

    @Override
    public void calcularInteresMensual() {
        double tasaInteresMensual = tasaInteresAnual / 12;
        interesesMensuales += getSaldo() * tasaInteresMensual / 100;
    }

    @Override
    public void calcularInteresSemanal() {
        double tasaInteresSemanal = tasaInteresAnual / 52;
        interesesSemanales += getSaldo() * tasaInteresSemanal / 100;
    }

    @Override
    public void calcularInteresDiario() {
        double tasaInteresDiario = tasaInteresAnual / 365;
        interesesDiarios += getSaldo() * tasaInteresDiario / 100;
    }

    @Override
    public double getInteresesAnuales() {
        return interesesAnuales;
    }

    @Override
    public double getInteresesMensuales() {
        return interesesMensuales;
    }

    @Override
    public double getInteresesSemanales() {
        return interesesSemanales;
    }

    @Override
    public double getInteresesDiarios() {
        return interesesDiarios;
    }
}
