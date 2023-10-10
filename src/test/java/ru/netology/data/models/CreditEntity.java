package ru.netology.data.models;

import lombok.*;

@Data
@NoArgsConstructor
public class CreditEntity {
    String id;
    String bank_id;
    String created;
    String status;
}