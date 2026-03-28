package org.vaadin.example.component;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import org.vaadin.example.backend.dto.FilterRangeDTO;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonthYearFilter extends Div {

    private final Select<String> monthSince;
    private final ComboBox<Integer> yearSince;
    private final Select<String> monthUntil;
    private final ComboBox<Integer> yearUntil;

    private static final Map<String, Integer> MONTH_INDEX = new LinkedHashMap<>();
    static {
        MONTH_INDEX.put("Enero", 1);
        MONTH_INDEX.put("Febrero", 2);
        MONTH_INDEX.put("Marzo", 3);
        MONTH_INDEX.put("Abril", 4);
        MONTH_INDEX.put("Mayo", 5);
        MONTH_INDEX.put("Junio", 6);
        MONTH_INDEX.put("Julio", 7);
        MONTH_INDEX.put("Agosto", 8);
        MONTH_INDEX.put("Septiembre", 9);
        MONTH_INDEX.put("Octubre", 10);
        MONTH_INDEX.put("Noviembre", 11);
        MONTH_INDEX.put("Diciembre", 12);
    }

    public MonthYearFilter() {
        monthSince = buildMonthSelect();
        yearSince = buildYearComboBox();
        monthUntil = buildMonthSelect();
        yearUntil = buildYearComboBox();
        Div since = buildGroup("del", monthSince, yearSince);
        Div until = buildGroup("fins", monthUntil, yearUntil);
        add(since,until);
    }

    private Div buildGroup(String label, Select<String> month, ComboBox<Integer> year) {
        Span span = new Span(label);
        Div group = new Div(span, month, year);
        group.addClassName("period-filter__range");
        return group;
    }

    public FilterRangeDTO getValue() {
        return new FilterRangeDTO(
                getMonthIndex(monthSince.getValue()),
                getMonthIndex(monthUntil.getValue()),
                yearSince.getValue(),
                yearUntil.getValue()
        );
    }

    private Select<String> buildMonthSelect() {
        Select<String> monthFilter = new Select<>();
        monthFilter.setItems(MONTH_INDEX.keySet());
        monthFilter.setPlaceholder("Mes");
        return monthFilter;
    }
    private ComboBox<Integer> buildYearComboBox() {
        ComboBox<Integer> yearFilter = new ComboBox<>();
        yearFilter.setItems(IntStream.rangeClosed(LocalDate.now().getYear() - 4,
                LocalDate.now().getYear()).boxed().collect(Collectors.toList()));
        yearFilter.setPlaceholder("Any");
        return yearFilter;
    }
    private Integer getMonthIndex(String mes) {
        return mes != null ? MONTH_INDEX.get(mes) : null;
    }

}
