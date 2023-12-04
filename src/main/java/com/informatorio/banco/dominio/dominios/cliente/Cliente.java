package com.informatorio.banco.dominio.dominios.cliente;

import com.informatorio.banco.dominio.dominios.banco.Banco;
import com.informatorio.banco.dominio.dominios.cuentaAhorro.CuentaAhorro;
import com.informatorio.banco.dominio.dominios.cuentaBancaria.CuentaBancaria;
import com.informatorio.banco.dominio.dominios.cuentaCorriente.CuentaCorriente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {
    private String nombre;
    private String direccion;
    private List<CuentaBancaria> cuentas;
    private String numeroUnico;



    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
        this.numeroUnico = generarNumeroUnico();

    }

    public Map<String, String> obtenerDatosComoMap() {
        Map<String, String> datosCliente = new HashMap<>();
        datosCliente.put("Nombre Completo", nombre);
        datosCliente.put("Dirección", direccion);
        return datosCliente;
    }

    public void setCuentas(List<CuentaBancaria> cuentas) {
        this.cuentas = cuentas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

    public void abrirCuentas(double montoInicial, String tipoCuenta, Banco banco) {
        if (tipoCuenta.equalsIgnoreCase("Ahorro")) {

            CuentaAhorro cuentaAhorro = abrirCuentaAhorro(montoInicial, 0.70);
            banco.registrarCuenta(this, cuentaAhorro);

        } else if (tipoCuenta.equalsIgnoreCase("Corriente")) {
            double montoLimiteSobregiro = new CuentaCorriente(this, 0, 0)
                    .calcularLimiteSobregiro(montoInicial);
            CuentaCorriente cuentaCorriente = abrirCuentaCorriente(montoInicial, montoLimiteSobregiro);
            banco.registrarCuenta(this, cuentaCorriente);

        } else if (tipoCuenta.equalsIgnoreCase("Ambas")) {
            double montoLimiteSobregiro = new CuentaCorriente(this, 0, 0).obtenerMontoLimiteSobregiro();

            CuentaAhorro cuentaAhorro = abrirCuentaAhorro(montoInicial, 0.70);
            CuentaCorriente cuentaCorriente = abrirCuentaCorriente(montoInicial, montoLimiteSobregiro);
            banco.registrarCuenta(this, cuentaAhorro);
            banco.registrarCuenta(this, cuentaCorriente);
        } else {
            System.out.println("Tipo de cuenta no válido.");
        }
    }


    public CuentaAhorro abrirCuentaAhorro(double montoInicial, double tasaInteres) {
        CuentaAhorro cuentaAhorro = new CuentaAhorro(this, montoInicial);
        cuentaAhorro.setTasaInteresAnual(tasaInteres);
        agregarCuenta(cuentaAhorro);
        return cuentaAhorro;
    }


    public void calcularIntereses() {
        double interesesAnuales = 0.0;
        double interesesMensuales = 0.0;
        double interesesSemanales = 0.0;
        double interesesDiarios = 0.0;

        for (CuentaBancaria cuenta : getCuentas()) {
            cuenta.calcularInteresAnual();
            cuenta.calcularInteresMensual();
            cuenta.calcularInteresSemanal();
            cuenta.calcularInteresDiario();

            if (cuenta instanceof CuentaAhorro) {
                CuentaAhorro cuentaAhorro = (CuentaAhorro) cuenta;
                interesesAnuales += cuentaAhorro.getInteresesAnuales();
                interesesMensuales += cuentaAhorro.getInteresesMensuales();
                interesesSemanales += cuentaAhorro.getInteresesSemanales();
                interesesDiarios += cuentaAhorro.getInteresesDiarios();
            }
        }

        System.out.println("Intereses acumulados:");
        System.out.println("Anuales: " + interesesAnuales);
        System.out.println("Mensuales: " + interesesMensuales);
        System.out.println("Semanales: " + interesesSemanales);
        System.out.println("Diarios: " + interesesDiarios);
    }

    public CuentaCorriente abrirCuentaCorriente(double montoInicial, double montoLimiteSobregiro) {
        CuentaCorriente cuentaCorriente = new CuentaCorriente(this, montoInicial, montoLimiteSobregiro);
        agregarCuenta(cuentaCorriente);
        return cuentaCorriente;
    }

    public void eliminarCuenta(String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }

    public void mostrarInformacion() {
        System.out.println("Información del cliente:");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Dirección: " + this.direccion);
    }

    public double consultarSaldoTotal() {
        return cuentas.stream().mapToDouble(CuentaBancaria::consultarSaldo).sum();
    }

    public String getNumeroUnico() {
        return numeroUnico;
    }
    private String generarNumeroUnico() {
        return java.util.UUID.nameUUIDFromBytes(nombre.getBytes()).toString();
    }



}
