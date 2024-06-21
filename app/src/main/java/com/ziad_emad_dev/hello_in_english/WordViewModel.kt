package com.ziad_emad_dev.hello_in_english

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ziad_emad_dev.hello_in_english.database.Word
import com.ziad_emad_dev.hello_in_english.database.WordDB

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val words = WordDB(getApplication<Application>().applicationContext).getAllWords()

    private val _quizSize = MutableLiveData(words.size)
    val quizSize: LiveData<Int>
        get() = _quizSize

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentWordUsed = MutableLiveData<String>()
    val currentWordUsed: LiveData<String>
        get() = _currentWordUsed

    private val _currentWordUsedAnswer = MutableLiveData<String>()

    private val _playerWord = MutableLiveData<String>()

    private val _status = MutableLiveData("Start")

    fun startQuiz() {
        if (_status.value == "Start") {
            getNextWord()
            _status.value = "Running"
        }
    }

    private var myWordsUsed: MutableList<Word> = mutableListOf()

    private lateinit var currentWord: Word

    private fun getNextWord() {

        currentWord = words.random()

        if (myWordsUsed.contains(currentWord)) {
            getNextWord()
        } else {
            _currentWordUsed.value = currentWord.arabicWord
            _currentWordUsedAnswer.value = currentWord.englishWord
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            myWordsUsed.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < words.size) {
            getNextWord()
            true
        } else {
            false
        }
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.inc()
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        _playerWord.value = playerWord
        if (_playerWord.value.toString().uppercase() == _currentWordUsedAnswer.value.toString()
                .uppercase()
        ) {
            increaseScore()
            return true
        }
        return false
    }

    fun restartQuiz() {
        _score.value = 0
        _currentWordCount.value = 0
        myWordsUsed.clear()
        getNextWord()
    }
}