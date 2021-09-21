/*
 * Nguyen Tien Dung
 * dunghkuit@gmail.com
 * UIT
 */

package com.tiendunghk.sevenminuteapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tiendunghk.sevenminuteapp.models.ExerciseModel

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.StatusViewHolder>() {
    class StatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem = view.findViewById<TextView>(R.id.tvItemStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        return StatusViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_exercise_status,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {

        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()

        if (model.getIsSelected()) {
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_accent_border)

            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        } else if (model.getIsCompleted()) {
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.tvItem.background =
                ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
