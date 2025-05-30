package com.wolfyscript.utilities.bukkit.nms.item.crafting;

import com.google.common.base.Preconditions;
import me.wolfyscript.utilities.api.WolfyUtilCore;
import me.wolfyscript.utilities.util.NamespacedKey;
import me.wolfyscript.utilities.util.version.MinecraftVersion;
import me.wolfyscript.utilities.util.version.ServerVersion;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public abstract class FunctionalRecipeBuilderCooking extends FunctionalRecipeBuilder<Inventory> {

    private final ItemStack result;
    private final RecipeChoice ingredient;
    private float experience = 1f;
    private int cookingTime = 60;

    public FunctionalRecipeBuilderCooking(NamespacedKey key, ItemStack result, RecipeChoice ingredient) {
        super(key);
        this.result = result;
        this.ingredient = ingredient;
        this.recipeMatcher = null;
        this.recipeAssembler = inventory -> Optional.empty();
        this.remainingItemsFunction = inventory -> Optional.empty();
    }

    public ItemStack getResult() {
        return result;
    }

    public RecipeChoice getIngredient() {
        return ingredient;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        Preconditions.checkArgument(cookingTime <= Short.MAX_VALUE, "The cooking time cannot be longer than 32767 ticks!");
        this.cookingTime = cookingTime;
    }

    protected abstract FunctionalRecipeType getType();

    public void createAndRegister() {
        try {
            FunctionalRecipeGenerator functionalRecipeGenerator = WolfyUtilCore.getInstance().getFunctionalRecipeGenerator();
            Class<?> recipeClass = functionalRecipeGenerator.getFunctionalRecipeClass(getType());
            Constructor<?> constructor;
            FunctionalRecipe<Inventory> recipe;
            if (ServerVersion.isAfterOrEq(MinecraftVersion.of(1, 19, 3))) {
                constructor = recipeClass.getConstructor(
                        NamespacedKey.class, RecipeMatcher.class, RecipeAssembler.class, RecipeRemainingItemsFunction.class, String.class, String.class, RecipeChoice.class, ItemStack.class, Float.TYPE, Integer.TYPE
                );
                recipe = (FunctionalRecipe<Inventory>) constructor.newInstance(key, recipeMatcher, recipeAssembler, remainingItemsFunction, group, "misc", ingredient, result, experience, cookingTime);
            } else {
                constructor = recipeClass.getConstructor(
                        NamespacedKey.class, RecipeMatcher.class, RecipeAssembler.class, RecipeRemainingItemsFunction.class, String.class, RecipeChoice.class, ItemStack.class, Float.TYPE, Integer.TYPE
                );
                recipe = (FunctionalRecipe<Inventory>) constructor.newInstance(key, recipeMatcher, recipeAssembler, remainingItemsFunction, group, ingredient, result, experience, cookingTime);
            }
            functionalRecipeGenerator.addRecipeToRecipeManager(recipe);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
