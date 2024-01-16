package at.fhv.layblar.commands;

public class RemoveLabeledDataCommand {

    public String labeledDataId;

    public static RemoveLabeledDataCommand create(String labeledDataId){
        RemoveLabeledDataCommand command = new RemoveLabeledDataCommand();
        command.labeledDataId = labeledDataId;
        return command;
    }
    
}
