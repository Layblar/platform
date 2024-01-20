package at.fhv.layblar.projectServiceRouting.model;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectDataDTO {

    public String labeledDataId;
    public String projectId;
    public String labelId;
    public String labelName;
    public String deviceCategoryId;
    public String deviceCategoryName;
    public LocalDateTime validFrom;
    public LocalDateTime validTo;
    public LocalDateTime readingTime;
    public Float v1_7_0;
    public Float v1_8_0;
    public Float v2_7_0;
    public Float v2_8_0;
    public Float v3_8_0;
    public Float v4_8_0;
    public Float v16_7_0;
    public Float v31_7_0;
    public Float v32_7_0;
    public Float v51_7_0;
    public Float v52_7_0;
    public Float v71_7_0;
    public Float v72_7_0;
    public List<ProjectMetaDataDTO> metaData;
    
}
