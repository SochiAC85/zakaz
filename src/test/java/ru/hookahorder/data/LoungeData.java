package ru.hookahorder.data;

public class LoungeData {

    public final String shopName;
    public final String description;
    public final String phone;
    public final String address;
    public final String ownerPhone;

    public LoungeData(String uniqueId) {
        this.shopName = "SmokeTest Lounge " + uniqueId;
        this.description = "Тестовая Кальянная для автотеста " + uniqueId;
        this.phone = "+7 (999) 123-45-67";
        this.address = "ул. Тверская 10";
        this.ownerPhone = "+79990000000";
    }
}

