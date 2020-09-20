package com.thoughtworks.gtb.basicdesign.dto;

import com.thoughtworks.gtb.basicdesign.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.thoughtworks.gtb.basicdesign.constant.Invariant.*;
import static com.thoughtworks.gtb.basicdesign.constant.ErrorMessage.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;

    @NotBlank(message = NAME_IS_EMPTY)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_OUT_OF_RANGE)
    private String name;

    @NotNull(message = AGE_IS_EMPTY)
    @Min(value = AGE_MIN, message = AGE_OUT_OF_RANGE)
    private Long age;

    @NotBlank(message = AVATAR_IS_EMPTY)
    @Size(min = AVATAR_MIN_LENGTH, max = AVATAR_MAX_LENGTH, message = AVATAR_OUT_OF_RANGE)
    private String avatar;

    @Size(max = DESCRIPTION_MAX_LENGTH, message = DESCRIPTION_OUT_OF_RANGE)
    private String description;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .avatar(this.avatar)
                .description(this.description)
                .build();
    }
}
