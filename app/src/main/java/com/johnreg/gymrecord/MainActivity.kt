package com.johnreg.gymrecord

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.johnreg.gymrecord.calisthenics.CalisthenicsFragment
import com.johnreg.gymrecord.constants.Constants
import com.johnreg.gymrecord.databinding.ActivityMainBinding
import com.johnreg.gymrecord.databinding.DialogResetBinding
import com.johnreg.gymrecord.weights.WeightsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedPreferences by lazy { getSharedPreferences(Constants.CURRENT_ITEM_FILE, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager2AndTabLayout()
    }

    override fun onPause() {
        super.onPause()
        sharedPreferences.edit {
            putInt(Constants.CURRENT_ITEM_INT, binding.viewPager2.currentItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_reset -> {
                showAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog() {
        val dialogBinding = DialogResetBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(this)
            .setTitle("Reset all records")
            .setView(dialogBinding.root)
            .setPositiveButton("Reset") { dialog, _ ->
                showSnackbarAndDeletePreferences(dialogBinding, dialog)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showSnackbarAndDeletePreferences(dialogBinding: DialogResetBinding, dialog: DialogInterface) {
        val weightsPreferences = getSharedPreferences(Constants.WEIGHTS_FILE, Context.MODE_PRIVATE)
        val calisthenicsPreferences = getSharedPreferences(Constants.CALISTHENICS_FILE, Context.MODE_PRIVATE)
        when {
            dialogBinding.checkBoxFreeWeights.isChecked && dialogBinding.checkBoxCalisthenics.isChecked -> {
                weightsPreferences.edit { clear() }
                calisthenicsPreferences.edit { clear() }
                Snackbar.make(binding.root, "All records cleared successfully!", Snackbar.LENGTH_LONG).show()
                refreshFragment()
            }
            !dialogBinding.checkBoxFreeWeights.isChecked && !dialogBinding.checkBoxCalisthenics.isChecked -> {
                dialog.dismiss()
            }
            dialogBinding.checkBoxFreeWeights.isChecked -> {
                weightsPreferences.edit { clear() }
                Snackbar.make(binding.root, "Free Weights records cleared successfully!", Snackbar.LENGTH_LONG).show()
                refreshFragment()
            }
            dialogBinding.checkBoxCalisthenics.isChecked -> {
                calisthenicsPreferences.edit { clear() }
                Snackbar.make(binding.root, "Calisthenics records cleared successfully!", Snackbar.LENGTH_LONG).show()
                refreshFragment()
            }
        }
    }

    private fun refreshFragment() {
        when (binding.tabLayout.selectedTabPosition) {
            0 -> {
                binding.viewPager2.adapter = ViewPager2Adapter(this)
                binding.viewPager2.currentItem = 0
            }
            1 -> {
                binding.viewPager2.adapter = ViewPager2Adapter(this)
                binding.viewPager2.currentItem = 1
            }
        }
    }

    private fun setViewPager2AndTabLayout() {
        binding.viewPager2.adapter = ViewPager2Adapter(this)
        binding.viewPager2.currentItem = sharedPreferences.getInt(Constants.CURRENT_ITEM_INT, 0)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Free Weights"
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.icon_dumbbell)
                }
                1 -> {
                    tab.text = "Calisthenics"
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.icon_pushups)
                }
            }
        }.attach()
    }

    private inner class ViewPager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = Constants.NUMBER_OF_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> CalisthenicsFragment()
                else -> WeightsFragment()
            }
        }
    }

}