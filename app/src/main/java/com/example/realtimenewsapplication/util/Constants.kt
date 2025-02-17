package com.example.realtimenewsapplication.util

import com.example.realtimenewsapplication.R
import com.example.realtimenewsapplication.models.BottomNavItem

object Constants {

    val BottomNavItems=listOf(
        BottomNavItem(
            label = "Home",
            route = "home",
            icon = R.drawable.home

        ),
        BottomNavItem(
            label = "Search"
            , route = "search",
            icon = R.drawable.search
        )
        , BottomNavItem(
            label = "Favourite",
            route = "profile",
            icon = R.drawable.favorites
        )
    )
}
