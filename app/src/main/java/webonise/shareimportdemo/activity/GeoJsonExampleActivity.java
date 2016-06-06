package webonise.shareimportdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import webonise.shareimportdemo.R;
import webonise.shareimportdemo.models.SampleGeoJsonModel;
import webonise.shareimportdemo.models.SampleJsonModel;
import webonise.shareimportdemo.utils.FileUtil;
import webonise.shareimportdemo.utils.GeoJsonParser;

public class GeoJsonExampleActivity extends AppCompatActivity {

    private static final String TAG = "GeoJsonExampleActivity";
    private static final int PICK_FILE_RESULT_CODE = 2;
    private TextView tvImportedFileContent;
    private TextView tvWriteToFileContent;
    private String mFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_json_example);
        initializeComponents();
    }

    private void initializeComponents() {
        tvImportedFileContent = (TextView) findViewById(R.id.tvImportedFileContent);
        tvWriteToFileContent = (TextView) findViewById(R.id.tvWriteToFileContent);
        tvWriteToFileContent.setText(getContent());
    }

    /**
     * Function is called onclick of write to file button
     *
     * @param view View
     */
    public void onClickWriteToFileButton(View view) {
        FileUtil fileUtil = new FileUtil(this);
        String content = getContent();
        Toast.makeText(GeoJsonExampleActivity.this, "Successfully! Converted Sample model to json",
                Toast.LENGTH_SHORT).show();
        Log.i(TAG, content);
        mFilePath = fileUtil.writeToFile(content);

    }

    /**
     * Function to get the content that need to be written into file
     *
     * @return String
     */
    private String getContent() {
        return new GeoJsonParser().toGeoJson(getSampleGeoJsonModel());
    }

    /**
     * Function to get sample model
     *
     * @return SampleGeoJsonModel
     */
    private SampleGeoJsonModel getSampleGeoJsonModel() {
        SampleGeoJsonModel sampleGeoJsonModel = new SampleGeoJsonModel();
        sampleGeoJsonModel.setAltitude(40);
        sampleGeoJsonModel.setLocationName("Bhavdan");
        sampleGeoJsonModel.setAreaOfInterest(getAreaOfInterest());
        return sampleGeoJsonModel;
    }

    /**
     * Function to return string list
     *
     * @return List<String>
     */
    private List<LatLng> getAreaOfInterest() {
        List<LatLng> latLngPolygon = new ArrayList<>();
        latLngPolygon.add(new LatLng(28.6139, 77.2090));//delhi
        latLngPolygon.add(new LatLng(22.2587, 71.1924));//gujarat
        latLngPolygon.add(new LatLng(18.5204, 73.8567));//pune
        latLngPolygon.add(new LatLng(12.9716, 77.5946));//banglore
        latLngPolygon.add(new LatLng(25.5941, 85.1376));//patna
        latLngPolygon.add(new LatLng(28.6139, 77.2090));//delhi
        return latLngPolygon;
    }

    /**
     * Function is called onclick of share file button
     *
     * @param view View
     */
    public void onClickShareFileButton(View view) {
        if (TextUtils.isEmpty(mFilePath)) {
            Toast.makeText(GeoJsonExampleActivity.this, "First you need to click on 'Write to file' button",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file = new File(mFilePath);
        if (file.exists()) {
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + mFilePath));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing ...");
            intent.putExtra(Intent.EXTRA_TEXT, "Sharing File at location : " + mFilePath);
            startActivity(Intent.createChooser(intent, "Share file"));
        }
    }

    /**
     * Function is called onclick of import file button
     *
     * @param view View
     */
    public void onClickImportFileButton(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //For reading plain text file use : "text/plain"
        //For reading any type of file use : "*/*"
        intent.setType("text/plain");
        startActivityForResult(intent, PICK_FILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_RESULT_CODE) {
            if (data != null) {
                Uri uri = data.getData();
                FileUtil fileUtil = new FileUtil(this);
                String fileContent = fileUtil.readFile(uri);
                if (TextUtils.isEmpty(fileContent)) {
                    Toast.makeText(this, "Unable to read file content", Toast.LENGTH_SHORT).show();
                    return;
                }
                setFileContent(fileContent);
            }
        }
    }

    /**
     * Function to set file content into a model
     *
     * @param fileContent String
     */
    private void setFileContent(String fileContent) {
        tvImportedFileContent.setText(fileContent);
        try {
            SampleJsonModel sampleJsonModel = new Gson().fromJson(fileContent, SampleJsonModel.class);
            Toast.makeText(GeoJsonExampleActivity.this, "Successfully parsed file content into sample model",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(GeoJsonExampleActivity.this, "Unable to parsed file content into sample model",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
