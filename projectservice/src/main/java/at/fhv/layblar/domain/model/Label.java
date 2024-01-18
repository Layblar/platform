package at.fhv.layblar.domain.model;

import java.util.List;


public class Label {

    public String labelId;
    public String labelName;
    public String labelDescription;
    public String labelMethod;
    public List<DeviceCategory> categories;
    
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
        Label other = (Label) obj;
        if (labelId == null) {
            if (other.labelId != null)
                return false;
        } else if (!labelId.equals(other.labelId))
            return false;
        return true;
    }

    

}
