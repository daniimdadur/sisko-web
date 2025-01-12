package practice.web.fakultas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import practice.web.majors.model.MajorsRes;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakultasRes {
    private String id;
    private String code;
    private String name;
    private List<MajorsRes> majorsList = new ArrayList<>();
}
