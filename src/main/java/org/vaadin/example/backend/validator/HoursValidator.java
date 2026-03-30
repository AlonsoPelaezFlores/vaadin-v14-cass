package org.vaadin.example.backend.validator;


import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class HoursValidator implements Validator<Integer> {
    private final IntegerField field;
    public HoursValidator(IntegerField field){
        this.field = field;
    }
    @Override
    public ValidationResult apply(Integer value, ValueContext valueContext) {

        if (!field.isEnabled()){
            return ValidationResult.ok();
        }
        if (value == null || value.toString().isBlank()){
            return ValidationResult.error("El número d’hores és obligatori per aquest concepte");
        }

        if (value < 0){
            return ValidationResult.error("El camp no pot ser negatiu");
        }
        return ValidationResult.ok();
    }
}
