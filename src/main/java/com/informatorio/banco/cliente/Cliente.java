package com.informatorio.banco.cliente;

import com.informatorio.banco.cuenta.Cuenta;
import com.informatorio.banco.cuenta.CuentaAhorro;
import com.informatorio.banco.cuenta.CuentaCorriente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {
    private String nombre;
    private String direccion;
    private List<Cuenta> cuentas;
    private double saldoCuentaAhorro;
    private double saldoCuentaCorriente;

    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
    }

    public Map<String, String> obtenerDatosComoMap() {
        Map<String, String> datosCliente = new HashMap<>();
        datosCliente.put("Nombre", this.nombre);
        datosCliente.put("Dirección", this.direccion);
        datosCliente.put("Saldo total", String.valueOf(this.consultarSaldoTotal()));
        return datosCliente;
    }

    public String getNumeroUnico() {
        return java.util.UUID.randomUUID().toString();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void realizarOperacionEnCuenta(Cuenta cuenta, double monto) {
        if (monto >= 0) {
            cuenta.depositar(monto);
            System.out.println("Depósito realizado con éxito.");
        } else {
            double saldoDisponible = cuenta.consultarSaldo();
            if (saldoDisponible >= Math.abs(monto)) {
                cuenta.retirar(Math.abs(monto));
                System.out.println("Retiro realizado con éxito.");
            } else {
                System.out.println("Saldo insuficiente para realizar el retiro.");
            }
        }
    }

    public boolean realizarOperacionEnCuenta(int numeroCuenta, double monto) {
        Cuenta cuenta = buscarCuentaPorNumero(numeroCuenta);
        if (cuenta != null) {
            realizarOperacionEnCuenta(cuenta, monto);
            return true;
        } else {
            System.out.println("Cuenta no encontrada.");
            return false;
        }
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }

    public void eliminarCuenta(String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }

    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Cuenta corriente " + saldoCuentaCorriente);
        System.out.println("Cuenta ahorro " + saldoCuentaAhorro);


        CuentaAhorro cuentaAhorro = getCuentaAhorro();
        if (cuentaAhorro != null) {
            System.out.println("Saldo Cuenta de Ahorro: " + cuentaAhorro.getSaldo());
        }

        CuentaCorriente cuentaCorriente = getCuentaCorriente();
        if (cuentaCorriente != null) {
            System.out.println("Saldo Cuenta Corriente: " + cuentaCorriente.getSaldo());
        }
    }

    private CuentaAhorro getCuentaAhorro() {
        return cuentas.stream()
                .filter(cuenta -> cuenta instanceof CuentaAhorro)
                .map(cuenta -> (CuentaAhorro) cuenta)
                .findFirst()
                .orElse(null);
    }

    private CuentaCorriente getCuentaCorriente() {
        return cuentas.stream()
                .filter(cuenta -> cuenta instanceof CuentaCorriente)
                .map(cuenta -> (CuentaCorriente) cuenta)
                .findFirst()
                .orElse(null);
    }

    public double realizarDepositoEnCuenta(double monto) {
        saldoCuentaAhorro += monto;
        return saldoCuentaAhorro;
    }

    public double realizarRetiroCuenta(double monto) {
        saldoCuentaCorriente -= monto;
        return saldoCuentaCorriente;
    }

    public double abrirCuentaAhorro(double montoInicial) {
        saldoCuentaAhorro = montoInicial;
        return saldoCuentaAhorro;
    }

    public double abrirCuentaCorriente(double montoInicial) {
        saldoCuentaCorriente = montoInicial;
        return saldoCuentaCorriente;
    }

    public double consultarSaldoTotal() {
        return cuentas.stream().mapToDouble(Cuenta::getSaldo).sum();
    }

    public String exportarCuentasCSV() {
        StringBuilder csv = new StringBuilder();
        csv.append("Número único del titular,Nombre de titular,Numero de cuenta,Saldo,Tipo\n");
        for (Cuenta cuenta : cuentas) {
            csv.append(getNumeroUnico()).append(",")
                    .append(getNombre()).append(",")
                    .append(cuenta.getNumero()).append(",")
                    .append(cuenta.getSaldo()).append(",")
                    .append((cuenta instanceof CuentaAhorro) ? "Ahorro" : "Corriente").append("\n");
        }
        return csv.toString();
    }


    private Cuenta buscarCuentaPorNumero(int numeroCuenta) {
        return cuentas.stream()
                .filter(cuenta -> cuenta.getNumero().equals(String.valueOf(numeroCuenta)))
                .findFirst()
                .orElse(null);
    }
}
