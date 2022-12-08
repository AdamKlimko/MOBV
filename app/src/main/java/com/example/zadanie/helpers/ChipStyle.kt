package com.example.zadanie.helpers

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.example.zadanie.R

class ChipStyle(
    val text: String,
    val iconDrawable: Drawable?,
    val backgroundColor: ColorStateList
)

object ChipStyleUtil {
    fun getChipStyle(context: Context, type: String): ChipStyle {
        return when(type) {
            "fast_food" -> {
                ChipStyle("fast food",
                    ResourcesCompat.getDrawable(
                        context.resources , R.drawable.ic_baseline_fastfood_24, null
                    ),
                    ColorStateList.valueOf(ResourcesCompat.getColor(
                        context.resources, R.color.color_fast_food,null)))
            }
            "bar" -> {
                ChipStyle("bar",
                    ResourcesCompat.getDrawable(
                        context.resources , R.drawable.ic_baseline_local_bar_24, null
                    ),
                    ColorStateList.valueOf(ResourcesCompat.getColor(
                        context.resources, R.color.color_bar,null)))
            }
            "pub" -> {
                ChipStyle("pub",
                    ResourcesCompat.getDrawable(
                        context.resources , R.drawable.ic_baseline_sports_bar_24, null
                    ),
                    ColorStateList.valueOf(ResourcesCompat.getColor(
                        context.resources, R.color.color_pub,null)))
            }
            "cafe" -> {
                ChipStyle("cafe",
                    ResourcesCompat.getDrawable(
                        context.resources , R.drawable.ic_baseline_coffee_24, null
                    ),
                    ColorStateList.valueOf(ResourcesCompat.getColor(
                        context.resources, R.color.color_cafe,null)))
            }
            "restaurant" -> {
                ChipStyle("restaurant",
                    ResourcesCompat.getDrawable(
                        context.resources , R.drawable.ic_baseline_restaurant_24, null
                    ),
                    ColorStateList.valueOf(ResourcesCompat.getColor(
                        context.resources, R.color.color_restaurant,null)))
            }
            else -> {
                return ChipStyle("bar",
                    ResourcesCompat.getDrawable(
                        context.resources , R.drawable.ic_baseline_local_bar_24, null
                    ),
                    ColorStateList.valueOf(ResourcesCompat.getColor(
                        context.resources, R.color.color_bar,null)))
            }
        }
    }
}