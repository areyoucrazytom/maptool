package scpfoundation.per.maptools.locationcombie;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinedPoint<T extends LocationComparable> implements LocationComparable{
    private Set<T> mCPList;
    private double mLatitude;
    private double mLongitude;
    private double mLeftMargin;
    private double mRightMargin;
    private double mTopMargin;
    private double mBottomMargin;
    public CombinedPoint(){
        mCPList = new HashSet<T>();
    }
    public int getSize(){
        return mCPList.size();
    }
    public Set<T> getAll(){
        return mCPList;
    }
    public void addPoint(T point){
        if (getSize() == 0) {
            mLatitude = point.getLat();
            mLongitude = point.getLng();
            mLeftMargin = mLongitude;
            mRightMargin = mLongitude;
            mTopMargin = mLatitude;
            mBottomMargin = mLongitude;
            mCPList.add(point);
        } else {
            mLeftMargin = point.getLng() < mLeftMargin ? point.getLng() :mLeftMargin;
            mRightMargin = point.getLng() > mRightMargin ? point.getLng() :mRightMargin;
            mTopMargin = point.getLat() < mTopMargin ? point.getLat() :mTopMargin;
            mBottomMargin = point.getLat() > mBottomMargin ? point.getLat() :mBottomMargin;
            mLatitude = (mLeftMargin + mRightMargin) / 2;
            mLongitude = (mTopMargin + mBottomMargin) / 2;
            mCPList.add(point);
        }
    }
    public boolean isOverRangeIfCombine(CombinedPoint acdp) {
        return acdp.mRightMargin - mLeftMargin > Constant.MAX_LANDSCAPE
                || acdp.mBottomMargin - mTopMargin > Constant.MAX_PORTRAIT;
    }
    public boolean isOverRangeIfAdd(LocationComparable acdp) {
        return acdp.getLng() - mLeftMargin >  Constant.MAX_LANDSCAPE
                || mRightMargin - acdp.getLng() >  Constant.MAX_LANDSCAPE
                || acdp.getLat() - mTopMargin >  Constant.MAX_PORTRAIT
                || mBottomMargin - acdp.getLat() >  Constant.MAX_PORTRAIT;
    }
    public void combineAnother(CombinedPoint cdp) {
        mCPList.addAll(cdp.mCPList);
    }
    @Override
    public int compareTo(@NonNull Object o) {
        T a = (T)o;
        if (getLat() > a.getLat()) {
            return 1 ;
        } else if (getLat() < a.getLat()) {
            return -1;
        }
        return 0;
    }

    @Override
    public double getLat() {
        return mLatitude;
    }

    @Override
    public double getLng() {
        return mLongitude;
    }
}
