package com.johnreg.gymrecord.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnreg.gymrecord.databinding.ItemWorkoutBinding

class RecyclerViewAdapter(
    private val workouts: Array<Workout>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(workouts[position])
    }

    class ViewHolder(private val binding: ItemWorkoutBinding, onClickListener: OnClickListener?) : RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: Workout) {
            binding.imageViewItem.setImageResource(workout.image)
            binding.textViewImageTitle.text = workout.textTitle
            binding.textViewRecordValue.text = workout.textRecord
            binding.textViewDateValue.text = workout.textDate
            binding.ratingBar.rating = workout.rating
        }

        init {
            itemView.setOnClickListener {
                onClickListener?.onClick(bindingAdapterPosition)
            }
        }

    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

}