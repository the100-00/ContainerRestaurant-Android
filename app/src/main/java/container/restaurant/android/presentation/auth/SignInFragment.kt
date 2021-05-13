package container.restaurant.android.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentSignInBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

internal class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    private val nicknameEditing = MutableStateFlow("")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            nicknameEditing
                .debounce(DEBOUNCE_TIME)
                .filter {
                    if (it.toByteArray().size > 16) {
                        binding.tvNicknameError.text = getString(R.string.nickname_too_long)
                        binding.tvNicknameError.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange_02))
                        return@filter false
                    }
                    if (it.isEmpty()) {
                        return@filter false
                    }

                    true
                }.mapLatest {
                    var isCharOrNumeric = true
                    it.toCharArray().forEach { char ->
                        if (!Character.isLetterOrDigit(char)) {
                            isCharOrNumeric = false
                        }
                    }

                    isCharOrNumeric
                }.collect {
                    if (it) {
                        binding.tvNicknameError.text = getString(R.string.nickname_possible)
                        binding.tvNicknameError.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_03))
                    } else {
                        binding.tvNicknameError.text = getString(R.string.nickname_not_possiblew)
                        binding.tvNicknameError.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange_02))
                    }
                }
        }
        setupNicknameEditing()
        setupBtnCompleteListener()
    }

    private fun setupBtnCompleteListener() {
        binding.btnComplete.setOnClickListener {

        }
    }

    private fun setupNicknameEditing() {
        binding.editNickname.doOnTextChanged { text, _, _, _ ->
            nicknameEditing.value = text.toString()
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 250L
        fun newInstance() = SignInFragment()
    }
}