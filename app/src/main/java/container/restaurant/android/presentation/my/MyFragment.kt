package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import container.restaurant.android.databinding.FragmentMyBinding
import container.restaurant.android.presentation.NavigationController
import container.restaurant.android.util.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    private val viewModel: MyViewModel by viewModel()

    private val navigationController: NavigationController by inject { parametersOf(requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyBinding.inflate(layoutInflater, container, false)
            .apply {
                viewModel = this@MyFragment.viewModel
                lifecycleOwner = this@MyFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            navToSettings.observe(viewLifecycleOwner) {
                navigationController.navigateToSettings()
            }
        }
    }

    companion object {
        fun newInstance(): MyFragment = MyFragment()
    }
}