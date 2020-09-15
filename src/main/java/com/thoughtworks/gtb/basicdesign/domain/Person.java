package com.thoughtworks.gtb.basicdesign.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private long id;
    private String name;
    private long age;
    private String avatar;
    private String description;
}
