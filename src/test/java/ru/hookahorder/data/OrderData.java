package ru.hookahorder.data;

public class OrderData {

    public final String orderPhone;
    public final String commentText;

    public OrderData(String uniqueId) {
        this.orderPhone = "+79991112233";
        this.commentText = "Тест комментария " + uniqueId;
    }
}
