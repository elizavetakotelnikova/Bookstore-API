package com.bookstore.app.entities.shop.api;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.api.responses.ShopIDResponse;
import com.bookstore.app.entities.shop.api.responses.ShopJsonResponse;
import com.bookstore.app.entities.shop.api.viewModels.CreateShopViewModel;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import com.bookstore.app.entities.shop.persistance.FindCriteria;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopController {
    private IShopsRepository shopsRepository;

    @PostMapping("/shops")
    public ShopIDResponse createShop(@RequestBody CreateShopViewModel providedShop) {
        var shopMapper = new ShopMapper();
        var shop = shopMapper.MapShopViewModelToShop(providedShop);
        shop = shopsRepository.save(shop);
        return new ShopIDResponse(shop.getId());
    }

    @GetMapping("/shop/{shopId}")
    public ShopJsonResponse getUserById(@PathVariable("shopId") UUID shopId) {
        var shopMapper = new ShopMapper();
        return shopMapper.MapShopToViewModel(shopsRepository.getShopById(shopId));
    }
    @GetMapping("/shops/")
    public List<ShopJsonResponse> getShopByCriteria(@Param("city") String city) {
        var criteria = new FindCriteria();
        if (city != null) criteria.setCity(city);
        var shopMapper = new ShopMapper();
        List<ShopJsonResponse> listOfShops = new ArrayList<>();
        List<Shop> shops = shopsRepository.findShopsByCriteria(criteria);
        for (Shop each: shops) {
            listOfShops.add(shopMapper.MapShopToViewModel(each));
        }
        return listOfShops;
    }

    @PutMapping("/shop/{shopId}")
    public ShopIDResponse updateUser(@PathVariable("shopId") UUID id, @RequestBody CreateShopViewModel providedShop) {
        var shopMapper = new ShopMapper();
        var shop = shopMapper.MapShopViewModelToShop(providedShop);
        shop = shopsRepository.update(shop);
        return new ShopIDResponse(shop.getId());
    }

    @DeleteMapping("/shop/{shopId}")
    public void updateUser(@PathVariable("shopId") UUID id) {
        shopsRepository.deleteShopById(id);
    }

}
