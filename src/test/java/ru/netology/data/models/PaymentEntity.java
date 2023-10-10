package ru.netology.data.models;

import lombok.*;

@Data
@NoArgsConstructor
public class PaymentEntity {
    String id;
    int amount;
    String created;
    String status;
    String transaction_id;
}