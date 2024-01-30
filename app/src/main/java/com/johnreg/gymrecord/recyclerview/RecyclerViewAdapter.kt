package com.johnreg.gymrecord.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnreg.gymrecord.R
import com.johnreg.gymrecord.databinding.ItemWorkoutBinding

class RecyclerViewAdapter(
    private val workouts: Array<Workout>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int = workouts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_workout, parent, false)
        return ViewHolder(itemView, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = workouts[position]
        holder.bind(currentItem)
    }

    class ViewHolder(
        itemView: View,
        onClickListener: OnClickListener?
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(currentItem: Workout) {
            val binding = ItemWorkoutBinding.bind(itemView)
            binding.imageViewItem.setImageResource(currentItem.image)
            binding.textViewImageTitle.text = currentItem.textTitle
            binding.textViewRecordValue.text = currentItem.textRecord
            binding.textViewDateValue.text = currentItem.textDate
            binding.ratingBar.rating = currentItem.rating
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