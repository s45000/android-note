package com.survivalcoding.noteapp.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ActivityMainBinding
import com.survivalcoding.noteapp.presentation.note_add.AddNoteFragment
import com.survivalcoding.noteapp.presentation.note_edit.UpdateNoteFragment
import com.survivalcoding.noteapp.presentation.note_list.NoteListFragment
import com.survivalcoding.noteapp.presentation.util.nav.NoteNav
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun isTablet(): Boolean {
        val screenSizeType =
            resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        if (screenSizeType == Configuration.SCREENLAYOUT_SIZE_XLARGE ||
            screenSizeType == Configuration.SCREENLAYOUT_SIZE_LARGE
        ) {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        if (isTablet()) {
            navHostFragment.navController.apply {
                graph = createGraph(
                    startDestination = NoteNav.NoteAdd.route,
                ) {
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
        } else {
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
}