package com.thoughtworks.gtb.basicdesign.domain;

import lombok.*;

import javax.validation.constraints.*;

import static com.thoughtworks.gtb.basicdesign.domain.Constant.*;
import static com.thoughtworks.gtb.basicdesign.domain.ErrorMessage.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    private Long userId;

    @NotNull(message = YEAR_IS_EMPTY)
    @Min(value = 1900,message = YEAR_OUT_OF_RANGE)
    @Max(value = 2020,message = YEAR_OUT_OF_RANGE)
    private Long year;

    @NotBlank(message = TITLE_IS_EMPTY)
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH,message = TITLE_OUT_OF_RANGE)
    private String title;

    @NotBlank(message = EDUCATION_DESCRIPTION_IS_EMPTY)
    @Size(min = EDUCATION_DESCRIPTION_MIN_LENGTH, max = EDUCATION_DESCRIPTION_MAX_LENGTH,message = EDUCATION_DESCRIPTION_OUT_OF_RANGE)
    private String description;
}
