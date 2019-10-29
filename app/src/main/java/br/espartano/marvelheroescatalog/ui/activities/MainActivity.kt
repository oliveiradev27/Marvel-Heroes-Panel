package br.espartano.marvelheroescatalog.ui.activities

import android.content.DialogInterface
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
import br.espartano.marvelheroescatalog.repository.CharactersNetworkRepository
import br.espartano.marvelheroescatalog.schedulers.BaseSchedulerProvider
import br.espartano.marvelheroescatalog.ui.adapters.CharactersAdapter
import br.espartano.marvelheroescatalog.usecase.CharactersUseCase
import br.espartano.marvelheroescatalog.viewmodels.CharactersViewModel
import br.espartano.marvelheroescatalog.viewmodels.states.CharactersStates
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity() {

    private val viewModel : CharactersViewModel by lazy {
        val repository = CharactersNetworkRepository()
        ViewModelProviders
            .of(this,
                CharactersViewModel.get(CharactersUseCase(repository), BaseSchedulerProvider()))
            .get(CharactersViewModel::class.java)
    }


    private val characters = mutableListOf<Character>(

    )
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

        shimmerContainer = findViewById(R.id.shimmer_view_container)
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

    private fun configureObserverStates() {
        viewModel
            .getStates()
            .observe(this, Observer { state : CharactersStates ->
                when (state) {
                    is CharactersStates.Loading -> { frameLoading.visibility = View.VISIBLE }
                    is CharactersStates.Loaded  -> updateCharacters(state.caracters)
                    is CharactersStates.Error   -> showErrorMessage()
                    is CharactersStates.InitialState -> configureInitialState()
                    is CharactersStates.EmptyState  -> configureEmptystate()
                }
            })
    }

    private fun updateCharacters(chars : List<Character>) {
        characters.clear()
        chars.forEach { adapter.add(it) }

        recyclerState?.let {
            recyclerHeroes.layoutManager?.onRestoreInstanceState(it)
            recyclerState = null
        }
        removeLoadEffect()
    }

    private fun configureEmptystate() {
        AlertDialog
            .Builder(this)
            .setTitle(R.string.system_message)
            .setMessage("Lista vazia!")
            .setPositiveButton(R.string.try_again) { dialogInterface: DialogInterface, _: Int ->
                viewModel.resetState()
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    private fun configureInitialState() {
        frameLoading.visibility = View.VISIBLE
        shimmerContainer.visibility = View.VISIBLE
        shimmerContainer.startShimmer()
        viewModel.load()
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
            .setTitle(R.string.system_message)
            .setMessage(R.string.system_body_error)
            .setPositiveButton(R.string.try_again) { dialogInterface: DialogInterface, _: Int ->
                viewModel.resetState()
                dialogInterface.dismiss()
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
