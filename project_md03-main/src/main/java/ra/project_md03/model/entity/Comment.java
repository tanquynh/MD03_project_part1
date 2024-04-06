package ra.project_md03.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private int commentId;
    private int rating;
    private String comment;
    private Product product;
    private Date time;
    private int userId;
}
