package org.vaadin.example.component;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import org.vaadin.example.backend.dto.AddConceptForm;
import org.vaadin.example.backend.dto.ConceptContribution;
import org.vaadin.example.backend.dto.ConceptDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.example.backend.validator.DaysValidator;
import org.vaadin.example.backend.validator.HoursValidator;
import org.vaadin.example.backend.validator.ImportValidator;

import java.util.List;

public class InsuredDialog extends Dialog {

    Grid<ConceptDTO> grid = new Grid<>(ConceptDTO.class,false);
    Binder<AddConceptForm> binderForm = new Binder<>(AddConceptForm.class);
    private final Select<ConceptContribution> conceptList = new Select<>();
    private final TextField amountField = new TextField("Import");
    private final IntegerField hoursField = new IntegerField("Hores");
    private final IntegerField daysField = new IntegerField("Dies");
    public InsuredDialog(){
        conceptList.setLabel("Concepte");
        setUp();
        conceptGrid();
        add(new HorizontalLayout(new Span("Editar Debit")));
        H3 titleDialog = new H3("Conceptes");

        Button btnClose = new Button("Cancel·lar", e -> close());
        HorizontalLayout footer = new HorizontalLayout();
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.setWidthFull();
        footer.add(btnClose);
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        add(titleDialog,grid,conceptForm(),footer);
    }

    private void setUp(){
        configureBinder();
        configureSelectBinder();
    }
    private void configureBinder(){

        binderForm
                .forField(conceptList)
                .asRequired()
                .bind(AddConceptForm::getConcept,AddConceptForm::setConcept);
        binderForm
                .forField(amountField)
                .asRequired()
                .withValidator(new ImportValidator(null))
                .bind(AddConceptForm::getAmount,AddConceptForm::setAmount);

        binderForm
                .forField(hoursField)
                .withValidator(new HoursValidator(hoursField))
                .bind(AddConceptForm::getHours,AddConceptForm::setHours);

        binderForm.forField(daysField)
                .withValidator(new DaysValidator(daysField))
                .bind(AddConceptForm::getDays,AddConceptForm::setDays);

    }

    private void configureSelectBinder() {
        conceptList.addValueChangeListener( e -> {
            ConceptContribution value = e.getValue();
            binderForm
                    .forField(amountField)
                    .asRequired()
                    .withValidator(new ImportValidator(value.name()))
                    .bind(AddConceptForm::getAmount,AddConceptForm::setAmount);
            applyChangeUIByConcept(value);
        });

    }

    private void applyChangeUIByConcept(ConceptContribution value) {
        cleanFields();
        switch (value){
            case SBA:
                activeField(hoursField,false);
                activeField(daysField,false);
                break;
            case SBD:
                activeField(hoursField,true);
                activeField(daysField,true);
                break;
            case PRE:
            case CEC:
                activeField(hoursField,false);
                activeField(daysField,true);
                break;
            default:
                Notification.show("Error! Concepte no valid");
                break;
        }
    }
    private void cleanFields() {
        amountField.clear();
        amountField.setInvalid(false);
        amountField.setErrorMessage(null);

        hoursField.clear();
        hoursField.setErrorMessage(null);
        hoursField.setInvalid(false);

        daysField.clear();
        daysField.setErrorMessage(null);
        daysField.setInvalid(false);
    }
    private void activeField(IntegerField field, boolean status){
        field.setEnabled(status);
        field.setRequiredIndicatorVisible(status);
    }


    private Component conceptForm() {

        H3 titleForm = new H3("Afegir");
        titleForm.getStyle().set("margin-bottom","0");

        conceptList.setItems(ConceptContribution.SBA,ConceptContribution.SBD,ConceptContribution.CEC,ConceptContribution.PRE);
        conceptList.setPlaceholder("Escull un concepte");

        amountField.setSuffixComponent(new Span("€"));

        Button btnAdd = new Button("Afegir");

        btnAdd.addClassName("btn-primary");

        HorizontalLayout form = new HorizontalLayout(
                conceptList,amountField, hoursField, daysField,btnAdd);
        form.setAlignItems(FlexComponent.Alignment.BASELINE);
        return new VerticalLayout(titleForm,form);
    }

    private void conceptGrid(){
        grid.addColumn(ConceptDTO::getConcept).setHeader("Concepte");
        grid.addColumn(ConceptDTO::getAmount).setHeader("Import");
        grid.addColumn(ConceptDTO::getHours).setHeader("Hores");
        grid.addColumn(ConceptDTO::getDays).setHeader("Dies");
        grid.addColumn(ConceptDTO::getPercentageCASS).setHeader("% CASS");
        grid.addColumn(ConceptDTO::getContribution).setHeader("Cotizació");
        grid.addColumn(ConceptDTO::getPercentageIRPF).setHeader("% IRPF");
        grid.addColumn(ConceptDTO::getWithHolding).setHeader("Retenció");
        grid.addComponentColumn(conceptDTO -> {
            Button btnEdit = new Button(VaadinIcon.PENCIL.create());
            Button btnDelete = new Button(VaadinIcon.TRASH.create(),e-> deleteConceptAction(conceptDTO));

            btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            btnDelete.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

            return new HorizontalLayout(btnEdit,btnDelete);
        }).setHeader("Accions");
        grid.setAllRowsVisible(true);
        grid.getStyle().set("margin-bottom","4rem");
        grid.setItems(getMockData());
    }

    private void deleteConceptAction(ConceptDTO dto) {
        Dialog confirmDialog= new Dialog();
        confirmDialog.setWidth("400px");
        confirmDialog.setHeight("250px");
        confirmDialog.add(new VerticalLayout(new H3("Eliminar Concepte"),
                new Span("Vols eliminar el concepte " + dto.getConcept() + " ?")));

        Button btnConfirm = new Button("Confirmar", e -> confirmDialog.close());
        btnConfirm.addClassName("btn-primary");
        confirmDialog.setCloseOnEsc(false);
        confirmDialog.setCloseOnOutsideClick(false);
        Button btnCancel = new Button("Cancel·lar", e-> confirmDialog.close());

        confirmDialog.add(new HorizontalLayout(btnCancel,btnConfirm));

        confirmDialog.open();
    }

    private List<ConceptDTO> getMockData() {
        return List.of(
                new ConceptDTO("SBA", 1500.00, 160, 20, 28, 420, 15, 225),
                new ConceptDTO("ARV", 250.00, 20, 0, 28, 70, 15, 38)
        );
    }
}
