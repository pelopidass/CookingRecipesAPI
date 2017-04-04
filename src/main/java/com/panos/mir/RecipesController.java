package com.panos.mir;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Panos on 3/21/2017.
 */

//// TODO: 4/2/2017 Add @Body of Recipes because it contains user list, so ask for id and user_id and insert them to favorites

@Controller
@RequestMapping(path = "/recipes")
public class RecipesController {

    @Autowired
    private RecipesRepository repo;

    @GetMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<Map<String, Iterable<Recipes>>> getAllRecipes(){
        List<Recipes> recipes = (List<Recipes>) repo.findAll();
        Map result = new HashMap();
        result.put(RecipeDTO.class.getAnnotation(CustomJsonRootName.class).plural(), recipes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/all/{id}")
    public @ResponseBody ResponseEntity<Map<String, Recipes>> findRecipe(@PathVariable ("id") int id){
        List<Recipes> recipes = repo.findById(id);
        Map result = new HashMap();
        result.put(RecipeDTO.class.getAnnotation(CustomJsonRootName.class).plural(), recipes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/all/userId/{id}")
    public @ResponseBody ResponseEntity<Map<String, Recipes>> findUserRecipe(@PathVariable ("id") int id){
        List<Recipes> recipes = repo.findAllByUserUser_id(id);
        Map result = new HashMap();
        result.put(RecipeDTO.class.getAnnotation(CustomJsonRootName.class).plural(), recipes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(path = "/all/userId/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipes> createRecipe(@RequestBody Recipes recipes){
        Recipes saved = repo.save(recipes);
        return new ResponseEntity<Recipes>(saved, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all/findbyTitle/{title}")
    public @ResponseBody ResponseEntity<Map<String, Iterable<Recipes>>> getRecipesByTitle(@PathVariable("title") String title){
        List<Recipes> recipes = repo.findAllByTitleIsLike("%" + title + "%");
        Map result = new HashMap();
        result.put(RecipeDTO.class.getAnnotation(CustomJsonRootName.class).plural(), recipes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(path = "/all/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipes> create(@RequestBody Recipes recipes){
        Recipes saved = repo.save(recipes);
        return new ResponseEntity<Recipes>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/all/favorites")
    public @ResponseBody ResponseEntity<Map<String, Iterable<Recipes>>> getFavs(){
        List<Recipes> recipes = repo.findFavorites();
        Map result = new HashMap();
        result.put("recipes", recipes);
        return new ResponseEntity<Map<String, Iterable<Recipes>>>(result, HttpStatus.OK);
    }
//
//    @PostMapping("/all/favorites/add")
//    public @ResponseBody ResponseEntity<Recipes> addFavRecipe(@RequestBody Recipes recipes, @RequestBody Users user){
//        int id = recipes.getId();
//        recipes.setUser(user);
//        int user_id = recipes.getUser().getUser_id();
//        Recipes saved = repo.addFavorites(user_id, id);
//        return new ResponseEntity<Recipes>(saved, HttpStatus.CREATED);
//    }

}
