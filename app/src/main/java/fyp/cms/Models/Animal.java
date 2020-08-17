package fyp.cms.Models;

import com.google.firebase.firestore.Blob;

import java.util.List;

public class Animal {
    public String getSellerAccountNo() {
        return sellerAccountNo;
    }

    public void setSellerAccountNo(String sellerAccountNo) {
        this.sellerAccountNo = sellerAccountNo;
    }

    String sellerAccountNo;

    public Animal(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    String name;
    int price;
    int age;
    int quantity;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    String gender;
    String weight;
    Blob image;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    String sellerId;

    public Animal(String name, int price, int age, int quantity, String gender, String weight, Blob image,String sellerId,String sellerAccountNo) {
        this.name = name;
        this.price = price;
        this.age = age;
        this.quantity = quantity;
        this.gender = gender;
        this.weight = weight;
        this.image = image;
        this.sellerId=sellerId;
        this.sellerAccountNo=sellerAccountNo;
    }
}
