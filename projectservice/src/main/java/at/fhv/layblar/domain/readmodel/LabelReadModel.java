package at.fhv.layblar.domain.readmodel;

import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.model.Label;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class LabelReadModel extends PanacheEntityBase {

    @Id
    public String labelId;
    public String labelName;
    public String labelDescription;
    public String labelMethod;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "label_category",
        joinColumns = @JoinColumn(name = "labelId"),
        inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    public List<DeviceCategoryReadModel> categories;
    @ManyToOne
    @JoinColumn(name = "projectId")
    public ProjectReadModel project;

    public LabelReadModel(){};

    public static LabelReadModel createFromLabel(Label label){
        LabelReadModel lrm = new LabelReadModel();
        lrm.labelId = label.labelId;
        lrm.labelName = label.labelName;
        lrm.labelDescription = label.labelDescription;
        lrm.labelMethod = label.labelMethod;
        lrm.categories = label.categories.stream().map(category -> DeviceCategoryReadModel.createFromDeviceCategory(category)).collect(Collectors.toList());
        return lrm;
    }
    
}
