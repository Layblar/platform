package at.fhv.layblar.domain.readmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.domain.model.Label;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class LabelReadModel extends PanacheEntityBase {

    @Id
    public String labelId;
    public String labelName;
    public String labelDescription;
    public String labelMethod;
    @ManyToMany
    @JoinTable(
        name = "label_category",
        joinColumns = @JoinColumn(name = "labelId"),
        inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    public List<DeviceCategoryReadModel> categories = new LinkedList<>();

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((labelId == null) ? 0 : labelId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LabelReadModel other = (LabelReadModel) obj;
        if (labelId == null) {
            if (other.labelId != null)
                return false;
        } else if (!labelId.equals(other.labelId))
            return false;
        return true;
    }

    
    
}
