package container.restaurant.android.presentation.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import container.restaurant.android.databinding.FragmentMySettingBinding

class MySettingFragment : Fragment() {

    private lateinit var binding: FragmentMySettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMySettingBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setBindItem()

        return binding.root
    }

    private fun setBindItem() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}