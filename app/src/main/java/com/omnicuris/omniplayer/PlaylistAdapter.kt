package com.omnicuris.omniplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView


class PlaylistAdapter(
    private val dataList: List<String>,
    private val activity: Activity

) :
    RecyclerView.Adapter<PlaylistAdapter.Holder>() {
    lateinit var data: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_playlist, parent, false)
        return Holder(itemView)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, @SuppressLint("RecyclerView") position: Int) {
        data = dataList[position]
        holder.txtTitle.text = data
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var txtTitle: AppCompatTextView = view.findViewById(R.id.txt_title)
    }
}
