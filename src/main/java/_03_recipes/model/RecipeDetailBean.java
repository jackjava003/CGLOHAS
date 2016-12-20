package _03_recipes.model;

import java.util.List;

import _04_listItems.model.item_bean;

/**
 * Created by JACK on 2016/10/17.
 */

public class RecipeDetailBean {
    private List<recipes_itemBean> recipeItems;
    private List<processBean> process;
    private List<item_bean> recipeName;

    public RecipeDetailBean(){};
    public RecipeDetailBean(List<recipes_itemBean> rib, List<processBean> pb,List<item_bean> recipeName) {
        this.recipeItems = rib;
        this.process = pb;
        this.recipeName=recipeName;
    }

    public List<item_bean> getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(List<item_bean> recipeName) {
        this.recipeName = recipeName;
    }
    
    public List<recipes_itemBean> getRecipeItems() {
        return recipeItems;
    }

    public void setRecipeItems(List<recipes_itemBean> recipeItems) {
        this.recipeItems = recipeItems;
    }
    
    public List<processBean> getProcess() {
        return process;
    }

    public void setProcess(List<processBean> process) {
        this.process = process;
    }
}
