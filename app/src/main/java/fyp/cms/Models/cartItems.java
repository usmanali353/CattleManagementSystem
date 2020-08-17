package fyp.cms.Models;

public class cartItems {
    String name,productid,weight;
    int quantity,price;
     byte[] image;
     String gender;
     String sellerId,sellerAccountNo;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerAccountNo() {
        return sellerAccountNo;
    }

    public void setSellerAccountNo(String sellerAccountNo) {
        this.sellerAccountNo = sellerAccountNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public cartItems(String name, String productid, String weight, byte[] image, int quantity, int price,String gender,String sellerId,String sellerAccountNo) {
        this.name = name;
        this.productid = productid;
        this.weight = weight;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.gender=gender;
        this.sellerId=sellerId;
        this.sellerAccountNo=sellerAccountNo;
    }
    public cartItems(String name, String productid, String weight, int quantity, int price) {
        this.name = name;
        this.productid = productid;
        this.weight = weight;
        this.quantity = quantity;
        this.price = price;
    }
    public cartItems(){

    }
}
