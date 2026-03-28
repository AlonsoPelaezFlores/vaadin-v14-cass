package org.vaadin.example.component;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import org.vaadin.example.backend.dto.ConceptDTO;
import org.vaadin.example.backend.dto.InsuredDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class DialogInsured extends Dialog {
    Grid<ConceptDTO> grid = new Grid<>(ConceptDTO.class,false);
    public DialogInsured(InsuredDTO insuredDTO){
        conceptGrid();
        add(new HorizontalLayout(new Span("Editar Debit")));
        H3 titleDialog = new H3("Conceptes");
        Button btnClose = new Button("Cancel·lar", e -> close());
        HorizontalLayout footer = new HorizontalLayout();
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.setWidthFull();
        footer.add(btnClose);
        add(titleDialog,grid,conceptForm(),footer);

    }

    private Component conceptForm() {
        H3 titleForm = new H3("Afegir");
        titleForm.getStyle().set("margin-bottom","0");
        Select<String> conceptList = new Select<>();
        conceptList.setLabel("Concepte");
        String[] concepts= {"SBA","ARV","RES","CPP","PRE","CEC","CEI"};
        conceptList.setItems(concepts);
        conceptList.setPlaceholder("Escull un concepte");
        TextField amountField = new TextField("Import");
        TextField hoursField = new TextField("Hores");
        TextField daysField = new TextField("Dies");
        Button btnAdd = new Button("Afegir");
        btnAdd.addClassName("btn-primary");
        HorizontalLayout form = new HorizontalLayout(conceptList,amountField, hoursField, daysField,btnAdd);
        form.setAlignItems(FlexComponent.Alignment.BASELINE);
        VerticalLayout layout = new VerticalLayout(titleForm,form);
        layout.setSpacing(true);
        return layout;
    }

    private void conceptGrid(){
        grid.addColumn(ConceptDTO::getConcept).setHeader("Concepte").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getAmount).setHeader("Import").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getHours).setHeader("Hores").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getDays).setHeader("Dies").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getPercentageCASS).setHeader("% CASS").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getContribution).setHeader("Cotizació").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getPercentageIRPF).setHeader("% IRPF").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::getWithHolding).setHeader("Retenció").setTextAlign(ColumnTextAlign.CENTER);
        grid.addComponentColumn(conceptDTO -> {
           Button btnEdit = new Button(VaadinIcon.PENCIL.create());
           Button btnDelete = new Button(VaadinIcon.TRASH.create(),e-> confirmAction(conceptDTO));

           btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
           btnDelete.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

           return new HorizontalLayout(btnEdit,btnDelete);
        }).setHeader("Accions");
        grid.setAllRowsVisible(true);
        grid.getStyle().set("margin-bottom","4rem");
        grid.setItems(getMockData());
    }

    private void confirmAction(ConceptDTO dto) {
        ConfirmDialog confirm= new ConfirmDialog();
        confirm.setHeader("Eliminar Concepte");
        confirm.setText("Vols eliminar el concepte " + dto.getConcept() + " ?");
        Button btnConfirm = new Button("Confirmar", e -> confirm.close());
        btnConfirm.addClassName("btn-primary");
        Button btnCancel = new Button("Cancel·lar",e -> confirm.close());
        confirm.setCancelable(true);
        confirm.setConfirmButton(btnConfirm);
        confirm.setCancelButton(btnCancel);
        confirm.open();
    }

    private List<ConceptDTO> getMockData() {
        return List.of(
                new ConceptDTO("SBA", 1500.00, 160, 20, 28, 420, 15, 225),
                new ConceptDTO("ARV", 250.00, 20, 0, 28, 70, 15, 38)
                );
    }
}
