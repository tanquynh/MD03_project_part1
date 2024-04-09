package ra.project_md03.model.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.project_md03.model.dto.user.UserRegisterDTO;
import ra.project_md03.model.service.user.UserServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailConstraint implements ConstraintValidator<EmailUnique, UserRegisterDTO> {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void initialize(EmailUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegisterDTO userRegisterDTO, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findByMail(userRegisterDTO.getEmail()) == null;
    }
}
