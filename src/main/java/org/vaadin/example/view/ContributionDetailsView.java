package org.vaadin.example.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.backend.dto.FilerData;
import org.vaadin.example.component.IbanForm;
import org.vaadin.example.component.InsuredGrid;


@Route("/details")
@CssImport("./themes/principal/view/contribution-details-view.css")
@PageTitle("Contribution Details")
public class ContributionDetailsView extends VerticalLayout {
    private final IbanForm ibanFormIRPF = new IbanForm();
    private final IbanForm ibanFormContribution = new IbanForm();
    public ContributionDetailsView(){
        H1 title = new H1("Detall Declaracio Cotizació");
        add(
            title,
            filerData(),
            showContributionReportDetails(),
            showInsuredList(),
            validatorData(),
            bankingData(),
            signAndConfirmData(),
            exit()
        );
    }
    private Component filerData(){
        FilerData filer = new FilerData();
        H3 titleFilerData = new H3("Dades del Declarant");

        FormLayout filerData = new FormLayout();
        filerData.addFormItem(new Span(filer.getFilerNumber()), new Span("Numero del declarant"));
        filerData.addFormItem(new Span(filer.getFilerName()), new Span("Nom del declarant"));
        filerData.addFormItem(new Span(filer.getNrtNumber()), new Span("NRT"));
        filerData.addFormItem(new Span(filer.getIrpfNumber()), new Span("Num. IRPF"));

        Div cardFilerData = new Div(titleFilerData,filerData);
        cardFilerData.addClassName("card");
        Div divButtonAction =new Div(buttonActions());
        HorizontalLayout containerHorizontal = new HorizontalLayout(cardFilerData, divButtonAction);
        containerHorizontal.addClassName("card-container");
        containerHorizontal.setFlexGrow(3,cardFilerData);
        containerHorizontal.setFlexGrow(1,divButtonAction);
        return  containerHorizontal;
    }
    private VerticalLayout buttonActions(){
        VerticalLayout cardButton = new VerticalLayout();
        H3 title = new H3("Accions");

        Button btnLoadFiler = new Button("Recuperar Dades", e -> loadPreviousPeriod());
        Button btnDeleteData = new Button("Suprimir Dades", e -> deleteCurrentData());

        btnLoadFiler.addClassName("btn-primary");
        btnDeleteData.addClassName("btn-primary");

        cardButton.add(title,btnLoadFiler,btnDeleteData);
        cardButton.addClassName("card");
        cardButton.setWidthFull();
        return cardButton;
    }

    private void loadPreviousPeriod() {
        Dialog loadDataDialog = new Dialog();
        add(new HorizontalLayout(new Span("Recuperar dades periodo anterior")));
        Span label = new Span("Periode a recuperar");
        Select<String> datesSelect = new Select<>();
        String[] dates = {"2026/02","2026/01","2025/12","2025/11"};
        datesSelect.setItems(dates);
        HorizontalLayout layout = new HorizontalLayout(label,datesSelect);
        Button btnConfirm = new Button("Confirmar",e-> loadDataDialog.close());
        btnConfirm.addClassName("btn-primary");
        Button btnCancel = new Button("Cancel·lar",e-> loadDataDialog.close());
        HorizontalLayout footer = new HorizontalLayout();
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.setWidthFull();
        footer.add(btnConfirm,btnCancel);
        loadDataDialog.add(layout,footer);
        loadDataDialog.open();
    }

    private void deleteCurrentData() {
        ConfirmDialog deleteDataDialog= new ConfirmDialog();
        deleteDataDialog.setHeader("Suprimir dades actuales");
        deleteDataDialog.setText("Estas segur de suprimir totes les dades d'aquesta declaració ?");

        Button btnConfirm = new Button("Confirmar", e -> deleteDataDialog.close());
        btnConfirm.addClassName("btn-primary");
        Button btnCancel = new Button("Cancel·lar",e -> deleteDataDialog.close());
        deleteDataDialog.setCancelable(true);
        deleteDataDialog.setConfirmButton(btnConfirm);
        deleteDataDialog.setCancelButton(btnCancel);
        deleteDataDialog.open();
    }

    private Component showContributionReportDetails(){
        H3 titleContributionData = new H3("Dades del la Declaració");
        FormLayout form = new FormLayout();

        form.addFormItem(new Span("CT123456"), new Span("Numero del document:"));
        form.addFormItem(new Span("02/2026"), new Span("Periode:"));
        form.addFormItem(new Span("0.0"), new Span("Import total CASS:"));
        form.addFormItem(new Span("0.0"), new Span("Cotizació CASS:"));
        form.addFormItem(new Span("0.0"), new Span("Import total IRPF:"));
        form.addFormItem(new Span("0.0"), new Span("Retenció IRPF:"));
        VerticalLayout container = new VerticalLayout(titleContributionData,form);
        container.addClassName("card");
        return container;
    }

