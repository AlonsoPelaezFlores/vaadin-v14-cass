package org.vaadin.example.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class IbanForm extends HorizontalLayout {
    public IbanForm(){
        add(createBankAccountField());
    }
    public HorizontalLayout createBankAccountField(){
        HorizontalLayout bankAccount = new HorizontalLayout();
        bankAccount.add(
                createIbanSegmentField("Pais"),
                createIbanSegmentField("Banc"),
                createIbanSegmentField("Oficina"),
                createIbanSegmentField(""),
                createIbanSegmentField("Compte"),
                createIbanSegmentField("")
        );
        bankAccount.setAlignItems(Alignment.END);
        return bankAccount;
    }
    public Div createIbanSegmentField(String label){
        TextField ibanField = new TextField(label);
        Div wrapper = new Div(ibanField);
        wrapper.addClassName("iban-form__text-field");
        return wrapper;
    }
}
