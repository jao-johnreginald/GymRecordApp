package com.johnreg.gymrecord.weights

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
import com.johnreg.gymrecord.databinding.FragmentWeightsBinding
import com.johnreg.gymrecord.recyclerview.RecyclerViewAdapter
import com.johnreg.gymrecord.recyclerview.Workout

class WeightsFragment : Fragment() {

    private lateinit var binding: FragmentWeightsBinding

    private val sharedPreferences by lazy { requireContext().getSharedPreferences(Constants.WEIGHTS_FILE, Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val workouts = arrayOf(
            Workout(R.drawable.bench_press, Constants.BENCH_PRESS, returnFullString(Constants.BENCH_PRESS), getStringPreferences("${Constants.BENCH_PRESS} ${Constants.DATE}"), getFloatPreferences("${Constants.BENCH_PRESS} ${Constants.RATING}")),
            Workout(R.drawable.barbell_squat, Constants.BARBELL_SQUAT, returnFullString(Constants.BARBELL_SQUAT), getStringPreferences("${Constants.BARBELL_SQUAT} ${Constants.DATE}"), getFloatPreferences("${Constants.BARBELL_SQUAT} ${Constants.RATING}")),
            Workout(R.drawable.deadlift, Constants.DEADLIFT, returnFullString(Constants.DEADLIFT), getStringPreferences("${Constants.DEADLIFT} ${Constants.DATE}"), getFloatPreferences("${Constants.DEADLIFT} ${Constants.RATING}")),
            Workout(R.drawable.overhead_press, Constants.OVERHEAD_PRESS, returnFullString(Constants.OVERHEAD_PRESS), getStringPreferences("${Constants.OVERHEAD_PRESS} ${Constants.DATE}"), getFloatPreferences("${Constants.OVERHEAD_PRESS} ${Constants.RATING}"))
        )

        val recyclerViewAdapter = RecyclerViewAdapter(workouts)
        binding.recyclerViewWeights.adapter = recyclerViewAdapter

        recyclerViewAdapter.setOnClickListener(object : RecyclerViewAdapter.OnClickListener {
            override fun onClick(position: Int) {
                val currentItem = workouts[position]

                val intent = Intent(context, EditWeightsActivity::class.java)
                intent.putExtra(Constants.WEIGHTS_INTENT_DATA, IntentData(currentItem.image, currentItem.textTitle))
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
            else -> "${getStringPreferences("$string ${Constants.RECORD}")} ${getStringPreferences("$string ${Constants.IS_KG_OR_LBS}")}"
        }
    }

}