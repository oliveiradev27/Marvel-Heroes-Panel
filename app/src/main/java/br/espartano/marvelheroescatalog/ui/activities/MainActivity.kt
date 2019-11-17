package br.espartano.marvelheroescatalog.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.espartano.marvelheroescatalog.R
import br.espartano.marvelheroescatalog.data.api.Character
import br.espartano.marvelheroescatalog.ui.adapters.CharactersAdapter
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import com.facebook.shimmer.ShimmerFrameLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val charactersViewModel: CharactersViewModel by viewModel()
    private val characters = mutableListOf<Character>()
    private val adapter : CharactersAdapter by lazy {
         CharactersAdapter(characters)
    }

    private lateinit var recyclerHeroes : RecyclerView
    private lateinit var frameLoading : ViewGroup
    private lateinit var shimmerContainer: ShimmerFrameLayout
    private var recyclerState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureObserverStates()
        initializeViews()
        configureListeners()
    }

    private fun initializeViews() {
        shimmerContainer = findViewById(R.id.shimmer_view_container)
        frameLoading = findViewById(R.id.fl_loading)
        recyclerHeroes = findViewById(R.id.rv_characters)
    }

    private fun configureListeners() {
        val manager = LinearLayoutManager(this)
        recyclerHeroes.layoutManager = manager
        recyclerHeroes.adapter = adapter
        recyclerHeroes.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
                charactersViewModel.loadMoreCharacters(lastVisibleItemPosition)
            }
        })
    }

    private fun configureObserverStates() {
        charactersViewModel
            .getStates()
            .observe(this, Observer { state : CharactersStates ->
                when (state) {
                    is CharactersStates.Loading -> { frameLoading.visibility = View.VISIBLE }
                    is CharactersStates.Loaded  -> updateCharacters(state.caracters)
                    is CharactersStates.Error   -> showErrorMessage()
                    is CharactersStates.InitialState -> configureInitialState()
                    is CharactersStates.EmptyState  -> showErrorMessage()
                }
            })
    }

    private fun updateCharacters(chars : List<Character>) {
        characters.clear()
        adapter.add(chars)

        recyclerState?.let {
            recyclerHeroes.layoutManager?.onRestoreInstanceState(it)
            recyclerState = null
        }
        removeLoadEffect()
    }

    private fun configureInitialState() {
        frameLoading.visibility = View.VISIBLE
        shimmerContainer.visibility = View.VISIBLE
        shimmerContainer.startShimmer()
        charactersViewModel.loadMoreCharacters()
    }

    private fun removeLoadEffect() {
        frameLoading.visibility = View.GONE
        shimmerContainer.stopShimmer()
        shimmerContainer.visibility = View.GONE
    }

    private fun showErrorMessage() {
        frameLoading.visibility = View.GONE

        AlertDialog
            .Builder(this)
            .setCancelable(false)
            .setTitle(R.string.system_message)
            .setMessage(R.string.system_body_error)
            .setPositiveButton(R.string.try_again) { dialog, _ ->
                frameLoading.visibility = View.VISIBLE
                charactersViewModel.resetState()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recyclerHeroes.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }
}
