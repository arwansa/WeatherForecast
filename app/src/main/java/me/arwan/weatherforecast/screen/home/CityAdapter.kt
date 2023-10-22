package me.arwan.weatherforecast.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.arwan.weatherforecast.R
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

interface ItemClickListener {
    fun onItemClicked(coordinate: CoordinatesDto)
}

class CityAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val coordinatesList = mutableListOf<CoordinatesDto>()

    fun setData(coordinatesDtoList: List<CoordinatesDto>) {
        coordinatesList.clear()
        coordinatesList.addAll(coordinatesDtoList)
        notifyDataSetChanged()
    }

    fun clearData() {
        coordinatesList.clear()
        notifyDataSetChanged()
    }

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityLabel: TextView = view.findViewById(R.id.cityLabel)

        init {
            view.setOnClickListener {
                itemClickListener.onItemClicked(coordinatesList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val coordinate = coordinatesList[position]
        holder.cityLabel.text = "${coordinate.name}, ${coordinate.state}, ${coordinate.country}"
    }

    override fun getItemCount() = coordinatesList.size
}