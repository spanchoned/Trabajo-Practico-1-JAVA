package com.informatorio.banco.banco;

import com.informatorio.banco.cliente.Cliente;
import com.informatorio.banco.cuenta.Cuenta;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public List<Cuenta> getCuentasDeCliente(Cliente cliente) {
        return cliente.getCuentas();
    }

    public void registrarCuenta(Cliente cliente, Cuenta cuenta) {
        cliente.agregarCuenta(cuenta);
    }

    public void realizarDeposito(Cliente cliente, double monto) {
    }

    public void realizarRetiro(Cliente cliente, double monto) {
    }

    public void eliminarCuenta(Cliente cliente, String numeroCuenta) {
    }

    public void agregarIntereses(Cliente cliente) {
    }
}
