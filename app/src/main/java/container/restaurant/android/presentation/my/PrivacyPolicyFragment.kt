package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import container.restaurant.android.databinding.FragmentPrivacyPolicyBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PrivacyPolicyFragment : Fragment() {

    private lateinit var binding: FragmentPrivacyPolicyBinding

    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@PrivacyPolicyFragment.viewModel
                this.lifecycleOwner = this@PrivacyPolicyFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPrivacyPolicy()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            onPrivacyPolicyLoad.observe(viewLifecycleOwner) {
                if(it){
                    binding.layoutLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun getPrivacyPolicy() {
        lifecycleScope.launchWhenCreated {
            viewModel.getPrivacyPolicy()
        }
    }
}