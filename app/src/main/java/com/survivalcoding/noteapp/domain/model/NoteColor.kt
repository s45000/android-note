package com.survivalcoding.noteapp.domain.model

enum class NoteColor(val rgb: Long) {
    RedOrange(0xffffab91),
    RedPink(0xfff48fb1),
    BabyBlue(0xff81deea),
    Violet(0xffcf94da),
    LightGreen(0xffe7ed9b);

    companion object {
        fun fromLong(rgb: Long): NoteColor {
            return NoteColor.values().firstOrNull { it.rgb == rgb } ?: NoteColor.BabyBlue
        }
    }


}