package com.survivalcoding.noteapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ActivityMainBinding
import com.survivalcoding.noteapp.presentation.view.nav.NoteNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        navHostFragment.navController.apply {
            graph = createGraph(
                startDestination = NoteNav.NoteList.route,
            ) {
                fragment<NoteListFragment>(route = NoteNav.NoteList.route)
                fragment<AddNoteFragment>(route = NoteNav.NoteAdd.route)
                fragment<UpdateNoteFragment>(
                    route = NoteNav.NoteUpdate.route +
                            "?noteId={noteId}"
                ) {
                    argument(name = "noteId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                }
            }
        }
    }
}