package br.espartano.marvelheroescatalog.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.ui.adapters.CharactersAdapter
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates

class MainActivity : AppCompatActivity() {

    private val viewModel : CharactersViewModel by lazy {
        ViewModelProviders
            .of(this)
            .get(CharactersViewModel::class.java)
    }


    private val characters = mutableListOf<Character>(

    )
    private val adapter : CharactersAdapter by lazy {
         CharactersAdapter(characters)
    }

    private lateinit var recyclerHeroes : RecyclerView
    private lateinit var frameLoading : ViewGroup
    private var recyclerState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureObserverStates()

        frameLoading = findViewById(R.id.fl_loading)
        recyclerHeroes = findViewById(R.id.rv_characters)
        val manager = LinearLayoutManager(this)
        recyclerHeroes.layoutManager = manager
        recyclerHeroes.adapter = adapter
        recyclerHeroes.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == adapter.itemCount - 1) {
                    viewModel.loadMoreCharacters()
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recyclerHeroes.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun configureObserverStates() {
        viewModel
            .getStates()
            .observe(this, Observer{ state : CharactersStates ->
                when (state) {
                    is CharactersStates.Loading -> frameLoading.visibility = View.VISIBLE
                    is CharactersStates.Loaded -> { updateCharacters(state.caracters) }
                    is CharactersStates.Error -> { showErrorMessage(state.message) }
                    else -> viewModel.load()
                }
            })
    }

    private fun updateCharacters(chars : List<Character>) {
        characters.clear()
        chars.forEach {
            adapter.add(it)
        }

        recyclerState?.let {
            recyclerHeroes.layoutManager?.onRestoreInstanceState(it)
            recyclerState = null
        }

        frameLoading.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        frameLoading.visibility = View.GONE

        AlertDialog.Builder(this)
            .setTitle("Mensagem do sistema")
            .setMessage(message)
            .create()
            .show()
    }
}
