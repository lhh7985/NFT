package com.ecommerce.domain.item.dto;

import com.ecommerce.domain.item.domain.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDeleteResponse {
    private Long id;
    private String name;
    private String category;
    private String explanation;
    private boolean available;
    private String seller;
    private LocalDateTime registeredAt;
    private String image;

    public static ItemDeleteResponse ofItem(final Item item) {
        final Long id = item.getId();
        final String name = item.getName();
        final String category = item.getCategory();
        final String explanation = item.getExplanation();
        final boolean available = item.isAvailable();
        final String seller = item.getSeller().getName();
        final LocalDateTime registeredAt = item.getRegisteredAt();
        final String image = item.getImage();
        return new ItemDeleteResponse(id, name, category, explanation, available, seller, registeredAt, image);
    }

    public ItemDeleteResponse(final Long id, final String name, final String category, final String explanation,
                              final boolean available, final String seller, final LocalDateTime registeredAt, final String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.explanation = explanation;
        this.available = available;
        this.seller = seller;
        this.registeredAt = registeredAt;
        this.image = image;
    }

}
