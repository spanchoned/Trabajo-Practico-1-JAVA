package com.informatorio.banco.cliente;

import com.informatorio.banco.cuenta.Cuenta;
import com.informatorio.banco.cuenta.CuentaAhorro;
import com.informatorio.banco.cuenta.CuentaCorriente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
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

    public CuentaCorriente abrirCuentaCorriente(double montoInicial, double montoLimiteSobregiro) {
        CuentaCorriente cuentaCorriente = new CuentaCorriente(getNumeroUnico(), this, montoInicial, montoLimiteSobregiro);
        agregarCuenta(cuentaCorriente);
        return cuentaCorriente;
    }

    public double obtenerMontoLimiteSobregiro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el límite de sobregiro para la cuenta corriente: ");
        return scanner.nextDouble();
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
        cuenta.realizarOperacionEnCuenta(monto);

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
        boolean cuentaExistente = cuentas.stream()
                .anyMatch(c -> c.getNumero().equals(cuenta.getNumero()));
        if (!cuentaExistente) {
            cuentas.add(cuenta);
        }
    }


    public void eliminarCuenta(String numeroCuenta) {
        cuentas.removeIf(cuenta -> cuenta.getNumero().equals(numeroCuenta));
    }

    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Cuenta Corriente: " + saldoCuentaCorriente);
        System.out.println("Cuenta Ahorro: " + saldoCuentaAhorro);

        for (Cuenta cuenta : cuentas) {
            System.out.println("Saldo de la cuenta " + cuenta.getNumero() + ": " + cuenta.consultarSaldo());
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

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }


    public double realizarDepositoEnCuenta(double monto) {
        saldoCuentaAhorro += monto;
        return saldoCuentaAhorro;
    }

    public double realizarRetiroCuenta(double monto) {
        saldoCuentaCorriente -= monto;
        return saldoCuentaCorriente;
    }

    public CuentaAhorro abrirCuentaAhorro(double montoInicial) {
        CuentaAhorro cuentaAhorro = new CuentaAhorro(getNumeroUnico(), this, montoInicial);
        agregarCuenta(cuentaAhorro);
        return cuentaAhorro;
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

    public void agregarInteresesCuentaAhorro() {
        CuentaAhorro cuentaAhorro = getCuentaAhorro();
        if (cuentaAhorro != null) {
            cuentaAhorro.agregarIntereses();
        } else {
            System.out.println("El cliente no tiene una cuenta de ahorro.");
        }
    }

}

//falta quitar y trabajar en algunas parte, en desarrollo XD