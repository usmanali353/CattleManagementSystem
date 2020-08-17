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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumberOfTeeth() {
        return numberOfTeeth;
    }

    public void setNumberOfTeeth(String numberOfTeeth) {
        this.numberOfTeeth = numberOfTeeth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
    String price;
    String numberOfTeeth;
    String age;
    String quantity;
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

    public Animal(String name, String price, String numberOfTeeth, String age, String quantity, String gender, String weight, Blob image,String sellerId,String sellerAccountNo) {
        this.name = name;
        this.price = price;
        this.numberOfTeeth = numberOfTeeth;
        this.age = age;
        this.quantity = quantity;
        this.gender = gender;
        this.weight = weight;
        this.image = image;
        this.sellerId=sellerId;
        this.sellerAccountNo=sellerAccountNo;
    }
}
