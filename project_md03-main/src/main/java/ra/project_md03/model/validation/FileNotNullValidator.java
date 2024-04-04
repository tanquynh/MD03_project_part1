package ra.project_md03.model.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileNotNullValidator implements ConstraintValidator<FileNotNull, MultipartFile>{

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {

        if(value.getOriginalFilename().isEmpty()) {
            return false;
        }
        return true;
    }


}
