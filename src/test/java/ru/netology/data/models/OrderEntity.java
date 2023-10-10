package ru.netology.data.models;

import lombok.*;

@Data
@NoArgsConstructor
public class OrderEntity {
    String credit_id;
    String payment_id;
    String id;
    String created;
}