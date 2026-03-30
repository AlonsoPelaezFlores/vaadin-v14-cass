package org.vaadin.example.backend.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.Getter;
import org.apache.commons.validator.routines.IBANValidator;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;

public class IbanValidator implements Validator<String> {

    private final IBANValidator validator = IBANValidator.getInstance();
    private final String countryCode;
    private static final Integer MAX_LENGTH = 24;
    private static final Integer MIN_LENGTH = 2;
    public IbanValidator(){
        this.countryCode = "AD";
    }
    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        if (value == null || value.isBlank()) {
            return ValidationResult.error("IBAN is required");
        }
        if (value.length()< MAX_LENGTH){
            return ValidationResult.error("La longitud del IBAN es muy corto");
        }
        if (!validator.isValid(value)){
            return ValidationResult.error("El formato del IBAN no es valido");
        }
        String codeCountry = value.substring(0,2).toUpperCase();
        if (!countryCode.equals(codeCountry)){
            return ValidationResult.error("IBAN solo debe ser de Andorra");
        }
        return ValidationResult.ok();
    }
}
