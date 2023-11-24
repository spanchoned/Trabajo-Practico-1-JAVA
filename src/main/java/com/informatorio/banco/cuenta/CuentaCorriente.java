package com.informatorio.banco.cuenta;

import com.informatorio.banco.cliente.Cliente;

import java.time.LocalDate;

public class CuentaCorriente extends Cuenta {
    private static final double LIMITE_SOBREGIRO_PORCENTAJE = 0.90;
    private static final double INTERES_SOBREGIRO_PORCENTAJE = 0.50;
    private static final double INTERES_DIARIO = 0.01;
    private double limiteSobreGiro;
    private double interesGeneradoDia;
    private double interesGeneradoMes;
    private double interesGeneradoAnio;
    private int oportunidadesRetiro;

    public CuentaCorriente(String numero, Cliente titular, double saldoInicial, double limiteSobreGiro) {
        super(numero, titular, saldoInicial);
        this.limiteSobreGiro = limiteSobreGiro;
        this.interesGeneradoDia = 0;
        this.interesGeneradoMes = 0;
        this.interesGeneradoAnio = 0;
        this.oportunidadesRetiro = 3;
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    public double getInteresGeneradoDia() {
        return interesGeneradoDia;
    }

    public double getInteresGeneradoMes() {
        return interesGeneradoMes;
    }

    public double getInteresGeneradoAnio() {
        return interesGeneradoAnio;
    }

    public int getOportunidadesRetiro() {
        return oportunidadesRetiro;
    }

    @Override
    public double retirar(double monto) {
        double saldoDisponible = getSaldo() + limiteSobreGiro;

        if (monto <= saldoDisponible && oportunidadesRetiro > 0) {
            oportunidadesRetiro--;

            double interesRetiro = monto * INTERES_SOBREGIRO_PORCENTAJE;
            super.retirar(monto + interesRetiro);

            interesGeneradoDia += interesRetiro;
            interesGeneradoMes += interesRetiro;
            interesGeneradoAnio += interesRetiro;

            System.out.println("Retiro con éxito. Se ha aplicado un interés por sobregiro del 0.5%.");
            return monto;
        } else {
            System.out.println("Retiro excede el saldo disponible, límite de sobregiro o no quedan oportunidades.");
            return -1;
        }
    }

//en desarrollo. :(

    public void actualizarInteresesDiarios() {
        interesGeneradoDia = 0;
    }

    public void actualizarInteresesMensuales() {
        interesGeneradoMes = 0;
    }

    public void actualizarInteresesAnuales() {
        interesGeneradoAnio = 0;
    }
}
