package com.codedecode.foodcatalogue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codedecode.foodcatalogue.dto.FoodCataloguePage;
import com.codedecode.foodcatalogue.dto.FoodItemDTO;
import com.codedecode.foodcatalogue.dto.Restaurant;
import com.codedecode.foodcatalogue.entity.FoodItem;
import com.codedecode.foodcatalogue.mapper.FoodItemMapper;
import com.codedecode.foodcatalogue.repository.FoodItemRepo;

@Service
public class FoodCatalogueService {

    @Autowired
    FoodItemRepo foodItemRepo;

    @Autowired
    RestTemplate restTemplate;

    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {

        FoodItem foodItemSavedInDB = foodItemRepo.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return  FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDto(foodItemSavedInDB);

    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer restaurantId) {

        FoodCataloguePage foodCataloguePage = new FoodCataloguePage();

        foodCataloguePage.setRestaurant(fetchRestaurantDetailsFromRestaurantMS(restaurantId));
        foodCataloguePage.setFoodItemsList(fetchFoodItemList(restaurantId));

        return foodCataloguePage;
        

    }

    public Restaurant fetchRestaurantDetailsFromRestaurantMS(Integer restaurantId){

        return restTemplate.getForObject("http://RESTAURANT-SERVICE/restaurant/fetchById/" + restaurantId , Restaurant.class);
        
    }

    public List<FoodItem> fetchFoodItemList(Integer restaurantId){

        return foodItemRepo.findByRestaurantId(restaurantId);

    }
    
}
