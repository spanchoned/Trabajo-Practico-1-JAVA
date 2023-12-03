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


    public Cliente(String nombre, String direccion, String s) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
        this.numeroUnico = generarNumeroUnico(nombre);
    }

    private String generarNumeroUnico(String nombre) {
        return java.util.UUID.nameUUIDFromBytes(nombre.getBytes()).toString();
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
            CuentaAhorro cuentaAhorro = abrirCuentaAhorro(montoInicial);
            banco.registrarCuenta(this, cuentaAhorro);
        } else if (tipoCuenta.equalsIgnoreCase("Corriente")) {
            double montoLimiteSobregiro = new CuentaCorriente(this, 0, 0).obtenerMontoLimiteSobregiro();
            CuentaCorriente cuentaCorriente = abrirCuentaCorriente(montoInicial, montoLimiteSobregiro);
            banco.registrarCuenta(this, cuentaCorriente);
        } else if (tipoCuenta.equalsIgnoreCase("Ambas")) {
            double montoLimiteSobregiro = new CuentaCorriente(this, 0, 0).obtenerMontoLimiteSobregiro();
            CuentaAhorro cuentaAhorro = abrirCuentaAhorro(montoInicial);
            CuentaCorriente cuentaCorriente = abrirCuentaCorriente(montoInicial, montoLimiteSobregiro);
            banco.registrarCuenta(this, cuentaAhorro);
            banco.registrarCuenta(this, cuentaCorriente);
        } else {
            System.out.println("Tipo de cuenta no válido.");
        }
    }
    public CuentaAhorro abrirCuentaAhorro(double montoInicial) {
        CuentaAhorro cuentaAhorro = new CuentaAhorro(this, montoInicial);
        cuentas.add(cuentaAhorro);
        return cuentaAhorro;
    }

    public CuentaCorriente abrirCuentaCorriente(double montoInicial, double montoLimiteSobregiro) {
        CuentaCorriente cuentaCorriente = new CuentaCorriente(this, montoInicial, montoLimiteSobregiro);
        agregarCuenta(cuentaCorriente);
        return cuentaCorriente;
    }

    public void eliminarCuenta(String numeroCuenta, Banco banco) {
        CuentaBancaria cuentaAEliminar = null;

        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getNumero().equals(numeroCuenta)) {
                cuentaAEliminar = cuenta;
                break;
            }
        }

        if (cuentaAEliminar != null) {
            cuentas.remove(cuentaAEliminar);
            banco.eliminarCuentaOrdenada(cuentaAEliminar);
            System.out.println("Cuenta eliminada con éxito.");
        } else {
            System.out.println("La cuenta con el número " + numeroCuenta + " no existe para este cliente.");
        }
    }
    public void mostrarInformacion() {
        System.out.println("Información del cliente:");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Dirección: " + this.direccion);
    }

    public double consultarSaldoTotal() {
        return cuentas.stream().mapToDouble(CuentaBancaria::consultarSaldo).sum();
    }

    /*public String getNumeroUnico() {
        return java.util.UUID.randomUUID().toString();
    }*/
    public String getNumeroUnico() {
        return this.numeroUnico;
    }
}
