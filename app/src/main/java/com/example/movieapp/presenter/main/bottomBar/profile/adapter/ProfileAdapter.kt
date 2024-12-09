package com.example.movieapp.presenter.main.bottomBar.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.OptionsUserProfileBinding
import com.example.movieapp.domain.model.menu.MenuProfile
import com.example.movieapp.domain.model.menu.MenuProfileType

class ProfileAdapter(
    private val items: List<MenuProfile>,
    private val context: Context,
    private val onClick: (MenuProfileType) -> Unit
) : RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            OptionsUserProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]

        holder.binding.textOptionProfile.apply {
            text = context.getString(item.name)
            if(item.type == MenuProfileType.LOGOUT) {
                setTextColor(ContextCompat.getColor(context, R.color.color_default))
            }
        }
        holder.binding.imageOptionProfile.setImageDrawable(
            ContextCompat.getDrawable(context, item.icon)
        )
        holder.itemView.setOnClickListener {
            onClick(item.type)
        }

    }

    inner class MyViewHolder(
        val binding: OptionsUserProfileBinding
    ) : RecyclerView.ViewHolder(binding.root)
}