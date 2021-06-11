package container.restaurant.android.presentation.feed.write

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import container.restaurant.android.data.response.SearchLocalResponse
import container.restaurant.android.databinding.FragmentFeedWriteBottomSheetBinding
import container.restaurant.android.presentation.feed.write.adapter.SearchLocalAdapter
import container.restaurant.android.util.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedWriteBottomSheetFragment(private val clickItem:(SearchLocalResponse.Item) -> Unit) : BottomSheetDialogFragment() {

    private val viewModel: FeedWriteViewModel by viewModel()

    private lateinit var binding: FragmentFeedWriteBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeedWriteBottomSheetBinding.inflate(inflater,container,false)
        context ?: return binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        setBindItem()

        return binding.root
    }

    private fun setBindItem() {
        binding.apply {
            tvClose.setOnClickListener {
                dismiss()
            }

            etNameSearch.addTextChangedListener {
                if(!viewModel.searchProgressChk.value && etNameSearch.text.length >= 2) {
                    observe(viewModel.getSearchPlace(it.toString()), ::searchPlaceComplete)
                }
            }
        }
    }

    private fun searchPlaceComplete(searchLocalResponse: SearchLocalResponse) {
        val adapter = SearchLocalAdapter {
            clickItem(it)
            dismiss()
        }
        binding.rvRestaurantList.adapter = adapter
        adapter.submitList(searchLocalResponse.items)
    }

}