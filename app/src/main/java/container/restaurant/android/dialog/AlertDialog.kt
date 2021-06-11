package container.restaurant.android.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import container.restaurant.android.R
import container.restaurant.android.databinding.DialogAlertBinding

class AlertDialog(context: Context, private val titleStr: String?, private val msgStr: String?, private val confirmStr: String?, private val cancelStr: String?, val typeInt:Int?) : AlertDialog(context) {

    private lateinit var binding: DialogAlertBinding

    private val modelInAnim: Animation get() = AnimationUtils.loadAnimation(context, R.anim.modal_in)

    private var singleEventListener: SingleEventListener? = null

    private var multiEventLister: MultiEventLister? = null

    fun setSingleEventListener(singleEventListener: SingleEventListener) {
        this::singleEventListener.set(singleEventListener)
    }

    fun setMultiEventListener(multiEventLister: MultiEventLister) {
        this::multiEventLister.set(multiEventLister)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_alert, null, false)

        setContentView(binding.root)

        btnAction()
        subscribeUi()
    }

    private fun btnAction() {
        binding.confirm.setOnClickListener {
            singleEventListener?.confirmClick(this)
            multiEventLister?.confirmClick(this)
        }

        binding.cancel.setOnClickListener {
            multiEventLister?.cancelClick(this)
        }
    }

    private fun subscribeUi() {
        binding.title.visibility = View.GONE
        binding.message.visibility = View.GONE
        binding.confirm.visibility = View.GONE
        binding.confirm.visibility = View.GONE

        binding.layoutSuccess.visibility = View.GONE
        binding.layoutWarning.visibility = View.GONE
        binding.layoutError.visibility = View.GONE
        binding.layoutProgress.visibility = View.GONE

        titleStr?.let {
            binding.title.text = it
            binding.title.visibility = View.VISIBLE
        }
        msgStr?.let {
            binding.message.text = it
            binding.message.visibility = View.VISIBLE
        }
        confirmStr?.let {
            binding.confirm.text = it
            binding.confirm.visibility = View.VISIBLE
        }
        cancelStr?.let {
            binding.cancel.text = it
            binding.cancel.visibility = View.VISIBLE
        }
        typeInt?.let { type ->
            when(type) {
                SUCCESS_TYPE -> {
                    binding.layoutSuccess.visibility = View.VISIBLE
                }
                WARNING_TYPE -> {
                    binding.layoutWarning.visibility = View.VISIBLE
                }
                ERROR_TYPE -> {
                    binding.layoutError.visibility = View.VISIBLE
                }
                PROGRESS_TYPE -> {
                    binding.layoutProgress.visibility = View.VISIBLE
                    Glide.with(binding.root).load(R.drawable.img_level_up).into(binding.loading)
                }
                else -> binding.layoutSuccess.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.root.startAnimation(modelInAnim)
    }

    interface SingleEventListener {
        fun confirmClick(dialogSelf: container.restaurant.android.dialog.AlertDialog)
    }

    interface MultiEventLister {
        fun confirmClick(dialogSelf: container.restaurant.android.dialog.AlertDialog)
        fun cancelClick(dialogSelf: container.restaurant.android.dialog.AlertDialog)
    }

    override fun show() {
        super.show()
        showCheck = true
    }

    override fun dismiss() {
        super.dismiss()
        showCheck = false
    }

    companion object {
        const val SUCCESS_TYPE = 1
        const val WARNING_TYPE = 2
        const val ERROR_TYPE = 3
        const val PROGRESS_TYPE = 4

        var showCheck = false
    }
}