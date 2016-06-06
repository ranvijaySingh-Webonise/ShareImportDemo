package webonise.shareimportdemo.utils;

import android.util.Log;

import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.Polygon;
import com.cocoahero.android.geojson.Ring;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import webonise.shareimportdemo.models.SampleGeoJsonModel;

public class GeoJsonParser {
    private static final String TAG = "GeoJsonParser";

    /**
     * Function to convert Geojson model into geojson string
     *
     * @param sampleGeoJsonModel SampleGeoJsonModel
     * @return String
     */
    public String toGeoJson(SampleGeoJsonModel sampleGeoJsonModel) {
        JSONObject jsonObject = null;
        if (sampleGeoJsonModel != null) {
            //Convert list of lat long into double dimension array
            List<LatLng> latLngList = sampleGeoJsonModel.getAreaOfInterest();
            double[][] positions = new double[latLngList.size()][2];
            for (int i = 0; i < latLngList.size(); i++) {
            LatLng latLngPosition = latLngList.get(i);
                positions[i][0] = latLngPosition.getLatitude();
                positions[i][1] = latLngPosition.getLongitude();
            }
            //Create ring object using double dimension array of coordinates
            Ring ring = new Ring(positions);
            //Create polygon using this ring
            Polygon polygon = new Polygon(ring);
            //Create feature from the given polygon
            Feature feature = new Feature(polygon);
            //Create feature collection and add feature to it.
            FeatureCollection featureCollection = new FeatureCollection();
            featureCollection.addFeature(feature);
            jsonObject = null;
            //Convert feature collection into string format.
            try {
                jsonObject = featureCollection.toJSON();
                Log.i(TAG, jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject != null ? jsonObject.toString() : "";
    }
}
