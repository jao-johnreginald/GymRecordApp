package com.johnreg.gymrecord.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnreg.gymrecord.R

class RecyclerViewAdapter(
    private val workouts: Array<Workout>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun getItemCount(): Int {
        return workouts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return ViewHolder(itemView, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = workouts[position]
        holder.image.setImageResource(currentItem.image)
        holder.textTitle.text = currentItem.textTitle
        holder.textRecord.text = currentItem.textRecord
        holder.textDate.text = currentItem.textDate
        holder.rating.rating = currentItem.rating
    }

    class ViewHolder(itemView: View, onClickListener: OnClickListener?) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageViewItem)
        val textTitle: TextView = itemView.findViewById(R.id.textViewImageTitle)
        val textRecord: TextView = itemView.findViewById(R.id.textViewRecordValue)
        val textDate: TextView = itemView.findViewById(R.id.textViewDateValue)
        val rating: RatingBar = itemView.findViewById(R.id.ratingBar)

        init {
            itemView.setOnClickListener {
                onClickListener?.onClick(bindingAdapterPosition)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    fun setOnClickListener(parameter: OnClickListener) {
        onClickListener = parameter
    }

}