package com.ziad_emad_dev.hello_in_english.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ziad_emad_dev.hello_in_english.R
import com.ziad_emad_dev.hello_in_english.WordViewModel
import com.ziad_emad_dev.hello_in_english.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordViewModel.startQuiz()

        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

        wordViewModel.currentWordUsed.observe(viewLifecycleOwner) {
            binding.arabicTextView.text = it
        }

        wordViewModel.currentWordCount.observe(viewLifecycleOwner) {
            binding.askNum.text = getString(R.string.ask_num, it, wordViewModel.quizSize.value)
        }
    }

    private fun onSubmitWord() {
        val playerWord = binding.englishTextInputEditText.text.toString().trim()

        if (wordViewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!wordViewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    private fun onSkipWord() {
        if (wordViewModel.nextWord()) {
            setErrorTextField(false)
            wordViewModel.currentWordUsed.observe(viewLifecycleOwner) {
                binding.arabicTextView.text = it
            }

            wordViewModel.currentWordCount.observe(viewLifecycleOwner) {
                binding.askNum.text = getString(R.string.ask_num, it, wordViewModel.quizSize.value)
            }
        } else {
            showFinalScoreDialog()
        }
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.quiz_finished))
            .setMessage(
                getString(
                    R.string.you_scored,
                    wordViewModel.score.value,
                    wordViewModel.quizSize.value
                )
            )
            .setCancelable(false)
            .setPositiveButton(getString(R.string.quiz_again)) { _, _ ->
                restartGame()
            }
            .setNegativeButton(getString(R.string.cancel)) { materialAlertDialogBuilder, _ ->
                findNavController().navigate(R.id.action_quizFragment_to_mainFragment)
                materialAlertDialogBuilder.dismiss()
            }
            .show()
    }

    private fun restartGame() {
        wordViewModel.restartQuiz()
        setErrorTextField(false)
        wordViewModel.currentWordUsed.observe(viewLifecycleOwner) {
            binding.arabicTextView.text = it
        }

        wordViewModel.currentWordCount.observe(viewLifecycleOwner) {
            binding.askNum.text = getString(R.string.ask_num, it, wordViewModel.quizSize.value)
        }
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.englishTextInputLayout.isErrorEnabled = true
            binding.englishTextInputEditText.error = getString(R.string.try_again)
        } else {
            binding.englishTextInputLayout.isErrorEnabled = false
            binding.englishTextInputEditText.text = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}