package com.teampotato.potion_level_fix.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator, "en_us");
    }


    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (int i = 6; i < 11; i++) {
            translationBuilder.add("potion.potency." + i, intToRoman(i));
        }
        for (int i = 11; i <= 4000; i++) {
            translationBuilder.add("enchantment.level." + i, intToRoman(i));
            translationBuilder.add("potion.potency." + i, intToRoman(i));
        }
    }

    public String intToRoman(int num) {

        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < nums.length && num >= 0; i++) {
            while (nums[i] <= num) {
                num -= nums[i];
                res.append(roman[i]);
            }
        }
        return res.toString();
    }
}
