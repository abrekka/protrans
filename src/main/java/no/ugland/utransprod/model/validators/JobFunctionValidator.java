package no.ugland.utransprod.model.validators;

import no.ugland.utransprod.gui.model.JobFunctionModel;
import no.ugland.utransprod.util.ModelUtil;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Validator for funksjon
 * @author atle.brekka
 */
public class JobFunctionValidator implements Validator {

    /**
     * Holds the order to be validated.
     */
    private JobFunctionModel jobFunctionModel;

    /**
     * Constructs an OrderValidator on the given Order.
     * @param aJobFunctionModel
     */
    public JobFunctionValidator(final JobFunctionModel aJobFunctionModel) {
        this.jobFunctionModel = aJobFunctionModel;
    }


    /**
     * Validates this Validator's Order and returns the result as an instance of
     * {@link ValidationResult}.
     * @return the ValidationResult of the order validation
     */
    public final ValidationResult validate() {
        PropertyValidationSupport support = new PropertyValidationSupport(
                jobFunctionModel, "Funksjon");

        if (ValidationUtils.isBlank(ModelUtil.nullToString(jobFunctionModel
                .getJobFunctionName()))) {
            support.addError("navn", "må settes");
        }

        return support.getResult();
    }

}
