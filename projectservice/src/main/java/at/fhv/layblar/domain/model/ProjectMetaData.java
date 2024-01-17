package at.fhv.layblar.domain.model;

public class ProjectMetaData {

    public String metaDataId;
    public String metaDataName;
    public Boolean isRequired;
    public String value;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((metaDataId == null) ? 0 : metaDataId.hashCode());
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
        ProjectMetaData other = (ProjectMetaData) obj;
        if (metaDataId == null) {
            if (other.metaDataId != null)
                return false;
        } else if (!metaDataId.equals(other.metaDataId))
            return false;
        return true;
    }

    

}
