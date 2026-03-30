package org.vaadin.example.component;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.vaadin.example.backend.validator.IbanValidator;

@CssImport("./themes/principal/component/iban-form.css")
public class IbanForm extends HorizontalLayout {
    private final StringBuilder bankAcc = new StringBuilder();
    private final TextField countryField = new TextField("Pais");
    private final TextField bankField = new TextField("Banc");
    private final TextField officeField = new TextField("Oficina");
    private final TextField accountFieldFirstPart = new TextField();
    private final TextField accountFieldSecondPart = new TextField("Compte");
    private final TextField accountFieldThirdPart = new TextField();

    public IbanForm(){
        configure();
        createBankAccountField();
    }
    public void createBankAccountField(){
        add(
                ibanFieldDiv(countryField),
                ibanFieldDiv(bankField),
                ibanFieldDiv(officeField),
                ibanFieldDiv(accountFieldFirstPart),
                ibanFieldDiv(accountFieldSecondPart),
                ibanFieldDiv(accountFieldThirdPart)
        );
        setAlignItems(Alignment.END);
    }
    private void configure(){
        countryField.setPattern("[A-Za-z]{2}[0-9]{2}");
        countryField.setMaxLength(4);

        bankField.setPattern("[0-9]{4}");
        bankField.setMaxLength(4);

        officeField.setPattern("[0-9]{4}");
        officeField.setMaxLength(4);

        accountFieldFirstPart.setPattern("[0-9]{4}");
        accountFieldFirstPart.setMaxLength(4);

        accountFieldSecondPart.setPattern("[0-9]{4}");
        accountFieldSecondPart.setMaxLength(4);

        accountFieldThirdPart.setPattern("[0-9]{4}");
        accountFieldThirdPart.setMaxLength(4);
    }
    public Div ibanFieldDiv(TextField field){
        Div wrapper = new Div(field);
        wrapper.addClassName("iban-form__field");
        return wrapper;
    }

    public String getBankAccount(){
        bankAcc.setLength(0);
        bankAcc
                .append(countryField.getValue())
                .append(bankField.getValue())
                .append(officeField.getValue())
                .append(accountFieldFirstPart.getValue())
                .append(accountFieldSecondPart.getValue())
                .append(accountFieldThirdPart.getValue());
        return bankAcc.toString();
    }
    public String validateIban() {
        IbanValidator ibanValidator = new IbanValidator();
        ValidationResult result = ibanValidator.apply(getBankAccount(),new ValueContext());
        if (result.isError()){
            return result.getErrorMessage();
        }else return "IBAN IS VALID";
    }
}
