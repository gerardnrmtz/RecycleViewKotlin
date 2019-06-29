package com.example.jesusmartinez.ejemplorecyclerview

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var lista:RecyclerView?=null
    var adaptador:AdaptadorCustom?=null
    //administrar el layout del recyclerlist
    var layoutManager:RecyclerView.LayoutManager?=null

    var isActionMode=false
    var actionMode:android.support.v7.view.ActionMode?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val platillos=ArrayList<Platillo>()
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.chilesrellenos))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.enchiladas))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.shushi))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.sopes))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.pollo))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.tacos))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.tostadas))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.gorditas))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.enchiladasverdes))
        platillos.add(Platillo("platillo 1",250.0,3.5f,R.drawable.tamales))

        lista=findViewById(R.id.lista)
        //si tiene un alto definido
        lista?.setHasFixedSize(true)

        layoutManager=LinearLayoutManager(this)
        lista?.layoutManager=layoutManager

        var callback =object :android.support.v7.view.ActionMode.Callback{
            override fun onActionItemClicked(p0: android.support.v7.view.ActionMode?, p1: MenuItem?): Boolean {
                //Cuanto le damos click al toolbar
                when(p1?.itemId){
                    R.id.iEliminar->{
                        adaptador?.eliminarSeleccionados()
                    }

                    else -> {return true}

                }
                adaptador?.terminarActionMode()
                p0?.finish()
                isActionMode=false
                return true
            }

            override fun onCreateActionMode(p0: android.support.v7.view.ActionMode?, p1: Menu?): Boolean {
                //Permite inicializar este actionmode
                adaptador?.iniciarActionMode()
                actionMode=p0
                menuInflater.inflate(R.menu.menu_contextual,p1!!)
                return true
            }

            override fun onPrepareActionMode(p0: android.support.v7.view.ActionMode?, p1: Menu?): Boolean {
                //Preparar el menu
                p0?.title=""
                return false

            }

            override fun onDestroyActionMode(p0: android.support.v7.view.ActionMode?) {
                adaptador?.destruirActionMode()
                isActionMode=false
            }


        }

        adaptador=AdaptadorCustom(platillos,object :ClickListener{
            override fun onClick(vista: View, index: Int) {
               Toast.makeText(applicationContext,platillos.get(index).nombre,Toast.LENGTH_SHORT).show()
            }

        },object:LongClickListener{
            override fun longClick(vista: View, index: Int) {
              if(!isActionMode){
                 startSupportActionMode(callback)
                  isActionMode=true
                  adaptador?.seleccionarItem(index)
              }
                else{

                  adaptador?.seleccionarItem(index)
                  //selecciones o deselecciones
              }
                actionMode?.title= adaptador?.obtenerElementosSeleccionados().toString()+" Seleccionados"

            }

        })
        lista?.adapter=adaptador









        val SwipeToRefresh=findViewById<SwipeRefreshLayout>(R.id.SwipeRefresh)
        SwipeToRefresh.setOnRefreshListener {
            for (i in 1..10000000)
            {

            }
            SwipeToRefresh.isRefreshing=false
            platillos.add(Platillo("platillo 2",250.0,5.0f,R.drawable.chilesrellenos))
            adaptador?.notifyDataSetChanged()
        }
    }
}
