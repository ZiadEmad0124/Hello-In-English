package com.ziad_emad_dev.hello_in_english.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ziad_emad_dev.hello_in_english.R
import com.ziad_emad_dev.hello_in_english.database.Word
import com.ziad_emad_dev.hello_in_english.database.WordDB
import com.ziad_emad_dev.hello_in_english.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordDB: WordDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        wordDB = WordDB(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            addNewWord()
        }
    }

    private fun addNewWord() {
        val englishWord = binding.englishTextInputEditText.text.toString().trim()
        val arabicWord = binding.arabicTextInputEditText.text.toString().trim()

        if (englishWord.isNotBlank() && arabicWord.isNotBlank()) {
            wordDB.addNewWord(Word(englishWord, arabicWord))
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
            removeInputs()
        } else if (englishWord.isBlank() && arabicWord.isBlank()) {
            setErrorEnglishTextField()
            setErrorArabicTextField()
        } else if (englishWord.isBlank()) {
            setErrorEnglishTextField()
        } else if (arabicWord.isBlank()) {
            setErrorArabicTextField()
        }
    }

    private fun removeInputs() {
        binding.englishTextInputEditText.text = null
        binding.arabicTextInputEditText.text = null
    }

    private fun setErrorEnglishTextField() {
        binding.englishTextInputLayout.isErrorEnabled = true
        binding.englishTextInputEditText.error = getString(R.string.empty)
    }

    private fun setErrorArabicTextField() {
        binding.arabicTextInputLayout.isErrorEnabled = true
        binding.arabicTextInputEditText.error = getString(R.string.empty)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}