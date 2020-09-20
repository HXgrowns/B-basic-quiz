package com.thoughtworks.gtb.basicdesign.repository;

import com.thoughtworks.gtb.basicdesign.domain.Education;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Data
@AllArgsConstructor
public class EducationRespository {
    private List<Education> educations = Collections.synchronizedList(new ArrayList<>());

    public EducationRespository() {
        Education secondary = new Education(
                1L,
                2005L,
                "Secondary school specializing in artistic",
                "Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.");
        Education firstLevel = new Education(
                1L,
                2009L,
                "First level graduation in Graphic Design",
                "Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.");
        this.educations.add(secondary);
        this.educations.add(firstLevel);
    }

    public synchronized Education createEducation(Education education) {
        educations.add(education);
        return education;
    }
}
