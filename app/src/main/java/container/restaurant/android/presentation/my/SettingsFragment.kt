package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentSettingsBinding
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@SettingsFragment.viewModel
                lifecycleOwner = this@SettingsFragment
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSettingsRecycler()

        with(viewModel) {
            back.observe(viewLifecycleOwner) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun setupSettingsRecycler() {
        with(binding.rvSettings) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SettingsAdapter(
                listOf(
                    Settings(getString(R.string.alarm_settings), TYPE_ALARM),
                    Settings(getString(R.string.personal_info_policy), TYPE_NAV),
                    Settings(getString(R.string.service_agreement), TYPE_NAV),
                    Settings(getString(R.string.logout), TYPE_NAV),
                    Settings(getString(R.string.user_drop), TYPE_NAV),
                    Settings(getString(R.string.app_version), TYPE_TEXT)
                )
            )
            val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                .apply {
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_divider)?.let { setDrawable(it) }
                }
            addItemDecoration(divider)
        }
    }

    companion object {
        const val TYPE_ALARM = 0
        const val TYPE_NAV = 1
        const val TYPE_TEXT = 2

        fun newInstance() = SettingsFragment()
    }
}