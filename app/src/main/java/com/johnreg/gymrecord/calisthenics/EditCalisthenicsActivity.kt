package com.johnreg.gymrecord.calisthenics

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.johnreg.gymrecord.constants.Constants
import com.johnreg.gymrecord.constants.IntentData
import com.johnreg.gymrecord.databinding.ActivityEditCalisthenicsBinding

class EditCalisthenicsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCalisthenicsBinding

    private val intentData by lazy {
        @Suppress("DEPRECATION")
        intent.getSerializableExtra(Constants.CALISTHENICS_INTENT_DATA) as IntentData
    }

    private val sharedPreferences by lazy {
        getSharedPreferences(Constants.CALISTHENICS_FILE, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCalisthenicsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUI()
        setClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                areThereChanges()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun areThereChanges() {
        when {
            sharedPreferences.getString(
                "${intentData.textTitle} ${Constants.RECORD}",
                ""
            ) != binding.editTextReps.text.toString() || sharedPreferences.getString(
                "${intentData.textTitle} ${Constants.DATE}",
                ""
            ) != binding.editTextDate.text.toString() || sharedPreferences.getInt(
                "${intentData.textTitle} ${Constants.SPINNER_POSITION}",
                0
            ) != binding.spinner.selectedItemPosition -> showAlertDialog()
            else -> finish()
        }
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Save changes")
            .setMessage("Do you want to save your changes?")
            .setPositiveButton("Save") { _, _ ->
                replacePreferences()
                finish()
            }
            .setNegativeButton("Don't Save") { _, _ ->
                finish()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    private fun setUI() {
        title = "${intentData.textTitle} Record"
        binding.imageView.setImageResource(intentData.image)

        val ratings = arrayOf("Rating", "1 Star", "2 Star", "3 Star", "4 Star", "5 Star")
        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ratings)
        binding.spinner.setSelection(sharedPreferences.getInt("${intentData.textTitle} ${Constants.SPINNER_POSITION}", 0))

        binding.editTextReps.setText(sharedPreferences.getString("${intentData.textTitle} ${Constants.RECORD}", null))
        binding.editTextDate.setText(sharedPreferences.getString("${intentData.textTitle} ${Constants.DATE}", null))
    }

    private fun setClickListeners() {
        binding.buttonSave.setOnClickListener {
            replacePreferences()
            finish()
        }

        binding.buttonClearRecord.setOnClickListener {
            deletePreferences()
            finish()
        }
        onBackPressedDispatcher.addCallback { areThereChanges() }
    }

    private fun replacePreferences() {
        val floatRating = when (binding.spinner.selectedItemPosition) {
            1 -> 1f
            2 -> 2f
            3 -> 3f
            4 -> 4f
            5 -> 5f
            else -> 0f
        }

        sharedPreferences.edit {
            putString("${intentData.textTitle} ${Constants.RECORD}", binding.editTextReps.text.toString())
            putString("${intentData.textTitle} ${Constants.DATE}", binding.editTextDate.text.toString())
            putFloat("${intentData.textTitle} ${Constants.RATING}", floatRating)
            putInt("${intentData.textTitle} ${Constants.SPINNER_POSITION}", binding.spinner.selectedItemPosition)
        }
    }

    private fun deletePreferences() {
        sharedPreferences.edit {
            remove("${intentData.textTitle} ${Constants.RECORD}")
            remove("${intentData.textTitle} ${Constants.DATE}")
            remove("${intentData.textTitle} ${Constants.RATING}")
            remove("${intentData.textTitle} ${Constants.SPINNER_POSITION}")
        }
    }
    
}