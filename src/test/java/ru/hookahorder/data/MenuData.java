package ru.hookahorder.data;

public class MenuData {

    public final String categoryName;
    public final String positionName;
    public final int price;
    public final String portion;

    public MenuData(String uniqueId) {
        this.categoryName = "Тест меню " + uniqueId;
        this.positionName = "Тест кальян " + uniqueId;
        this.price = 1000;
        this.portion = "50";
    }
}