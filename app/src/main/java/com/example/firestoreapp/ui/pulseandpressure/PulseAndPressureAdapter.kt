package com.example.firestoreapp.ui.pulseandpressure

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firestoreapp.databinding.ItemPulseBinding
import com.example.firestoreapp.domain.model.DomainData
import java.util.*

class PulseAndPressureAdapter:
    ListAdapter<DomainData, PulseAndPressureAdapter.UsersViewHolder>(DomainDataCallback) {

    inner class UsersViewHolder(private val vb: ItemPulseBinding) :
        RecyclerView.ViewHolder(vb.root) {
        @SuppressLint("SetTextI18n")
        fun show(model: DomainData, dateViewVisible: Boolean) {
            model.date.get(GregorianCalendar.MONTH)
            vb.itemDate.text = "${model.date.get(GregorianCalendar.DAY_OF_MONTH)}" +
                    getMonth(model.date.get(GregorianCalendar.MONTH))
            vb.itemTime.text =
                "${model.date.get(GregorianCalendar.HOUR_OF_DAY)}:${model.date.get(GregorianCalendar.MINUTE)}"
            vb.itemPressure.text = model.pressure
            vb.itemPulseTextView.text = model.pulse.toString()
            if (dateViewVisible){
                vb.itemDate.visibility = View.VISIBLE
            }else{
                vb.itemDate.visibility = View.GONE
            }
        }

        private fun getMonth(month: Int): String {
            return when (month) {
                0 -> "January"
                1 -> "February"
                2 -> "March"
                3 -> "April"
                4 -> "May"
                5 -> "June"
                6 -> "July"
                7 -> "August"
                8 -> "September"
                9 -> "October"
                10 -> "November"
                11 -> "December"
                else -> ""
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            ItemPulseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        if (position == 0){
            holder.show(currentList[position], true)
        }else {
            if (currentList[position].date.get(Calendar.DAY_OF_YEAR)
                == currentList[position - 1].date.get(Calendar.DAY_OF_YEAR)) {
                holder.show(currentList[position], false)
            } else {
                holder.show(currentList[position], true)
            }
        }
    }

    companion object DomainDataCallback : DiffUtil.ItemCallback<DomainData>() {
        override fun areItemsTheSame(
            oldItem: DomainData,
            newItem: DomainData,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DomainData,
            newItem: DomainData,
        ): Boolean {
            return oldItem == newItem
        }
    }
}