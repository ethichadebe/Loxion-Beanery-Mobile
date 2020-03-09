package SingleItem;

public class UpcomingOrderItem {
    private int intID;
    private String strShopName;
    private int intOrderNum;
    private String strTime;
    private String strMenu;
    private Double dblPrice;

    public UpcomingOrderItem(int intID, String strShopName, int intOrderNum, String strTime, String strMenu, Double dblPrice) {
        this.intID = intID;
        this.strShopName = strShopName;
        this.intOrderNum = intOrderNum;
        this.strTime = strTime;
        this.strMenu = strMenu;
        this.dblPrice = dblPrice;
    }

    public int getIntID() {
        return intID;
    }

    public void setIntID(int intID) {
        this.intID = intID;
    }

    public String getStrShopName() {
        return strShopName;
    }

    public void setStrShopName(String strShopName) {
        this.strShopName = strShopName;
    }

    public int getIntOrderNum() {
        return intOrderNum;
    }

    public void setIntOrderNum(int intOrderNum) {
        this.intOrderNum = intOrderNum;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getStrMenu() {
        return strMenu;
    }

    public void setStrMenu(String strMenu) {
        this.strMenu = strMenu;
    }

    public Double getDblPrice() {
        return dblPrice;
    }

    public void setDblPrice(Double dblPrice) {
        this.dblPrice = dblPrice;
    }
}