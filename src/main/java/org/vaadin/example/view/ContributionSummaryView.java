package org.vaadin.example.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("/summary")
@PageTitle("Contribution Summary")
public class ContributionSummaryView extends VerticalLayout {
    public ContributionSummaryView(){
        H1 titleContributionSummary = new H1("Resum de la declaració");
        add(titleContributionSummary,showContributionReportResult(),showPaymentInfo());
    }
    private Component showContributionReportResult(){
        H3 titleContribution = new H3("Resultat de la declaració");
        Span result = new Span("status");
        VerticalLayout layoutContributionResult = new VerticalLayout(titleContribution,result);
        layoutContributionResult.addClassName("card");
        Div buttonAction = new Div(buttonsActions());
        HorizontalLayout container = new HorizontalLayout();
        container.setWidthFull();
        container.add(layoutContributionResult,buttonAction);
        container.setFlexGrow(3,layoutContributionResult);
        container.setFlexGrow(1,buttonAction);
        return container;
    }
    private Component buttonsActions(){
        H3 titleAction =new H3("Accions");
        Button btnDownloadReceipt = new Button("Descarrega Comprobant");
        btnDownloadReceipt.addClassName("btn-primary");
        VerticalLayout layout = new VerticalLayout(titleAction,btnDownloadReceipt);
        layout.addClassName("card");
        layout.setWidth("auto");
        return layout;
    }

    private Component showPaymentInfo(){
        H3 titlePaymentInfo = new H3("Informació del pagament");
        FormLayout form = new FormLayout();
        form.setWidthFull();
        form.addFormItem(new Span("CT123456"), new Span("Numero del document:"));
        form.addFormItem(new Span("02/2026"),new Span("Periode:"));
        form.addFormItem(new Span("123456"),new Span("Numero del declarant:"));
        form.addFormItem(new Span("Josep Garcia Garcia"),new Span("Nom del declarant:"));
        form.addFormItem(new Span("500.0"), new Span("Cotizació CASS:"));
        form.addFormItem(new Span("10/03/2026"), new Span("Data de pagament:"));
        VerticalLayout layout = new VerticalLayout(titlePaymentInfo,form);
        layout.addClassName("card");
        return layout;
    }
}
