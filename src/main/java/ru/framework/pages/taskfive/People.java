package ru.framework.pages.taskfive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class People {
    private String name;
    private String job;

    public People() {
        super();
    }

    public People(String name, String job) {
        this.name = name;
        this.job = job;
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
