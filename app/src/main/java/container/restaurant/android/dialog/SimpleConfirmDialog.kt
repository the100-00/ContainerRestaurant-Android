package container.restaurant.android.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import container.restaurant.android.R
import container.restaurant.android.databinding.DialogSimpleConfirmBinding

class SimpleConfirmDialog(
    private val titleStr: String? = null,
    private val msgStr: String? = null,
    private val leftBtnStr: String? = null,
    private val rightBtnStr: String? = null
) : DialogFragment() {
    private lateinit var binding: DialogSimpleConfirmBinding

    private val modelInAnim: Animation
        get() = AnimationUtils.loadAnimation(
            context,
            R.anim.modal_in
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_simple_confirm,
            null,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBtnAction()
        uiSetting()
    }

    //버튼 클릭 이벤트 설정
    private fun setBtnAction() {
        binding.btnRight.setOnClickListener {
            singleEventListener?.confirmClick(this)
            multiEventListener?.onRightBtnClick(this)
        }

        binding.btnLeft.setOnClickListener {
            multiEventListener?.onLeftBtnClick(this)
        }
    }

    //Ui 설정
    private fun uiSetting() {
        binding.title.visibility = View.GONE
        binding.message.visibility = View.GONE
        binding.btnLeft.visibility = View.GONE
        binding.btnRight.visibility = View.GONE
        binding.horizontalDevider.visibility = View.GONE
        binding.verticalDevider.visibility = View.GONE

        // 디바이더로 사용된 뷰의 Visibility 설정 로직
        if (leftBtnStr != null || rightBtnStr != null) {
            binding.horizontalDevider.visibility = View.VISIBLE
            if (leftBtnStr != null && rightBtnStr != null) {
                binding.verticalDevider.visibility = View.VISIBLE
            }
        }

        //생성자에서 입력받은 값이 있으면 보여줌
        titleStr?.let {
            binding.title.text = it
            binding.title.visibility = View.VISIBLE
        }
        msgStr?.let {
            binding.message.text = it
            binding.message.visibility = View.VISIBLE
        }
        leftBtnStr?.let {
            binding.btnLeft.text = it
            binding.btnLeft.visibility = View.VISIBLE
        }
        rightBtnStr?.let {
            binding.btnRight.text = it
            binding.btnRight.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        binding.root.startAnimation(modelInAnim)
    }

    /** 여기부터는 리스너 관련 **/
    //확인 버튼만 있을 때 사용되는 리스너 인터페이스
    interface SingleEventListener {
        fun confirmClick(dialogSelf: SimpleConfirmDialog)
    }

    //확인, 취소 버튼이 있을 때 사용되는 리스너 인터페이스
    interface MultiEventListener {
        fun onRightBtnClick(dialogSelf: SimpleConfirmDialog)
        fun onLeftBtnClick(dialogSelf: SimpleConfirmDialog)
    }

    //확인 버튼만 있을 때 사용되는 리스너
    private var singleEventListener: SingleEventListener? = null

    //확인, 취소 버튼이 있을 때 사용되는 리스너
    private var multiEventListener: MultiEventListener? = null

    //확인 버튼만 있을 때 사용되는 리스너 설정 함수
    fun setSingleEventListener(singleEventListener: SingleEventListener) {
        this::singleEventListener.set(singleEventListener)
    }

    //확인, 취소 버튼이 있을 때 사용되는 리스너 설정 함수
    fun setMultiEventListener(multiEventListener: MultiEventListener) {
        this::multiEventListener.set(multiEventListener)
    }
    /** 여기까지 리스너 관련 **/
}