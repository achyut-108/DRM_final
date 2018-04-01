package com.mJunction.drm.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by siddhartha.kumar on 4/4/2017.
 */
public class ApiValidationBase {

    private List<ValidationError> validationErrors;

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public void addValidationError(ValidationError validationError){
        if(Objects.isNull(this.validationErrors)) this.validationErrors = new ArrayList<ValidationError>();
        this.validationErrors.add(validationError);
    }

    public void addValidationError(String errorCode,String errorDescription,String fieldName,Object fieldValue){
        this.addValidationError(new ValidationError(errorCode,errorDescription,fieldName,fieldValue));
    }


}
