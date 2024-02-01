package services.requests.constraints;

import services.requests.SpaceMarineSearchRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortConstraintValidator implements ConstraintValidator<SortConstraint, String> {

    private static List<String> ALLOWED_FIELDS = Arrays.stream(SpaceMarineSearchRequest.class.getDeclaredFields())
            .map(field -> field.getName())
            .collect(Collectors.toList());
    @Override
    public boolean isValid(String sort, ConstraintValidatorContext constraintValidatorContext) {
        return sort == null || ALLOWED_FIELDS.contains(sort);
    }
}
