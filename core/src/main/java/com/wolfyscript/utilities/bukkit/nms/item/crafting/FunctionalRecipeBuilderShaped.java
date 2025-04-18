package com.wolfyscript.utilities.bukkit.nms.item.crafting;

import me.wolfyscript.utilities.api.WolfyUtilCore;
import me.wolfyscript.utilities.util.NamespacedKey;
import me.wolfyscript.utilities.util.version.MinecraftVersion;
import me.wolfyscript.utilities.util.version.ServerVersion;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FunctionalRecipeBuilderShaped extends FunctionalRecipeBuilderCrafting {

    private List<RecipeChoice> choices = new ArrayList<>();
    private final int width;
    private final int height;

    public FunctionalRecipeBuilderShaped(NamespacedKey key, ItemStack result, int width, int height) {
        super(key, result);
        this.width = width;
        this.height = height;
    }

    public void setChoices(List<RecipeChoice> choices) {
        this.choices = choices == null ? new ArrayList<>() : choices;
    }

    public List<RecipeChoice> getChoices() {
        return choices;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    protected FunctionalRecipeType getType() {
        return FunctionalRecipeType.CRAFTING_SHAPED;
    }

    @Override
    public void createAndRegister() {
        try {
            Constructor<?> constructor;
            FunctionalRecipe<CraftingInventory> recipe;
            FunctionalRecipeGenerator functionalRecipeGenerator = WolfyUtilCore.getInstance().getFunctionalRecipeGenerator();
            if (ServerVersion.isAfterOrEq(MinecraftVersion.of(1, 19, 3))) {
                constructor =  functionalRecipeGenerator.getFunctionalRecipeClass(getType()).getConstructor(
                        NamespacedKey.class, RecipeMatcher.class, RecipeAssembler.class, RecipeRemainingItemsFunction.class, String.class, String.class, Integer.TYPE, Integer.TYPE, List.class, ItemStack.class
                );
                recipe = (FunctionalRecipe<CraftingInventory>) constructor.newInstance(key, recipeMatcher, recipeAssembler, remainingItemsFunction, group, "misc", width, height, choices, result);
            } else {
                constructor = functionalRecipeGenerator.getFunctionalRecipeClass(getType()).getConstructor(
                        NamespacedKey.class, RecipeMatcher.class, RecipeAssembler.class, RecipeRemainingItemsFunction.class, String.class, Integer.TYPE, Integer.TYPE, List.class, ItemStack.class
                );
                recipe = (FunctionalRecipe<CraftingInventory>) constructor.newInstance(key, recipeMatcher, recipeAssembler, remainingItemsFunction, group, width, height, choices, result);
            }
            functionalRecipeGenerator.addRecipeToRecipeManager(recipe);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
