package com.johnreg.gymrecord.calisthenics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.johnreg.gymrecord.R
import com.johnreg.gymrecord.constants.Constants
import com.johnreg.gymrecord.constants.IntentData
import com.johnreg.gymrecord.databinding.FragmentCalisthenicsBinding
import com.johnreg.gymrecord.recyclerview.RecyclerViewAdapter
import com.johnreg.gymrecord.recyclerview.Workout

class CalisthenicsFragment : Fragment() {

    private lateinit var binding: FragmentCalisthenicsBinding

    private val sharedPreferences by lazy { requireContext().getSharedPreferences(Constants.CALISTHENICS_FILE, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalisthenicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val workouts = arrayOf(
            Workout(R.drawable.pushups, Constants.PUSHUPS, returnFullString(Constants.PUSHUPS), getStringPreferences("${Constants.PUSHUPS} ${Constants.DATE}"), getFloatPreferences("${Constants.PUSHUPS} ${Constants.RATING}")),
            Workout(R.drawable.pullups, Constants.PULL_UPS, returnFullString(Constants.PULL_UPS), getStringPreferences("${Constants.PULL_UPS} ${Constants.DATE}"), getFloatPreferences("${Constants.PULL_UPS} ${Constants.RATING}")),
            Workout(R.drawable.situps, Constants.SIT_UPS, returnFullString(Constants.SIT_UPS), getStringPreferences("${Constants.SIT_UPS} ${Constants.DATE}"), getFloatPreferences("${Constants.SIT_UPS} ${Constants.RATING}")),
            Workout(R.drawable.squats, Constants.SQUATS, returnFullString(Constants.SQUATS), getStringPreferences("${Constants.SQUATS} ${Constants.DATE}"), getFloatPreferences("${Constants.SQUATS} ${Constants.RATING}"))
        )

        val recyclerViewAdapter = RecyclerViewAdapter(workouts)
        binding.recyclerViewCalisthenics.adapter = recyclerViewAdapter

        recyclerViewAdapter.setOnClickListener(object : RecyclerViewAdapter.OnClickListener {
            override fun onClick(position: Int) {
                val currentItem = workouts[position]

                val intent = Intent(context, EditCalisthenicsActivity::class.java)
                intent.putExtra(Constants.CALISTHENICS_INTENT_DATA, IntentData(currentItem.image, currentItem.textTitle))
                startActivity(intent)
            }
        })
    }

    private fun getStringPreferences(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    private fun getFloatPreferences(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    private fun returnFullString(string: String): String {
        return when (getStringPreferences("$string ${Constants.RECORD}")) {
            "" -> ""
            else -> "${getStringPreferences("$string ${Constants.RECORD}")} reps"
        }
    }

}