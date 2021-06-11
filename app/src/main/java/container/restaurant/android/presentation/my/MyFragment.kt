package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import container.restaurant.android.databinding.FragmentMyBinding

internal class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyBinding.inflate(layoutInflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    companion object {
        fun newInstance(): MyFragment = MyFragment()
    }

}