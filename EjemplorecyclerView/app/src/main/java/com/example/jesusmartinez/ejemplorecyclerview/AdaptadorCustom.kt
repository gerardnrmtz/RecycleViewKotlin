package com.example.jesusmartinez.ejemplorecyclerview

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import org.w3c.dom.Text

class AdaptadorCustom(items:ArrayList<Platillo>,var listener:ClickListener,var longClickListener: LongClickListener):RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items:ArrayList<Platillo>?=null
    //Guardar indices de los elementos seleccionados
    var itemsSeleccionados:ArrayList<Int>?=null
    var viewholder:ViewHolder?=null
    var multiSeleccion= false
    init {
        this.items=items
        itemsSeleccionados=ArrayList()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdaptadorCustom.ViewHolder {
       val vista=LayoutInflater.from(p0.context).inflate(R.layout.template_platillo,p0,false)

         viewholder=ViewHolder(vista,listener,longClickListener)
        return  viewholder!!
    }

    override fun getItemCount(): Int {
       return  items?.count()!!
    }

    override fun onBindViewHolder(holder: AdaptadorCustom.ViewHolder, p1: Int) {
        val item=items?.get(p1)
        holder.photo?.setImageResource(item?.photo!!)
        holder.nombre?.text=item?.nombre!!
        holder.precio?.text="$"+item?.precio!!.toString()
        holder.rating?.rating=item?.rating!!

        if(itemsSeleccionados?.contains(p1)!!)
        {
            holder.vista.setBackgroundColor(Color.LTGRAY)
        }else{
            holder.vista.setBackgroundColor(Color.WHITE)
        }
    }

    fun iniciarActionMode()
    {
        multiSeleccion=true
    }
    fun destruirActionMode()
    {
        multiSeleccion=false
        itemsSeleccionados?.clear()
        notifyDataSetChanged()
    }
    fun terminarActionMode()
    {
        for (item in itemsSeleccionados!!)
        {
            itemsSeleccionados?.remove(item)
        }
        multiSeleccion=false
        notifyDataSetChanged()
    }
    fun seleccionarItem(index:Int)
    {
        if(multiSeleccion)
        {
            if(itemsSeleccionados?.contains(index)!!){
                itemsSeleccionados?.removeAt(index)
            }
            else{
                itemsSeleccionados?.add((index))
            }

        }
        notifyDataSetChanged()
    }
    fun obtenerElementosSeleccionados():Int
    {
        return  itemsSeleccionados?.count()!!
    }
    fun eliminarSeleccionados(){
        if(itemsSeleccionados?.count()!!>0){
            var itemsEliminados=ArrayList<Platillo>()

            for (index in itemsSeleccionados!!)
            {
                itemsEliminados.add(items?.get(index)!!)

            }
            items?.removeAll(itemsEliminados)
            itemsSeleccionados?.clear()
        }
    }

    class ViewHolder(vista:View,listener: ClickListener,longClickListener: LongClickListener):RecyclerView.ViewHolder(vista),
        OnClickListener,View.OnLongClickListener{


        var vista=vista
        var photo:ImageView?=null
        var nombre:TextView?=null
        var precio:TextView?=null
        var rating:RatingBar?=null

        var listener:ClickListener?=null
        var longlistener:LongClickListener?=null

        init {
            this.photo=vista.findViewById(R.id.ivPhoto)
            this.nombre=vista.findViewById(R.id.tvNombre)
            this.precio=vista.findViewById(R.id.tvPrecio)
            this.rating=vista.findViewById(R.id.tvRating)
            this.listener=listener
            this.longlistener=longClickListener
            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.longlistener?.longClick(v!!,adapterPosition)
            return true
        }

    }

}