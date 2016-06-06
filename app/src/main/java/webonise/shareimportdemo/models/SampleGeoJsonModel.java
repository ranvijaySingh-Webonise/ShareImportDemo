package webonise.shareimportdemo.models;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class SampleGeoJsonModel {

    private int altitude;
    private List<LatLng> areaOfInterest;
    private String locationName;

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public List<LatLng> getAreaOfInterest() {
        return areaOfInterest;
    }

    public void setAreaOfInterest(List<LatLng> areaOfInterest) {
        this.areaOfInterest = areaOfInterest;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
