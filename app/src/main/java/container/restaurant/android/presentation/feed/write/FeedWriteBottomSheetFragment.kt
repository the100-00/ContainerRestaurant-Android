package container.restaurant.android.presentation.feed.write

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentFeedWriteBottomSheetBinding
import container.restaurant.android.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedWriteBottomSheetFragment() : BottomSheetDialogFragment() {

    private val viewModel: FeedWriteViewModel by viewModel()

    private lateinit var binding: FragmentFeedWriteBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeedWriteBottomSheetBinding.inflate(inflater,container,false)
            .apply {
                viewModel = this@FeedWriteBottomSheetFragment.viewModel
                lifecycleOwner = this@FeedWriteBottomSheetFragment
            }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setBindItem()
        observeData()

    }

    private fun observeData() {
        with(viewModel) {
            isCloseSearchButtonClicked.observe(this@FeedWriteBottomSheetFragment, EventObserver {
                if (it) {
                    dismiss()
                }
            })
            isSearchButtonClicked.observe(this@FeedWriteBottomSheetFragment, EventObserver {
                if(it) {
                    placeName.value?.let {
                        lifecycleScope.launchWhenCreated {
                            viewModel.getSearchPlace(it)
                        }
                    }
                }
            })
        }
    }

    private fun setBindItem() {
        binding.apply {
            etNameSearch.addTextChangedListener {
//                if(!viewModel.searchProgressChk.value && etNameSearch.text.length >= 2) {
//                    observe(viewModel.getSearchPlace(it.toString()), ::searchPlaceComplete)
//                }
            }
        }
    }

    // 둥근 모서리의 배경을 적용하기 위한 코드!!!
    override fun getTheme(): Int = R.style.BottomSheetDialog

}