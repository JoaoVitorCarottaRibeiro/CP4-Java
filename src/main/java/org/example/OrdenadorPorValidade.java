package org.example;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.example.Model.Medicamento;

public class OrdenadorPorValidade {

    public static List<Medicamento> ordenar(List<Medicamento> lista) {
        return lista.stream()
                .sorted(Comparator.comparing(m -> LocalDate.parse(m.getValidade() + "-01")))
                .collect(Collectors.toList());
    }
}
