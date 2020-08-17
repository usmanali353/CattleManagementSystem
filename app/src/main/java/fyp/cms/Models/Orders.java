package fyp.cms.Models;
import java.util.ArrayList;


public class Orders {
    ArrayList<String> sellerId;
    String customerId,status,location,date;
    String customerName,customerEmail,customerPhone;
    double deliveryLatitude,deliveryLongitude;
    ArrayList<cartItems> cartItems;
    public Orders(){

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    int totalPrice;
    public ArrayList<String> getSellerId() {
        return sellerId;
    }

    public void setSellerId(ArrayList<String> sellerId) {
        this.sellerId = sellerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public double getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(double deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public double getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(double deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    public ArrayList<fyp.cms.Models.cartItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<fyp.cms.Models.cartItems> cartItems) {
        this.cartItems = cartItems;
    }

    public Orders(ArrayList<String> sellerId, String customerId, String status, String location, String customerName, String customerEmail, String customerPhone, double deliveryLatitude, double deliveryLongitude, ArrayList<fyp.cms.Models.cartItems> cartItems,String date,int totalPrice) {
        this.sellerId = sellerId;
        this.customerId = customerId;
        this.status = status;
        this.location = location;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.deliveryLatitude = deliveryLatitude;
        this.deliveryLongitude = deliveryLongitude;
        this.cartItems = cartItems;
        this.totalPrice=totalPrice;
        this.date=date;
    }
}
