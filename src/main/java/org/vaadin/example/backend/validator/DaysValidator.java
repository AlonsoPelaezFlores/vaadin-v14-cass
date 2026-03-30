package org.vaadin.example.backend.validator;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class DaysValidator implements Validator<Integer> {
    private final IntegerField field;
    public DaysValidator(IntegerField field){
        this.field = field;
    }
    @Override
    public ValidationResult apply(Integer integer, ValueContext valueContext) {

        if (!field.isEnabled()){
            return ValidationResult.ok();
        }
        if (integer == null || integer.toString().isBlank()){
            return ValidationResult.error("El número de dies és obligatori per aquest concepte");
        }
        if (integer < 0 ){
            return ValidationResult.error("No pot tenir un valor negatiu o 0");
        }
        return ValidationResult.ok();
    }
}
