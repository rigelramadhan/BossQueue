package com.rigelramadhan.bossqueue.model

object SampleData {
    var bill = 0.0

    val placeSampleData = arrayListOf<Place>(
        Place("Hause Rooftop", "Malang", "Hause Rooftop adalah sebuah tempat aestetik yang memberikan pengalaman yang menyegarkan", true, ""),
        Place("Tempat Kedua", "Malang", "deskripsi2", true, ""),
        Place("Tempat Ketiga", "Malang", "deskripsi3", true, ""),
        Place("Tempat Keempat", "Malang", "deskripsi4", true, ""),
        Place("Tempat Kelima", "Malang", "deskripsi5", true, ""),
    )

    val categorySampleData = arrayListOf<Category>(
        Category(1, "Foods", ""),
        Category(2, "Drinks", "")
    )

    val activitySampleData = arrayListOf<Activity>(
        Activity(1, 1, 1, 2, true),
        Activity(2, 1, 1, 3, true),
        Activity(3, 1, 2, 4, true),
        Activity(4, 1, 2, 5, false),
        Activity(5, 1, 3, 7, false),
    )

    val foodSampleData = arrayListOf<Food>(
        Food("Food1", "Ini adalah deskripsi food1", 25600.0, "", 1, "1"),
        Food("Food2", "Ini adalah deskripsi food2", 15600.0, "", 1, "1"),
        Food("Food3", "Ini adalah deskripsi food3", 35600.0, "", 2, "1"),
        Food("Food4", "Ini adalah deskripsi food4", 10600.0, "", 1, "2"),
        Food("Food5", "Ini adalah deskripsi food5", 20600.0, "", 2, "2"),
        Food("Food6", "Ini adalah deskripsi food6", 30600.0, "", 2, "2"),
        Food("Food7", "Ini adalah deskripsi food7", 28600.0, "", 2, "3"),
        Food("Food8", "Ini adalah deskripsi food8", 18600.0, "", 1, "3"),
        Food("Food9", "Ini adalah deskripsi food9", 48600.0, "", 2, "3"),
    )

    val basketSampleData = arrayListOf<Basket>(
        Basket(1, 1, 2),
        Basket(2, 1, 1),
        Basket(3, 1, 1),
    )
}