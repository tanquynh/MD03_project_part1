package ra.project_md03.model.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.project_md03.model.entity.Product;
import ra.project_md03.model.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private int rating;
    private String comment;
    private int productId;
}
