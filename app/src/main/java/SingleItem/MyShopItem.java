package SingleItem;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

public class MyShopItem extends ShopItem {
    private String strPosition = "";
    private int intnOrders;
    private boolean isActive = false;

    /**
     * Standard constructor + operating Hours
     *
     * @param intID
     * @param strShopName
     * @param strPosition
     * @param intLogoSmall
     * @param intLogoBig
     * @param strShortDescript
     * @param strFullDescript
     * @param llLocation
     * @param strAveTime
     * @param intRating
     * @param strOperatingHRS
     */
    public MyShopItem(int intID, String strShopName, String strPosition, String intLogoSmall, String intLogoBig,
                      String strShortDescript, String strFullDescript, LatLng llLocation, String strAddress, String strAveTime,
                      int intRating, String strOperatingHRS, boolean isActive, int intStatus, Drawable draStatus,
                      int intnOrders) {
        this.intID = intID;
        this.strShopName = strShopName;
        this.strPosition = strPosition;
        this.strLogoSmall = intLogoSmall;
        this.strLogoBig = intLogoBig;
        this.strShortDescript = strShortDescript;
        this.strFullDescript = strFullDescript;
        this.llLocation = llLocation;
        this.strAddress = strAddress;
        this.strAveTime = strAveTime;
        this.intRating = intRating;
        this.strOperatingHRS = strOperatingHRS;
        this.isActive = isActive;
        this.intStatus = intStatus;
        this.draStatus = draStatus;
        this.intnOrders = intnOrders;
    }

    /**
     * Shop Registration constructor
     *
     * @param strShopName
     * @param draLogoBig
     * @param draLogoSmall
     * @param strShortDescript
     * @param strFullDescript
     * @param llLocation
     */
    public MyShopItem(String strShopName, String strShortDescript, String strFullDescript, Drawable draLogoSmall,
                      Drawable draLogoBig, LatLng llLocation, String strAddress) {
        this.strShopName = strShopName;
        this.draLogoBig = draLogoBig;
        this.draLogoSmall = draLogoSmall;
        this.strShortDescript = strShortDescript;
        this.strFullDescript = strFullDescript;
        this.llLocation = llLocation;
        this.strAddress = strAddress;
    }

    public String getStrPosition() {
        return strPosition;
    }

    public void setStrPosition(String strPosition) {
        this.strPosition = strPosition;
    }

    public boolean isActive() {
        return !isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getIntnOrders() {
        return intnOrders;
    }

    public void setIntnOrders(int intnOrders) {
        this.intnOrders = intnOrders;
    }
}