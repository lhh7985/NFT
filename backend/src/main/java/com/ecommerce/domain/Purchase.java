package com.ecommerce.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "purchases")
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long purchaseId; // purchase Id get from contract
    private String state = PurchaseState.I.toString(); // I(Initial-purchased), P(Paid), S(sent), C(confirmed), X(cancelled)
    private long sellerId;
    private long buyerId;
    private long itemId;
    private LocalDateTime createdAt;
    private String contractAddress;

    @Override
    public String toString()
    {
        return "{ id: " + id +
                "\n\tpurchaseId: " + purchaseId +
                "\n\tstate: " + state +
                "\n\tsellerId: " + sellerId +
                "\n\tbuyerId: " + buyerId +
                "\n\titemId: " + itemId +
                "\n\tcreatedAt: " + createdAt +
                "\n\tcontractAddress: " + contractAddress +
                " }";
    }
}
