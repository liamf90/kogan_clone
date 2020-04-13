package com.liamfarrell.android.koganclone.model.homescreen

import com.liamfarrell.android.koganclone.model.Product

class HomeScreenItems {

    fun getHeaderItems() : List<HomeScreenAd>{
        return listOf(
            HomeScreenAd(
                "https://assets.kogan.com/files/product/aub/InDemand_KGN_HPTILE.png",
                "In High Demand - Trending Items",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/pages/COVID-19/Covid_KGN_HPTILE_2.png",
                null,
                null,
                "https://kogan.com/au/high-volumes-update"
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/New_TV_HP_DT_tiles_2020/TV_HPTile.png",
                "TV & Home Theatre",
                "LED Televisions",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/Home_Office_KGN_HPTILE_6.png",
                "Revamp Your Home Office",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2020/Fitness_KGN_HPTILE.png",
                "Sports, Outdoors & Luggage",
                "Fitness Equipment",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/NintendoSwitch-General_HPTILE.png",
                "department: Gadgets, Toys & Video Games",
                null,
                null
            )
        )
    }

    fun getCurrentDealItems() : List<HomeScreenAd> {
        return listOf(
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/WinterWarmers_HPTile.jpg",
                null,
                null,
                "https://kogan.com/au/cold-weather-essentials"
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/mobile/BonusData_HP-tile-au.png",
                "Phones",
                "Prepaid Plans",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/Vacuum_KGN_HPTILE_V3.png",
                "Appliances",
                "Vacuum Cleaners",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/Heater_HPTiles.jpg",
                "Heating & Cooling",
                "Heaters",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/BWS_HPTILE.png",
                "Wine & Pantry",
                "Beer, Wine & Spirits",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2020/Nintendo_KGN_HPTILE.png",
                "Gadgets, Toys & Video Games",
                "5% OFF Selected Nintendo Switch Games & Accessories with any Nintendo Switch Console purchase*",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/Shoes_Apparel_Clearance_KGN_HP.png",
                "Shoes & Fashion",
                "Big Brand Fashion",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/OutbackUggs_KGN_HPTILE.png",
                "Shoes & Fashion",
                "Boots",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2020/Games_HPTILE.png",
                "Entertain Yourself at Home with Games & Gadgets",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/February-2020/HP_TILE_WHITEGOODS.jpg",
                "Whitegoods",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/December-2019/KOGAN_HOMEPAGE_TILE_FashionOutlet_v2.png",
                null,
                null,
                "https://kogan.com/au/shoes-fashion"
            ), HomeScreenAd(
                "https://assets.kogan.com/files/banners/February-2020/HP_TILE_WHITEGOODS.jpg",
                "Whitegoods",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2020/Trafaglar_Sets_HP_TILE.jpg.png",
                "Home & Garden",
                "Trafalgar 1500TC Cotton Rich Luxury Sets",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/January-2020/AU-HP-APPLE-AIRPODS_v2.png",
                "Audio",
                "In-Ear Headphones",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/SmarterHome_HPTile.jpg",
                null,
                null,
                "https://kogan.com/au/smarter-home"
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2019/New_Products_HOMEPAGE_TILE.png",
                "New Products",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2019/KM_HOMEPAGE_TILE_TEMPLATE_REFRESH.png",
                "Free Kogan MObile Large Plan with Eligible Smartphones",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/200401_HOMEPAGE-TILE.png",
                "Phones",
                "Prepaid Plans",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/January-2020/Ergolux_HOMEPAGE_TILE_TEMPLATE_REFRESH_v2.png",
                "Home & Garden",
                "Bed Frames",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/January-2020/KOGAN_HOMEPAGE_TILE_ErgoluxChairs_v2.png",
                "Get 15% Off when you buy 2 or more eligible Ergolux Standing Desks",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/January-2020/KOGAN_HOMEPAGE_TILE_Ergolux_Desks_v3.png",
                "Get 15% Off when you buy 2 or more eligible Ergolux Office Chairs",
                null,
                null
            )
        )
    }


    fun getPopularCategories() : List<HomeScreenAd> {
        return listOf(
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/December-2019/AU-HP-Cleaning-Appliances.png",
                "Cleaning Appliances & Accessories",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/April-2020/ToolsGarden_HPTILE.png",
                "Tools & Gardens",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/February-2020/HP_TILE_WHITEGOODS.jpg",
                "Whitegoods",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/New_TV_HP_DT_tiles_2020/TV_HPTile.png",
                "TV & Home Theatre",
                "LED Televisions",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/January-2020/AIR_PURIFIERS_KOGAN_HOMEPAGE_TILE_TEMPLATE_V2.png",
                "Air purifiers",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2019/KAU_Furniture_HOMEPAGE_TILE.png",
                null,
                null,
                "kogan.com/au/furniture"
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/august-2018/KOGAN_HOMEPAGE_TILE_TEMPLATE_SAMSUNG.jpg",
                "Phones",
                "Pre-loved Phones",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/December-2019/AU_homepage_outdoor_living.png",
                "Outdoor Living",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/may-2018-updates/ROLLOUT/KAU-LAPTOPS-NEW-homepage-tile.png",
                "Laptops & Computers",
                "Laptops",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/December-2019/AU-HP-Robot-Vacuums.png",
                "Appliances",
                "Robot Vacuums",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/September-2019/iphone11_11pro_KOGAN_HOMEPAGE_TILE.png",
                "Phones",
                "iPhones",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/January-2020/Beer_Cider_KOGAN_HOMEPAGE_TILE_v2.png",
                "Wine & Pantry",
                "Beer & Cider",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/March-2019/KAU_Office_HOMEPAGE_TILE.png",
                "Ergolux Office",
                null,
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/august-2018/Manchester_HomepageTile.png",
                "Home & Garden",
                "Manchester",
                null
            ),
            HomeScreenAd(
                "https://assets.kogan.com/files/banners/Kitchen_HPTile.png",
                "Appliances",
                "Ice Makers",
                null
            )
        )
    }


    fun getBrandItems() =
        listOf(
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/logos/applelogo.png",
                "Apple"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/brand-logos/dji-2.png",
                "DJI"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/logos/googlelogo.jpg",
                "Google"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/logos/LOGO_150x50_2.png",
                "Kogan"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/brand-logos/Microsoft.png",
                "Microsoft"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/logos/OVELA_LOGO.png",
                "Ovela"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/brand-logos/samsung.png",
                "Samsung"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/logos/SonyLogo.jpg",
                "Sony"
            ),
            HomeScreenBrandAd(
                "https://assets.kogan.com/files/logos/swann.png",
                "Swann"
            )
        )


        fun getTrendingItems() =
            listOf(
                Product(
                    1,
                    "Kogan 5-in-1 Stream Mop",
                    "Bake your own delicious pies in no time!",
                    "Kogan",
                    "Tough cleaning made easy",
                    "Cooking Appliances",
                    true,
                    true,
                    "Leaves warehouse in 1-2 business days",
                    49.99,
                    129.00,
                    null,
                    listOf("https://assets.kogan.com/files/product/2020/KAMOP51STMA/KAMOP51STMA_Hero.jpg")
                ),
                Product(
                    2,
                    "Kogan T5 Corded 45W Stick Vacuum",
                    "Cleans your home from top to toe",
                    "Kogan",
                    "Appliances",
                    "Vacuum Cleaners",
                    true,
                    true,
                    "Leaves warehouse in 1-2 business days",
                    79.99,
                    179.00,
                    null,
                    listOf("https://assets.kogan.com/files/product/KACRDSTVCMA/KACRDSTVCMA_01_v2.jpg")
                ),
                Product(
                    3,
                    "Kogan 10L Digital Multifunction Air Fryer",
                    "Appliances",
                    "Kogan",
                    "Appliances",
                    "Air Fryers",
                    true,
                    false,
                    "Leaves warehouse in 1-2 business days",
                    119.99,
                    199.00,
                    null,
                    listOf("https://assets.kogan.com/files/product/KA10DMFAFRA/KA10DMFAFRA_hero.jpg")
                ),
                Product(
                    4,
                    "Kogan SmarterHome™ R40 Smart Robot Vacuum with Mopping Function",
                    "Smarter vacuuming and mopping.",
                    "Kogan",
                    "Appliances",
                    "Vacuum Cleaners",
                    true,
                    true,
                    "Leaves warehouse in 1-2 business days",
                    199.99,
                    349.00,
                    null,
                    listOf("https://assets.kogan.com/files/product/2019/smarter/KAWFROBVACA_HERO_V4.jpg")
                ),
                Product(
                    5,
                    "Kogan 5.2L Digital Low Fat 1800W Air Fryer",
                    "Prepare healthy dishes with ease!",
                    "Kogan",
                    "Appliances",
                    "Air Fryerss",
                    true,
                    false,
                    "Leaves warehouse in 1-2 business days",
                    89.99,
                    149.99,
                    null,
                    listOf("https://assets.kogan.com/files/product/2020/KA5LDGAFRYA/KA5LDGAFRYA_Hero_v3.jpg")
                )


            )
}