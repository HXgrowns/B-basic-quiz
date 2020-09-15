package com.thoughtworks.gtb.basicdesign.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    private long userId;
    private long year;
    private String title;
    private String description;
}
