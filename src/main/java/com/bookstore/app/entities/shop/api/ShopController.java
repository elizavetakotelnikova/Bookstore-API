package com.bookstore.app.entities.shop.api;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.api.responses.IDResponse;
import com.bookstore.app.entities.shop.api.responses.ShopDetailsResponse;
import com.bookstore.app.entities.shop.dto.CreateShopDTO;
import com.bookstore.app.entities.shop.dto.UpdateShopDTO;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import com.bookstore.app.entities.shop.persistance.FindCriteria;
import com.bookstore.app.entities.shop.usecases.*;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ShopController {
    private final CreateShopUseCase createShopUseCase;
    private final FindShopByIdUseCase findShopByIdUseCase;
    private final FindShopsByCriteriaUseCase findShopsByCriteriaUseCase;
    private final UpdateShopUseCase updateShopUseCase;
    private final DeleteShopUsesCase deleteShopUseCase;
    @PostMapping("/shops")
    public IDResponse createShop(@RequestBody CreateShopDTO providedShop) {
        var command = new com.bookstore.app.entities.shop.usecases.DTOs.CreateShopDTO(providedShop.getAddress());
        Shop shop;
        try {
            shop = createShopUseCase.handle(command);
        } catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
        return new IDResponse(shop.getId());
    }

    @GetMapping("/shop/{shopId}")
    public ShopDetailsResponse getUserById(@PathVariable("shopId") UUID shopId) {
        var shop = findShopByIdUseCase.handle(shopId);
        return new ShopDetailsResponse(shop.getId(), shop.getAddress());
    }
    @GetMapping("/shops/")
    public List<ShopDetailsResponse> getShopByCriteria(@Param("city") String city) {
        var criteria = new FindCriteria();
        if (city != null) criteria.setCity(city);
        var shops = findShopsByCriteriaUseCase.handle(criteria);
        var shopMapper = new ShopMapper();
        List<ShopDetailsResponse> listOfShops = new ArrayList<>();
        for (Shop each: shops) {
            listOfShops.add(shopMapper.MapShopToViewModel(each));
        }
        return listOfShops;
    }

    @PutMapping("/shop/{shopId}")
    public IDResponse updateShop(@PathVariable("shopId") UUID id, @RequestBody UpdateShopDTO providedShop)  {
        var command = new com.bookstore.app.entities.shop.usecases.DTOs.UpdateShopDTO(id, providedShop.getAddress());
        Shop shop = null;
        try {
            shop = updateShopUseCase.handle(command);
        } catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
        return new IDResponse(shop.getId());
    }

    @DeleteMapping("/shop/{shopId}")
    public void deleteShop(@PathVariable("shopId") UUID id) {
        deleteShopUseCase.handle(id);
    }

}
