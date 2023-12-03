package com.informatorio.banco.dominio.servicio;

import com.informatorio.banco.dominio.dominios.cliente.Cliente;

public interface ClienteServicio {
    void registrarCliente(Cliente cliente);

    void ofrecerServicios();

    void exportarCuentasCSV();
}