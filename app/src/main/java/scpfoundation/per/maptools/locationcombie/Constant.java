package scpfoundation.per.maptools.locationcombie;

public class Constant {
    /*About overclose distance:
    * You should confirm a small enough margin value if you want to use these codes in GPS locations.
    * In Gaode Map,the max zoom ratios is 18, thus it means users can't make out points while distance should be at least 0.005 (approximately equal to 10 to 15m)
    * If you want to test this value ,maybe you need build a map api environment for precision.
     * */
    public static final double MARGIN_LANDSCAPE = 100;
    public static final double MARGIN_PORTRAIT  = MARGIN_LANDSCAPE;
    public static final double MAX_LANDSCAPE = 600;
    public static final double MAX_PORTRAIT  = MAX_LANDSCAPE;
}
