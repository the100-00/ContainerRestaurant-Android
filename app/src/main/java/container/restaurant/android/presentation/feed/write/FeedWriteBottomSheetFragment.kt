package container.restaurant.android.presentation.feed.write

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.FragmentFeedWriteBottomSheetBinding
import container.restaurant.android.util.CommUtils
import container.restaurant.android.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedWriteBottomSheetFragment() : BottomSheetDialogFragment() {

    private val viewModel: FeedWriteViewModel by viewModel()

    private lateinit var binding: FragmentFeedWriteBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedWriteBottomSheetBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@FeedWriteBottomSheetFragment.viewModel
                lifecycleOwner = this@FeedWriteBottomSheetFragment
            }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehaviorSetting()
        searchKeyBoardSetting()

        observeData()

    }

    private fun bottomSheetBehaviorSetting() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        // 초기에 확장된 상태로 보여줌
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        // 드래그해서 중간쯤 걸치는 효과 제거
        behavior.skipCollapsed = true
    }

    private fun searchKeyBoardSetting() {
        binding.etNameSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchPlace(v.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun observeData() {
        with(viewModel) {
            isCloseSearchButtonClicked.observe(this@FeedWriteBottomSheetFragment, EventObserver {
                if (it) {
                    dismiss()
                }
            })
            isSearchButtonClicked.observe(this@FeedWriteBottomSheetFragment, EventObserver {
                if (it) {
                    placeName.value?.let { placeName ->
                        searchPlace(placeName)
                    }
                }
            })
        }
    }

    private fun searchPlace(placeName: String) {
        // 에딧 텍스트 포커스 없애고 키보드 가림
        binding.etNameSearch.clearFocus()
        activity?.let {
            CommUtils.hideSoftKeyboard(it, binding.etNameSearch)
        }

        // naver api를 통한 검색
        lifecycleScope.launchWhenCreated {
            viewModel.getSearchPlace(placeName)
        }
    }

    // 둥근 모서리의 배경을 적용하기 위한 코드!!!
    override fun getTheme(): Int = R.style.BottomSheetDialog

}