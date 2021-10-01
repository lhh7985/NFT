package com.ecommerce.domain.item.api;

import com.ecommerce.domain.item.application.ItemService;
import com.ecommerce.domain.item.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ItemApi {

    private final ItemService itemService;

    @GetMapping("/api/v1/items/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        final Item item = itemService.findById(id);
        return ResponseEntity.ok().body(item);
    }

    @GetMapping("/api/v1/items")
    public ResponseEntity<?> findAll() {
        final List<Item> items = itemService.findAll();
        return ResponseEntity.ok().body(items);
    }

    /*
             id: userinfo.id,
            // 지갑주소,
            name: state.picture.name,
            description: state.picture.description,
            price: state.picture.price,
            cid: state.cid,
     */
    @PostMapping("/api/v1/items")
    public ResponseEntity<?> save(@RequestBody String tempData) {
        final Item item = itemService.save(Item.builder().build());
        return ResponseEntity.ok().body(item);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/items/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

}