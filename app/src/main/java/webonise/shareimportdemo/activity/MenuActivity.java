package webonise.shareimportdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import webonise.shareimportdemo.utils.PermissionUtil;
import webonise.shareimportdemo.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        askForPermission();
    }

    /**
     * Function to ask for write to file permission or storage permission
     */
    private void askForPermission() {
        new PermissionUtil(this).checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new
                PermissionUtil.OnPermissionGranted() {
                    @Override
                    public void permissionGranted() {
                        Toast.makeText(MenuActivity.this, "Permission Granted! Now you can write " +
                                "to file", Toast.LENGTH_SHORT).show();
                    }
                }, "Please give storage permission");
    }

    public void onJsonExampleButton(View view) {
        startActivity(new Intent(this, JsonExampleActivity.class));
    }

    public void onGeoJsonExampleButton(View view) {
        startActivity(new Intent(this, GeoJsonExampleActivity.class));
    }

}
