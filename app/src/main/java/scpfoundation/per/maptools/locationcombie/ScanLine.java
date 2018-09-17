package scpfoundation.per.maptools.locationcombie;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScanLine {

    public static <T extends LocationComparable> Set<CombinedPoint<T>> cluster(T[] latSortedArray) {
        HashSet<CombinedPoint<T>> ret = new HashSet<>();
        HashMap<T,CombinedPoint> combinedMap = new HashMap<>();
        List<T> inLatMarginList = new ArrayList<T>();
        CombinedPoint<T> citem;
        for (int i = 0;i < latSortedArray.length; i++) {
            inLatMarginList.clear();
            LocationComparable item = latSortedArray[i];
            LocationComparable bottomMarginLocation = buildSupposed(item.getLng(),item.getLat() + Constant.MARGIN_PORTRAIT);
            int marginLowBound = marginSearch(latSortedArray,i,latSortedArray.length,bottomMarginLocation);
            if (marginLowBound > i) {
                citem = combinedMap.get(item);
                if (citem == null) {
                    citem = new CombinedPoint();
                }
                boolean inRange = false;
                for (int j = i + 1;j < marginLowBound;j++) {
                    LocationComparable<T> jItem = latSortedArray[j];
                    inLatMarginList.add(latSortedArray[j]);
                    double lngDistance = latSortedArray[j].getLng() - item.getLng();
                    if (lngDistance < 0) {
                        lngDistance = -lngDistance;
                    }
                    if (lngDistance < Constant.MARGIN_LANDSCAPE) {
                        //In range
                        inRange = true;
                        if (!citem.isOverRangeIfAdd(jItem)){
                            CombinedPoint<T> jItemCombiedPoint = combinedMap.get(jItem);
                            if (jItemCombiedPoint != null && jItemCombiedPoint != citem) {
                                if (!citem.isOverRangeIfCombine(jItemCombiedPoint)) {
                                    //Todo point robbery logic
                                    citem.combineAnother(jItemCombiedPoint);
                                    ret.remove(jItemCombiedPoint);
                                }
                            } else {
                                citem.addPoint(latSortedArray[i]);
                            }
                            combinedMap.put(latSortedArray[j],citem);
                        } else {
                            //Todo nothing?
                        }
                    }
                }
                citem.addPoint(latSortedArray[i]);
                ret.add(citem);
            } else {
                //isolated
                citem = new CombinedPoint();
                citem.addPoint(latSortedArray[i]);
                ret.add(citem);
            }
        }
        return ret;
    }
    private static int marginSearch(LocationComparable[] a, int fromIndex, int toIndex, LocationComparable key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            LocationComparable midVal = a[mid];
            int cmp = midVal.compareTo(key);
            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
        }
        return low;
    }
    private static SupposedLocationComparable buildSupposed(double lng,double lat) {
        return new SupposedLocationComparable(lng,lat);
    }
    private static class SupposedLocationComparable implements LocationComparable{
        private double mLatitude;
        private double mLongitude;
        public SupposedLocationComparable(double lng,double lat) {
            mLatitude = lat;
            mLongitude = lng;
        }
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
            LocationComparable target = (LocationComparable)o;
            if (getLat() > target.getLat()) {
                return 1 ;
            } else if (getLat() < target.getLat()) {
                return -1;
            }
            return 0;
        }
    }
}