    private Component showInsuredList(){

        H3 titleInsuredList = new H3("Dades dels Assegurats");
        VerticalLayout container = new VerticalLayout(
                titleInsuredList,
                headerInsuredList(),
                new InsuredGrid(),
                paginationInsuredList()
        );
        container.addClassName("card");
        container.setSpacing(false);
        return container;
    }

    private Component paginationInsuredList() {
        Anchor first = new Anchor("#", "<<Primera");
        Anchor prev = new Anchor("#", "<Anterior");
        Anchor next = new Anchor("#", "Seguent>");
        Anchor last = new Anchor("#", "Darrera>>");
        HorizontalLayout footer = new HorizontalLayout(first,prev,next,last);
        last.getStyle().set("margin-right","1rem");
        footer.setWidthFull();
        footer.setJustifyContentMode(JustifyContentMode.END);
        return footer;
    }

    private HorizontalLayout headerInsuredList(){
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        TextField searchField = new TextField();
        searchField.setPlaceholder("assegurat");
        searchField.setSuffixComponent(VaadinIcon.SEARCH.create());
        Select<Integer> rowToShow = new Select<>();
        Span labelRow = new Span("Files a mostrar");
        rowToShow.setItems(1,2,3,4,5,6,7,8,9,10);
        HorizontalLayout filterRow = new HorizontalLayout(labelRow,rowToShow);
        filterRow.addClassName("filter-row");
        headerLayout.add(searchField,filterRow);
        headerLayout.setSpacing(false);
        headerLayout.setPadding(false);
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);
        headerLayout.addClassName("header-insured");
        return headerLayout;
    }
    private HorizontalLayout validatorData(){
        HorizontalLayout layout =new HorizontalLayout();
        layout.setWidthFull();
        Button btnValidate = new Button("Validar les dades de la cotizació");
        btnValidate.addClassName("btn-primary");
        btnValidate.getStyle().set("margin","0 auto");
        layout.add(btnValidate);
        layout.addClassName("card");
        return layout;
    }
    private VerticalLayout bankingData(){
        VerticalLayout bankingDataLayout = new VerticalLayout();
        Checkbox checkBoxBankingDataContribution = new Checkbox("Cárrec Bancari declaració cotizació");
        Span valueContribution = new Span("0.0 €");
        HorizontalLayout checkContribution = new HorizontalLayout(checkBoxBankingDataContribution,valueContribution);
        checkContribution.setSpacing(false);
        checkContribution.setAlignItems(Alignment.BASELINE);
        Component ibanIRPF = createIbanLayout(ibanFormContribution);

        Checkbox checkBoxBankingDataIRPF = new Checkbox("Cárrec Bancari IRPF");
        Span valueIRPF = new Span("0.0 €");
        HorizontalLayout checkIRPF = new HorizontalLayout(checkBoxBankingDataIRPF,valueIRPF);
        checkIRPF.setSpacing(false);
        checkIRPF.setAlignItems(Alignment.BASELINE);
        Component ibanContribution = createIbanLayout(ibanFormIRPF);

        Checkbox checkBoxWithoutPayment = new Checkbox("Declaració sense pagament(Aquestá declaració restará pendent de pagament)");

        bankingDataLayout.add(
                checkBoxBankingDataContribution,
                ibanIRPF,
                checkBoxBankingDataIRPF,
                ibanContribution,
                checkBoxWithoutPayment);

        bankingDataLayout.addClassName("card");
        bankingDataLayout.setSpacing(false);
        return bankingDataLayout;
    }
    private HorizontalLayout createIbanLayout(IbanForm ibanForm){

        HorizontalLayout ibanLayout= new HorizontalLayout();
        Button btnLoadAccount = new Button("Recuperar compte");
        btnLoadAccount.addClassName("btn-primary");
        btnLoadAccount.getStyle().set("margin-left","1.5rem");

        ibanLayout.add(ibanForm, btnLoadAccount);
        ibanLayout.getStyle().set("margin-left","5rem");
        ibanLayout.setSpacing(false);
        ibanLayout.setAlignItems(Alignment.END);

        return ibanLayout;
    }
    private HorizontalLayout signAndConfirmData() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        Button btnValidate = new Button("Confirmar y signar les dades", e->{
            Dialog dialog = new Dialog();
            dialog.add(new Span("acc: "),new Span(ibanFormContribution.getBankAccount()));
            dialog.add(new Span("result: "),new Span(ibanFormContribution.validateIban()));
            dialog.open();
        });
        btnValidate.addClassName("btn-primary");
        btnValidate.getStyle().set("margin", "0 auto");
        layout.add(btnValidate);
        layout.addClassName("card");
        return layout;
    }
    private HorizontalLayout exit(){
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        Button btnValidate = new Button("Sortir", e -> UI.getCurrent().navigate("/"));
        btnValidate.addClassName("btn-primary");
        btnValidate.getStyle().set("margin", "0 auto");
        layout.add(btnValidate);
        return layout;
    }
}