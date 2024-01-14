package at.fhv.layblar.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MeterDataReadingKey implements Serializable {

    public LocalDateTime time;
    public String sensorId;
    public String householdId;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((sensorId == null) ? 0 : sensorId.hashCode());
        result = prime * result + ((householdId == null) ? 0 : householdId.hashCode());
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
        MeterDataReadingKey other = (MeterDataReadingKey) obj;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (sensorId == null) {
            if (other.sensorId != null)
                return false;
        } else if (!sensorId.equals(other.sensorId))
            return false;
        if (householdId == null) {
            if (other.householdId != null)
                return false;
        } else if (!householdId.equals(other.householdId))
            return false;
        return true;
    }

    
    
}
