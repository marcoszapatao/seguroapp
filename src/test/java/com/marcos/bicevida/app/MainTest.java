package com.marcos.bicevida.app;

import java.util.List;
import java.util.Map;

import org.junit.Test;

//import com.marcos.bicevida.app.Main;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
public class MainTest {

    @Test
    public void testInsuranceClientsByRUT(){
        Map<String, List<String>> correcto = new HashMap<>();

        correcto.put("SEGURO DE VIDA", Arrays.asList("94020190", "86620855", "73826497", "7317855K"));
        correcto.put("SEGURO APV", Arrays.asList("94020190", "99804238", "73826497", "7317855K"));
        correcto.put("SEGURO COMPLEMENTARIO DE SALUD", Arrays.asList("94020190", "86620855", "73826497", "88587715", "7317855K"));

        Map<String, List<String>> result = Main.insuranceClientsByRUT();
        assertEquals(correcto, result);
    }

    @Test
    public void testHigherClientsBalances(){
        List<Integer> correcto = Arrays.asList(89000, 50400, 38200, 32000);
        List<Integer> result = Main.higherClientsBalances();
        assertEquals(correcto, result);
    }

    @Test
    public void testInsuranceSortedByHighestBalance(){
        List<Integer> correcto = Arrays.asList(3, 1, 2);
        List<Integer> result = Main.insuranceSortedByHighestBalance();
        assertEquals(correcto, result);
    }

    @Test
    public void testListClientsIds(){
        List<Integer> correcto = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> result = Main.listClientsIds();
        assertEquals(correcto, result);
    }

    @Test
    public void testListClientsIdsSortedByRUT(){
        List<Integer> correcto = Arrays.asList(2, 3, 1, 4, 5, 6);
        List<Integer> result = Main.listClientsIdsSortedByRUT();
        assertEquals(correcto, result);
    }

    @Test
    public void testSortClientsTotalBalances(){
        List<String> correcto = Arrays.asList("ALEJANDRO ZELADA", "ERNESTO GRANADO", "NICOLAS PEREZ", "JORDAN MARTINEZ", "DENIS ROJAS", "DANIEL BUSTOS");
        List<String> actual = Main.sortClientsTotalBalances();
        assertEquals(correcto, actual);
    }

    @Test
    public void testUniqueInsurance(){
        Map<String, Long> correcto = new HashMap<>();
        correcto.put("SEGURO DE VIDA", 0L);
        correcto.put("SEGURO APV", 1L);
        correcto.put("SEGURO COMPLEMENTARIO DE SALUD", 1L);

        Map<String, Long> actual = Main.uniqueInsurance();
        assertEquals(correcto, actual);
    }

    @Test
    public void testClientWithLessFunds(){
        Map<String, Integer> correcto = new HashMap<>();
        correcto.put("SEGURO DE VIDA", 1);
        correcto.put("SEGURO APV", 3);
        correcto.put("SEGURO COMPLEMENTARIO DE SALUD", 1); 
    
        Map<String, Integer> actual = Main.clientWithLessFunds();
        assertEquals(correcto, actual);
    }

    @Test
    public void testNewClientRanking() {
        int correcto = 5;
        int actual = Main.newClientRanking();
        assertEquals(correcto, actual);
    }


}
