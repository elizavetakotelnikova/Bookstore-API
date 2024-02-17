package com.bookstore.app.entities.shop.api;

import com.bookstore.app.entities.shop.Shop;
import com.bookstore.app.entities.shop.api.responses.ShopIDResponse;
import com.bookstore.app.entities.shop.api.responses.ShopJsonResponse;
import com.bookstore.app.entities.shop.api.viewModels.CreateShopViewModel;
import com.bookstore.app.entities.shop.api.viewModels.UpdateShopViewModels;
import com.bookstore.app.entities.shop.persistance.IShopsRepository;
import com.bookstore.app.entities.shop.persistance.FindCriteria;
import com.bookstore.app.entities.shop.usecases.*;
import com.bookstore.app.entities.shop.usecases.DTOs.CreateShopDTO;
import com.bookstore.app.entities.shop.usecases.DTOs.UpdateShopDTO;
import com.bookstore.app.exceptions.IncorrectArgumentsException;
import com.bookstore.app.exceptions.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopController {
    @Autowired
    private IShopsRepository shopsRepository;
    @Autowired
    private CreateShopUseCase createShopUseCase;
    @Autowired
    private FindShopByIdUseCase findShopByIdUseCase;
    @Autowired
    private FindShopsByCriteriaUseCase findShopsByCriteriaUseCase;
    @Autowired
    private UpdateShopUseCase updateShopUseCase;
    @Autowired
    private DeleteShopUsesCase deleteShopUseCase;

    @PostMapping("/shops")
    public ShopIDResponse createShop(@RequestBody CreateShopViewModel providedShop) {
        var command = new CreateShopDTO(providedShop.getAddress());
        Shop shop;
        try {
            shop = createShopUseCase.handle(command);
        } catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
        return new ShopIDResponse(shop.getId());
    }

    @GetMapping("/shop/{shopId}")
    public ShopJsonResponse getUserById(@PathVariable("shopId") UUID shopId) {
        var shop = findShopByIdUseCase.handle(shopId);
        return new ShopJsonResponse(shop.getId(), shop.getAddress());
    }
    @GetMapping("/shops/")
    public List<ShopJsonResponse> getShopByCriteria(@Param("city") String city) {
        var criteria = new FindCriteria();
        if (city != null) criteria.setCity(city);
        var shops = findShopsByCriteriaUseCase.handle(criteria);
        var shopMapper = new ShopMapper();
        List<ShopJsonResponse> listOfShops = new ArrayList<>();
        for (Shop each: shops) {
            listOfShops.add(shopMapper.MapShopToViewModel(each));
        }
        return listOfShops;
    }

    @PutMapping("/shop/{shopId}")
    public ShopIDResponse updateShop(@PathVariable("shopId") UUID id, @RequestBody UpdateShopViewModels providedShop)  {
        var command = new UpdateShopDTO(id, providedShop.getAddress());
        Shop shop = null;
        try {
            shop = updateShopUseCase.handle(command);
        } catch (IncorrectArgumentsException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exc.getMessage(), exc);
        }
        return new ShopIDResponse(shop.getId());
    }

    @DeleteMapping("/shop/{shopId}")
    public void deleteShop(@PathVariable("shopId") UUID id) {
        deleteShopUseCase.handle(id);
    }

}
