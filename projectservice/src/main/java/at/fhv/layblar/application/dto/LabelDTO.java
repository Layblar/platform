package at.fhv.layblar.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.model.Label;

public class LabelDTO {

    public String labelId;
    public String labelName;
    public String labelDescription;
    public String labelMethod;
    public List<DeviceCategoryDTO> categories;

    public LabelDTO() {
    }

    private LabelDTO(String labelId, String labelName, String labelDescription, String labelMethod,
            List<DeviceCategoryDTO> categories) {
        this.labelId = labelId;
        this.labelName = labelName;
        this.labelDescription = labelDescription;
        this.labelMethod = labelMethod;
        this.categories = categories;
    }

    public static LabelDTO createLabelDTO(Label label) {
        return new LabelDTO(label.labelId, label.labelName, label.labelDescription, label.labelMethod,
                label.categories.stream().map(category -> DeviceCategoryDTO.createDeviceCategoryDTO(category))
                        .collect(Collectors.toList()));
    }

}
