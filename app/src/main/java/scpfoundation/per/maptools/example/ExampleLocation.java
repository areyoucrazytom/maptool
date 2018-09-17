package scpfoundation.per.maptools.example;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Random;

import scpfoundation.per.maptools.R;
import scpfoundation.per.maptools.locationcombie.LocationComparable;

public class ExampleLocation implements LocationComparable{
    private static final double RANGE = 1000;
    private double mLatitude;
    private double mLongitude;
    @Override
    public double getLat() {
        return mLatitude;
    }

    @Override
    public double getLng() {
        return mLongitude;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        LocationComparable a = (LocationComparable)o;
        if (getLat() > a.getLat()) {
            return 1 ;
        } else if (getLat() < a.getLat()) {
            return -1;
        }
        return 0;
    }
    public static ExampleLocation[] randomBuild(int count) {
        ExampleLocation[] ret = new ExampleLocation[count];
        Random ran = new Random();
        for (int i = 0;i < count; i++) {
            ExampleLocation item = new ExampleLocation();
            item.mLatitude = ran.nextDouble() * RANGE;
            item.mLongitude = ran.nextDouble() * RANGE;
            ret[i] = item;
        }
        Arrays.sort(ret,null);
        return ret;
    }
}

