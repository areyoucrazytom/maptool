package scpfoundation.per.maptools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

import scpfoundation.per.maptools.example.ExampleLocation;
import scpfoundation.per.maptools.locationcombie.CombinedPoint;
import scpfoundation.per.maptools.locationcombie.ScanLine;

public class MainTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        ExampleLocation[] exampleArray = ExampleLocation.randomBuild(10);
        for (ExampleLocation item : exampleArray) {
            Log.d("BEFORE::",String.format("%.2f", item.getLng()) + " " + String.format("%.2f", item.getLat()));
        }
        Set<CombinedPoint<ExampleLocation>> combined = ScanLine.cluster(exampleArray);
        for (CombinedPoint item : combined) {
            Log.d("AFTER::",String.format("%.2f", item.getLng()) + " " + String.format("%.2f", item.getLat())+ " "+item.getSize());
        }
    }
}
