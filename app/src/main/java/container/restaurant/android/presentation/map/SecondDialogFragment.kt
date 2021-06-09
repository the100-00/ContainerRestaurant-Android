package container.restaurant.android.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentBottomMap2Binding


class SecondDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomMap2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_map_2, container, false)
        binding.setLifecycleOwner(this)

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn0.setOnClickListener{
            itemClick(0)
            dialog?.dismiss()
        }
        binding.btn1.setOnClickListener{
            itemClick(1)
            dialog?.dismiss()
        }
    }
    companion object {
        fun newInstance(itemClick : (Int) -> Unit) : SecondDialogFragment {
            return SecondDialogFragment(itemClick)
        }
    }
}
