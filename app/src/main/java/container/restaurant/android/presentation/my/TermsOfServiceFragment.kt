package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import container.restaurant.android.databinding.FragmentTermsOfServiceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TermsOfServiceFragment : Fragment() {

    private lateinit var binding: FragmentTermsOfServiceBinding

    private val viewModel: MyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTermsOfServiceBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@TermsOfServiceFragment.viewModel
                this.lifecycleOwner = this@TermsOfServiceFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTermsOfService()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {

        }
    }

    private fun getTermsOfService() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTermsOfService {
                binding.layoutLoading.visibility = View.GONE
            }
        }
    }
}