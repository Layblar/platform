package at.fhv.layblar.domain;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Device extends PanacheEntityBase {

    @Id
    public String deviceId;
    public String deviceName;
    public String deviceDescription;
    public String manufacturer;
    public String modelNumber;
    public Integer powerDraw;
    public String energyEfficiencyRating;
    public Float weight;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<DeviceCategory> deviceCategory;

    public Device(){}

}
