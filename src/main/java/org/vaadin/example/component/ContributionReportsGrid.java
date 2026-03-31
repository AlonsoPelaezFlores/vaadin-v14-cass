package org.vaadin.example.component;

import org.vaadin.example.backend.dto.ContributionReportRowDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class ContributionReportsGrid extends VerticalLayout {
    public ContributionReportsGrid(){
        Grid<ContributionReportRowDTO> grid = new Grid<>(ContributionReportRowDTO.class, false);

        grid.addColumn(ContributionReportRowDTO::getPeriod).setHeader("Periode");
        grid.addColumn(ContributionReportRowDTO::getSheetNumber).setHeader("Num. full");
        grid.addColumn(ContributionReportRowDTO::getIrpfNumber).setHeader("Num. IRPF");
        grid.addColumn(ContributionReportRowDTO::getSubmissionDate).setHeader("Data Presentacio");
        grid.addColumn(ContributionReportRowDTO::getTotal).setHeader("Total");
        grid.addColumn(ContributionReportRowDTO::getPayment).setHeader("Pagament");
        grid.addColumn(ContributionReportRowDTO::getStatus).setHeader("Estat");
        grid.addComponentColumn(contribution ->{
            Button viewBtn = new Button(VaadinIcon.EYE.create(), event ->{
                UI.getCurrent().navigate("/summary");
            });
            Button modifyBtn = new Button(VaadinIcon.PENCIL.create(), e -> {
                UI.getCurrent().navigate("/details");
            });
            Button uploadXmlBtn = new Button(VaadinIcon.UPLOAD.create(), e -> {
                ExportErrorDialog exportErrorDialog = new ExportErrorDialog();
                exportErrorDialog.setCloseOnEsc(false);
                exportErrorDialog.setCloseOnOutsideClick(false);
                exportErrorDialog.setModal(false);
                exportErrorDialog.open();
            });
            viewBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
            modifyBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
            return new HorizontalLayout(viewBtn, modifyBtn,uploadXmlBtn);
        }).setHeader("Accions");
        grid.setItems(getMockData());
        grid.setWidthFull();
        add(grid);
    }

    private List<ContributionReportRowDTO> getMockData() {
        return List.of(
                new ContributionReportRowDTO(YearMonth.of(2024, 1), "001", "IRPF-2024-001", LocalDate.of(2024, 1, 15), 1500.00, 750.00, "Pendent"),
                new ContributionReportRowDTO(YearMonth.of(2024, 2), "002", "IRPF-2024-002", LocalDate.of(2024, 2, 10), 2300.50, 1150.25, "Declarat i pagat"),
                new ContributionReportRowDTO(YearMonth.of(2024, 3), "003", "IRPF-2024-003", LocalDate.of(2024, 3, 20), 980.75, 490.38, "Declarat i pagat"),
                new ContributionReportRowDTO(YearMonth.of(2024, 4), "004", "IRPF-2024-004", LocalDate.of(2024, 4, 5),  3200.00, 1600.00, "Declarat i no pagat"),
                new ContributionReportRowDTO(YearMonth.of(2024, 6), "006", "IRPF-2024-006", LocalDate.of(2024, 6, 22), 4100.00, 2050.00, "Declarat i not pagat"),
                new ContributionReportRowDTO(YearMonth.of(2024, 7), "007", "IRPF-2024-007", LocalDate.of(2024, 7, 8),  1875.50, 937.75, "Pendent")
        );
    }
}
