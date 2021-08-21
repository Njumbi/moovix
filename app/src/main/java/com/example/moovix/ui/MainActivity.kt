package com.example.moovix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.moovix.R
import com.example.moovix.ui.fragments.AccountFragment
import com.example.moovix.ui.fragments.ActorsFragment
import com.example.moovix.ui.fragments.MoviesFragment
import com.example.moovix.ui.fragments.SeriesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(MoviesFragment())
        bottom_menu.setOnNavigationItemSelectedListener(this)

    }

    private fun loadFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main_holder, frag)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_movies -> loadFragment(MoviesFragment())
            R.id.item_series -> loadFragment(SeriesFragment())
            R.id.item_actors -> loadFragment(ActorsFragment())
            R.id.item_account -> loadFragment(AccountFragment())


        }
        return true
    }
}