package com.marcos.bicevida.app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.marcos.bicevida.app.model.Cliente;
import com.marcos.bicevida.app.model.Cuenta;
import com.marcos.bicevida.app.model.Seguro;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema de seguros BICEVIDA");
        System.out.println("1.- Lista de IDs de Clientes: "+listClientsIds());
        System.out.println("2.- Lista de IDs de Clientes ordenados por RUT: "+listClientsIdsSortedByRUT());
        System.out.println("3.- Lista los nombres ordenados de mayor a menor por balance en cuenta: "+sortClientsTotalBalances());
        System.out.println("4.- "+insuranceClientsByRUT());
        System.out.println("5.- Mas de 30000: "+higherClientsBalances());
        System.out.println("6.- Cantidad de dinero administrado: "+insuranceSortedByHighestBalance());
        System.out.println("7.- Seguros con cliente explusivos: "+uniqueInsurance());
        System.out.println("8.- Clientes con menos fondos: "+clientWithLessFunds());
        System.out.println("9.- Ranking de nuevo cliente: "+ newClientRanking());
    }

    private static final List<Cliente> clients = List.of(
        new Cliente(1, "86620855", "DANIEL BUSTOS"),
        new Cliente(2, "7317855K", "NICOLAS PEREZ"),
        new Cliente(3, "73826497", "ERNESTO GRANADO"),
        new Cliente(4, "88587715", "JORDAN MARTINEZ"),
        new Cliente(5, "94020190", "ALEJANDRO ZELADA"),
        new Cliente(6, "99804238", "DENIS ROJAS")
    );

    private static final List<Cuenta> accounts = List.of(
        new Cuenta(6, 1, 15000),
        new Cuenta(1, 3, 18000),
        new Cuenta(5, 3, 135000),
        new Cuenta(2, 2, 5600),
        new Cuenta(3, 1, 23000),
        new Cuenta(5, 2, 15000),
        new Cuenta(3, 3, 45900),
        new Cuenta(2, 3, 19000),
        new Cuenta(4, 3, 51000),
        new Cuenta(5, 1, 89000),
        new Cuenta(1, 2, 1600),
        new Cuenta(5, 3, 37500),
        new Cuenta(6, 1, 19200),
        new Cuenta(2, 3, 10000),
        new Cuenta(3, 2, 5400),
        new Cuenta(3, 1, 9000),
        new Cuenta(4, 3, 13500),
        new Cuenta(2, 1, 38200),
        new Cuenta(5, 2, 17000),
        new Cuenta(1, 3, 1000),
        new Cuenta(5, 2, 600),
        new Cuenta(6, 1, 16200),
        new Cuenta(2, 2, 10000)
    );

    private static final List<Seguro> insurances = List.of(
        new Seguro(1, "SEGURO APV"),
        new Seguro(2, "SEGURO DE VIDA"),
        new Seguro(3, "SEGURO COMPLEMENTARIO DE SALUD")
    );


    /**
     * Lista los IDs de clientes.
     *
     * @return Lista de IDs de clientes.
     */
    public static List<Integer> listClientsIds() {
        return clients.stream()
            .map(Cliente::getId)
            .toList();
    }

    /**
     * Lista los IDs de clientes ordenados por RUT.
     *
     * @return Lista de IDs de clientes ordenados por RUT.
     */
    public static List<Integer> listClientsIdsSortedByRUT() {
        return clients.stream()
            .sorted((client1, client2) -> client1.getRut().compareTo(client2.getRut()))
            .map(Cliente::getId)
            .toList();
    }

    /**
     * Lista los nombres de clientes ordenados de mayor a menor por la suma TOTAL
     * de los saldos de cada cliente en los seguros que participa.
     *
     * @return Lista de nombres de clientes ordenados por saldo total.
     */
    public static List<String> sortClientsTotalBalances() {
        return clients.stream()
            .sorted((client1, client2) -> {
                int totalBalanceClient1 = accounts.stream()
                    .filter(account -> account.getClientId() == client1.getId())
                    .mapToInt(Cuenta::getBalance)
                    .sum(); 
                int totalBalanceClient2 = accounts.stream()
                    .filter(account -> account.getClientId() == client2.getId())
                    .mapToInt(Cuenta::getBalance)
                    .sum();
                return Integer.compare(totalBalanceClient2, totalBalanceClient1);
            }).map(Cliente::getName).toList();
    }
   
    /**
     * Genera un objeto en que las claves sean los nombres de los seguros y los
     * valores un arreglo con los RUTs de sus clientes ordenados alfabéticamente por nombre.
     *
     * @return Mapa de seguros y RUTs de sus clientes.
     */
    public static Map<String, List<String>> insuranceClientsByRUT() {
        return insurances.stream()
            .collect(Collectors.toMap(Seguro::getName, seguro -> accounts.stream()
                .filter(cuenta -> cuenta.getInsuranceId() == seguro.getId())
                .map(cuenta -> clients.stream().filter(cliente -> cliente.getId() == cuenta.getClientId()).findFirst().orElseThrow(() -> new IllegalStateException("Cliente no encontrado")))
                // Elimina duplicados
                .collect(Collectors.toMap(Cliente::getRut, cliente -> cliente, (existing, replacement) -> existing)) 
                .values().stream()
                .sorted(Comparator.comparing(Cliente::getName))
                .map(Cliente::getRut).collect(Collectors.toList())
            ));
    }

    /**
     * Genera un arreglo ordenado decrecientemente con los saldos de clientes que
     * tengan más de 30.000 en el "Seguro APV".
     *
     * @return Lista de saldos de clientes con más de 30.000 en el "Seguro APV".
     */
    public static List<Integer> higherClientsBalances() {
        int seguroAPVId = insurances.stream().filter(seguro -> "SEGURO APV".equals(seguro.getName())).findFirst().orElseThrow(() -> new IllegalStateException("Seguro APV no encontrado")).getId();
        return accounts.stream()
            .filter(cuenta -> cuenta.getInsuranceId() == seguroAPVId)
            .collect(Collectors.groupingBy(Cuenta::getClientId, Collectors.summingInt(Cuenta::getBalance)))
            .values().stream().filter(balance -> balance > 30000).sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
    }

    /**
     * Genera un arreglo con IDs de los seguros ordenados crecientemente por la
     * cantidad TOTAL de dinero que administran.
     *
     * @return Lista de IDs de seguros ordenados por dinero administrado.
     */
    public static List<Integer> insuranceSortedByHighestBalance() {
        return insurances.stream().sorted((insurance1, insurance2) ->{
            int totalMoneyI1 = accounts.stream().filter(account -> account.getInsuranceId() == insurance1.getId()).mapToInt(Cuenta::getBalance).sum();
            int totalMoneyI2 = accounts.stream().filter(account -> account.getInsuranceId() == insurance2.getId()).mapToInt(Cuenta::getBalance).sum();
            return Integer.compare(totalMoneyI2, totalMoneyI1);
        }).map(Seguro::getId).toList();
    }

    /**
     * Genera un objeto en que las claves sean los nombres de los Seguros y los
     * valores el número de clientes que solo tengan cuentas en ese seguro.
     *
     * @return Mapa de seguros y número de clientes exclusivos.
     */
    public static Map<String, Long> uniqueInsurance() {
        return insurances.stream()
        .collect(Collectors.toMap(Seguro::getName, seguro -> clients.stream()
            .filter(cliente -> accounts.stream()
            .filter(cuenta -> cuenta.getClientId() == cliente.getId()).map(Cuenta::getInsuranceId).distinct().count() == 1) 
            .filter(cliente -> accounts.stream()
            .filter(cuenta -> cuenta.getClientId() == cliente.getId()).allMatch(cuenta -> cuenta.getInsuranceId() == seguro.getId())).count()));        
    }

    /**
     * Genera un objeto en que las claves sean los nombres de los Seguros y los
     * valores el ID de su cliente con menos fondos.
     *
     * @return Mapa de seguros y ID del cliente con menos fondos.
     */
    public static Map<String, Integer> clientWithLessFunds() {
        return insurances.stream()
        .collect(Collectors.toMap(Seguro::getName, seguro -> accounts.stream()
            .filter(cuenta -> cuenta.getInsuranceId() == seguro.getId())
            .collect(Collectors.groupingBy(Cuenta::getClientId, Collectors.summingInt(Cuenta::getBalance)))
            .entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey)
            .orElse(null)));
    }
    
    /**
     * Agrega un nuevo cliente con datos ficticios y una cuenta en el "SEGURO
     * COMPLEMENTARIO DE SALUD" con un saldo de 15000 para este nuevo cliente,
     * luego devuelve el lugar que ocupa este cliente en el ranking de la pregunta 2.
     *
     * @return Posición del nuevo cliente en el ranking de IDs ordenados por RUT.
     */
    public static int newClientRanking() {
        /*Creo unas copias mutables de las listas originales definidas en el enunciado,
        debido a que ellas son creadas con List.Of(..) lo cual las vuelve inmutables 
        impidiendo que pueda agregar un nuevo cliente o una nueva cuenta.*/
        List<Cliente> mutableClients = new ArrayList<>(clients);
        List<Cuenta> mutableAccounts = new ArrayList<>(accounts);
        
        Cliente cliente = new Cliente(7,"91310066", "Nicolas Morales"); 
        //Agrego el cleinte a la lista mutable
        mutableClients.add(cliente);
        Cuenta cuenta = new Cuenta(7, insurances.stream().filter(seguro -> "SEGURO COMPLEMENTARIO DE SALUD".equals(seguro.getName())).findFirst().orElseThrow(() -> new IllegalStateException("Seguro no encontrado")).getId(), 15000);
        //Agrego la cuenta a la lista mutable
        mutableAccounts.add(cuenta);

        //Ordeno por RUT los clientes de la lista mutable
        List<Integer> sortedClientIdsByRUT = mutableClients.stream()
        .sorted(Comparator.comparing(Cliente::getRut)) 
        .map(Cliente::getId) 
        .collect(Collectors.toList());

        return sortedClientIdsByRUT.indexOf(cliente.getId())+1;
    }
    


}