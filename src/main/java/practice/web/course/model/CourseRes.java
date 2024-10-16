package practice.web.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRes {
    private String id;
    private String code;
    private String name;
    private String dosenName;
    private String grade;
    private String studentId;
    private String studentName;
}
