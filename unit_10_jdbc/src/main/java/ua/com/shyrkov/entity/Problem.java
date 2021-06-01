package ua.com.shyrkov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Problem {

    private Integer id;
    private Integer fromId;
    private Integer toId;

}
