package at.fhv.layblar;

public class MeterDataReading {

    public String id;
    public String message;

    public MeterDataReading() {}

    public MeterDataReading(String id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public String toString() {
        return "MeterDataReading [id=" + id + ", message=" + message + "]";
    }

    
    
}
