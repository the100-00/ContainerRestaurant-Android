package container.restaurant.android.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import container.restaurant.android.databinding.FragmentResNearBinding
import container.restaurant.android.presentation.map.item.NearRestaurant
import container.restaurant.android.presentation.map.item.NearResAdapter

internal class NearResFragment : Fragment() {
    private lateinit var binding: FragmentResNearBinding

    private val nearResAdapter by lazy {
        NearResAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResNearBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNearResRecycler()
    }

    private fun  setupNearResRecycler() {
        with(binding.rvNearRes) {
            adapter = nearResAdapter
        }

        nearResAdapter.setItems(
            listOf(
                NearRestaurant(1,"","용기낸 식당","#태그","2.1","3회")
            )
        )
    }

    companion object {
        fun newInstance(): NearResFragment = NearResFragment()
    }
}