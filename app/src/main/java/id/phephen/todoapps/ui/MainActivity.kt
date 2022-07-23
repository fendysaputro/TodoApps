package id.phephen.todoapps.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import id.phephen.todoapps.R
import id.phephen.todoapps.data.db.TodoDatabase
import id.phephen.todoapps.repository.TodoRepository
import id.phephen.todoapps.ui.home.HomeViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        setupViewModels()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(bottomNavigationView, navController)
    }

    private fun setupViewModels() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }
}