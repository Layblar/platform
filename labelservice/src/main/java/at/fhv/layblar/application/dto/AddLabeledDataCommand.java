package at.fhv.layblar.application.dto;

import java.util.List;

public class AddLabeledDataCommand {

    public String label;
    public List<SmartMeterDataDTO> labelData;
    public LabelMetaDataDTO labelMetaData;

}
