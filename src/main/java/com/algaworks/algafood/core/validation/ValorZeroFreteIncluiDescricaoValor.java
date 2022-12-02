package com.algaworks.algafood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

public class ValorZeroFreteIncluiDescricaoValor implements ConstraintValidator<ValorFreteZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorFreteZeroIncluiDescricao constraintAnnotation) {
        valorField = constraintAnnotation.valorField();
        descricaoField = constraintAnnotation.descricaoField();
        descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        boolean valide = true;

        BigDecimal valor = null;
        String descricao = null;
        try {
            valor = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), valorField).getReadMethod().invoke(o);
            descricao = (String) BeanUtils.getPropertyDescriptor(o.getClass(), descricaoField).getReadMethod().invoke(o);
        } catch (IllegalAccessException e) {
           throw new ValidationException(e);
        } catch (InvocationTargetException e) {
            throw new ValidationException(e);
        }

        if ( valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
            valide = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
        }

        return valide;
    }
}
