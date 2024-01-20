package at.fhv.layblar.domain.readmodel;

import java.io.Serializable;

public class LabeledDataReadModelKey implements Serializable {

    public String labeledDataId;
    public Integer batchNumber; 
    
    public LabeledDataReadModelKey(){}

    public LabeledDataReadModelKey(String entityId, Integer batchNumber) {
        this.labeledDataId = entityId;
        this.batchNumber = batchNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((labeledDataId == null) ? 0 : labeledDataId.hashCode());
        result = prime * result + ((batchNumber == null) ? 0 : batchNumber.hashCode());
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
        LabeledDataReadModelKey other = (LabeledDataReadModelKey) obj;
        if (labeledDataId == null) {
            if (other.labeledDataId != null)
                return false;
        } else if (!labeledDataId.equals(other.labeledDataId))
            return false;
        if (batchNumber == null) {
            if (other.batchNumber != null)
                return false;
        } else if (!batchNumber.equals(other.batchNumber))
            return false;
        return true;
    }

    
    
}
