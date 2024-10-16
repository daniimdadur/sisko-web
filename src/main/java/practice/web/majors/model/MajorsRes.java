package practice.web.majors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import practice.web.student.model.StudentRes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MajorsRes {
    private String id;
    private String code;
    private String name;
    private String fakultasId;
    private String fakultasName;
    private List<StudentRes> studentList = new ArrayList<>();
}
