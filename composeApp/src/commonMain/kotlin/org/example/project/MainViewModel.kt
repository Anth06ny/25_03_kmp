package org.example.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.model.Photographer
import org.example.project.model.PhotographerAPI

class MainViewModel : ViewModel() {

    private val _dataList = MutableStateFlow(emptyList<Photographer>())
    val dataList = _dataList.asStateFlow()

    private val _runInProgress = MutableStateFlow(false)
    val runInProgress = _runInProgress.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        loadPhotographers()
    }

    private fun loadPhotographers() {
        _runInProgress.value = true

        viewModelScope.launch {
            try {
                _dataList.value = PhotographerAPI.loadPhotographers()
                _runInProgress.value = false
            }
            catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Une erreur"
                _runInProgress.value = false
            }
        }
    }

    //Pour la Preview
    fun loadFakeData(runInProgress: Boolean = false, errorMessage: String = "") {
        _runInProgress.value = runInProgress
        _errorMessage.value = errorMessage
        _dataList.value = listOf(
            Photographer(
                id = 1,
                stageName = "Bob la Menace",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/bob.jpg",
                story = "Ancien agent secret, Bob a troqué ses gadgets pour un appareil photo après une mission qui a mal tourné. Il traque désormais les instants volés plutôt que les espions.",
                portfolio = listOf(
                    "https://example.com/photo1.jpg",
                    "https://example.com/photo2.jpg",
                    "https://example.com/photo3.jpg"
                )
            ),
            Photographer(
                id = 2,
                stageName = "Jean-Claude Flash",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/jc.jpg",
                story = "Ancien champion de rodéo, il s’est reconverti en photographe après une chute mémorable. Maintenant, il dompte la lumière comme un vrai cow-boy.",
                portfolio = listOf(
                    "https://example.com/photo4.jpg",
                    "https://example.com/photo5.jpg",
                    "https://example.com/photo6.jpg"
                )
            )
        )
    }
}